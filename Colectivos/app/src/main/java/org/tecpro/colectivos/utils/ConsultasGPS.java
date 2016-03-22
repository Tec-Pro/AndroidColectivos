package org.tecpro.colectivos.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tecpro.colectivos.model.ModelBondi;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by nico on 16/03/16.
 */
public class ConsultasGPS {


    private class AsyncCaller extends AsyncTask<String, Void, ArrayList<ModelBondi> > {
        Context context; //contexto para largar la activity aca adentro

        private AsyncCaller(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected  ArrayList<ModelBondi> doInBackground(String... params) {

            //"Linea 1 Rojo","7","24","206"
            try {
                return ConsultasGPS.getHorarios(params[0], params[1], params[2], params[3]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<ModelBondi>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute( ArrayList<ModelBondi> result) {
            for(ModelBondi bondi: result){
                System.out.println(bondi.getLinea());
            }
        }
    }


    public static ArrayList<ModelBondi> getHorarios(String linea,String pCodigoLinea,String pCodigoOrigen,String pCodigoDestino) throws IOException {
        URL url;
        HttpURLConnection conn;
        url = new URL("http://mibondiya.cba.gov.ar/MiBondi.asmx/GetHorarios");
        String body = "{\"pCodigoEmpresa\":\"9802\",\"pCodigoLinea\":\""+pCodigoLinea+"\",\"pCodigoOrigen\":\""+pCodigoOrigen+"\",\"pCodigoDestino\":\""+pCodigoDestino+"\",\"pCodigoParada\":\"\"}";
        conn = (HttpURLConnection) url.openConnection();
        //set the output to true, indicating you are outputting(uploading) POST data
        conn.setDoOutput(true);
        //once you set the output to true, you don’t really need to set the request method to post, but I’m doing it anyway
        conn.setRequestMethod("POST");
        //Android documentation suggested that you set the length of the data you are sending to the server, BUT
        // do NOT specify this length in the header by using conn.setRequestProperty("Content-Length", length);
        //use this instead.
        conn.setFixedLengthStreamingMode(body.getBytes().length);
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        //send the POST out
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(body);
        out.close();
        ArrayList<ModelBondi> listResult = new ArrayList<>();
        try {
            JSONArray json= new JSONObject(streamToString(conn.getInputStream())).getJSONArray("d");
            int i=0;
            while(i<json.length()){
                JSONObject jsonObject= json.getJSONObject(i);
                ModelBondi bondi =new ModelBondi();
                String lineaAux=jsonObject.getString("linea").toLowerCase().replace("roja", "rojo");
                lineaAux=lineaAux.replace("negro", "verde");
                if(lineaAux.contains(linea.toLowerCase())) {
                    bondi.setLinea(linea);
                    if(!jsonObject.getString("lat").isEmpty()) {
                        bondi.setLatLng(Double.valueOf(jsonObject.getString("lat")), Double.valueOf(jsonObject.getString("lon")));
                        bondi.setCoche(Integer.valueOf(jsonObject.getString("coche")));
                        if (!listResult.contains(bondi))
                            listResult.add(bondi);
                    }
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listResult;
    }
    public static String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


}
