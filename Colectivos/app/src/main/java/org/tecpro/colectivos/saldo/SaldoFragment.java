package org.tecpro.colectivos.saldo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.tecpro.colectivos.R;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by nico on 16/03/16.
 */
public class SaldoFragment extends Fragment {

    private View view;
    private TextView txtUsuario, txtCarnet,txtSaldo,txtVencimiento, txtUltimoViaje,txtImporte,txtOrigen,txtDestino, txtCoche;
    private LinearLayout layoutInformation,layoutProgress,layoutNoData;
    private Spinner spinner;
    private EditText txtDni;
    private Button btnConsultar, btnHelp;
    public  SaldoFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.saldo_fragment, container, false);
        setReferences();
        getlastCode();
        return view;
    }

    private void setReferences(){
        txtUsuario = (TextView)  view.findViewById(R.id.txt_usuario);
        txtCarnet = (TextView)  view.findViewById(R.id.txt_carnet);
        txtSaldo = (TextView)  view.findViewById(R.id.txt_saldo);
        txtVencimiento = (TextView)  view.findViewById(R.id.txt_fecha_vencimiento);
        txtUltimoViaje = (TextView)  view.findViewById(R.id.txt_ultimo_viaje);
        txtImporte = (TextView)  view.findViewById(R.id.txt_importe);
        txtOrigen = (TextView)  view.findViewById(R.id.txt_origen);
        txtDestino = (TextView)  view.findViewById(R.id.txt_destino);
        txtCoche = (TextView)  view.findViewById(R.id.txt_coche);
        layoutInformation = (LinearLayout) view.findViewById(R.id.layout_information);
        layoutProgress = (LinearLayout) view.findViewById(R.id.layout_progress);
        layoutNoData = (LinearLayout) view.findViewById(R.id.layout_no_data);
        spinner = (Spinner) view.findViewById(R.id.spn_tipo_consulta);
        txtDni = (EditText) view.findViewById(R.id.txt_dni);
        btnConsultar = (Button) view.findViewById(R.id.btn_consultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = view.getContext().getSharedPreferences("preferencias", view.getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("codigo", txtDni.getText().toString());
                editor.putInt("type", spinner.getSelectedItemPosition());
                editor.commit();
                layoutInformation.setVisibility(View.GONE);
                layoutProgress.setVisibility(View.VISIBLE);
                layoutNoData.setVisibility(View.GONE);
                if(spinner.getSelectedItemPosition()==1){
                    try {
                        long l = new BigInteger(txtDni.getText().toString(), 16).longValue();
                        new AsyncCall().execute(String.valueOf(l),"codigo");

                    }catch (NumberFormatException e){
                        new AsyncCall().execute("111","codigo");

                    }
                }
                else{
                    new AsyncCall().execute(txtDni.getText().toString(),"dni");
                }
            }
        });
        btnHelp = (Button) view.findViewById(R.id.btn_help);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }


    public HashMap callHttp(String consulta,String tipo){
        //do this wherever you are wanting to POST
        URL url;
        HttpURLConnection conn;
        String usuario="";
        String saldo="";
        String carnet="";
        String vencimiento="";
        String ultimoViaje="";
        String origen="";
        String destino="";
        String coche="";
        String importe="";
        HashMap<String,String> result = new HashMap<>();
        String response = "";
        String auxConsulta = "sel_dni="+consulta+"&tarjeta=&volver=1&sel_empresa=0%3D98";
        try{
            //if you are using https, make sure to import java.net.HttpsURLConnection
            if(tipo=="codigo")
                url = new URL("http://www.yaviene.com/usuario/tarjeta_respuesta.php?c=98&v=1&n=" + consulta);
            else
                url = new URL("http://www.yaviene.com/usuario/tarjeta_respuesta.php");

                conn = (HttpURLConnection) url.openConnection();
//set the output to true, indicating you are outputting(uploading) POST data
                conn.setDoOutput(true);
//once you set the output to true, you don’t really need to set the request method to post, but I’m doing it anyway
            if(tipo=="codigo")
                conn.setRequestMethod("GET");

            else {
                conn.setRequestMethod("POST");
                conn.setFixedLengthStreamingMode(auxConsulta.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(auxConsulta);
                out.close();
            }
            Scanner inStream = new Scanner(conn.getInputStream());

//process the stream and store it in StringBuilder
            while(inStream.hasNextLine())
                response+=(inStream.nextLine());
            System.out.println(response);
            String parser = "<h1>Consulta de Saldo de Tarjetas</h1>Titular:";
            int indexFrom = response.indexOf(parser);
            int indexTo = response.indexOf("<br>", indexFrom);
            if(indexFrom!=-1) {
                usuario=response.substring(indexFrom + parser.length(), indexTo);
                parser = "<br>Saldo :";
                indexFrom=  response.indexOf(parser);
                indexTo = response.indexOf("<br><br>", indexFrom);
                String[] aux = response.substring(indexFrom + parser.length(), indexTo).split("<br>");
                saldo = aux[0].substring(0, aux[0].length() - 2);
                carnet = aux[1].substring(7);
                vencimiento = aux[2].substring(12).replace(" ", "");
                parser = "imo viaje</h3>";
                indexFrom=  response.indexOf(parser);
                if(indexFrom!=-1) {
                    indexTo = response.indexOf("<br></div>", indexFrom);
                    aux = response.substring(indexFrom + parser.length(), indexTo).split("<br>");
                    ultimoViaje = aux[0].substring(19).replace(" / ", "/");
                    origen = aux[1].substring(13);
                    destino = aux[2].substring(14);
                    coche = aux[3].substring(12);
                    importe = aux[4].substring(14);

                    System.out.println(usuario+","+saldo+","+carnet+","+vencimiento+","+ultimoViaje+","+origen+","+destino+","+coche+","+importe);

                }
            }
            result.put("usuario",usuario);
            result.put("saldo",saldo);
            result.put("carnet",carnet);
            result.put("vencimiento",vencimiento);
            result.put("ultimoViaje",ultimoViaje);
            result.put("origen",origen);
            result.put("destino",destino);
            result.put("coche",coche);
            result.put("importe",importe);
        }
//catch some error
        catch(MalformedURLException ex){
            System.out.println(ex.toString());

        }
// and some more
        catch(IOException ex){
            System.out.println(ex.toString());
        }
        return result;
    }

    private class AsyncCall extends AsyncTask<String, Void,HashMap<String,String>   > {



        @Override
        protected HashMap<String,String>  doInBackground(String... params) {
            return callHttp(params[0],params[1]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(HashMap<String,String> result) {
            if(!result.containsKey("usuario") || (result.get("usuario").isEmpty() && result.get("saldo").isEmpty())){
                layoutProgress.setVisibility(View.GONE);
                layoutInformation.setVisibility(View.GONE);
                layoutNoData.setVisibility(View.VISIBLE);
            }else {
                txtUsuario.setText(result.get("usuario"));
                txtSaldo.setText("$" + result.get("saldo"));
                txtVencimiento.setText(result.get("vencimiento"));
                txtCarnet.setText(result.get("carnet"));
                txtUltimoViaje.setText(result.get("ultimoViaje"));
                txtOrigen.setText(result.get("origen"));
                txtDestino.setText(result.get("destino"));
                txtCoche.setText(result.get("coche"));
                txtImporte.setText("$" + result.get("importe"));
                layoutProgress.setVisibility(View.GONE);
                layoutInformation.setVisibility(View.VISIBLE);
            }

        }
    }


    public void showAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());

        // set title
        alertDialogBuilder.setTitle("Como obtener el ID de la tarjeta");

        // set dialog message
        alertDialogBuilder
                .setMessage(("El ID de la tarjeta se encuentra en el boleto, arriba de la fecha. \n\nEj:2219-00017412-DCE45A2F , el código será: DCE45A2F"))
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        dialog.cancel();
                    }
                });



        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void getlastCode(){
        SharedPreferences prefs = view.getContext().getSharedPreferences("preferencias", view.getContext().MODE_PRIVATE);
        String code= prefs.getString("codigo","");
        int type = prefs.getInt("type",0);
        spinner.setSelection(type);
        txtDni.setText(code);
    }
}
