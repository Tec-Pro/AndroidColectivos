package org.tecpro.colectivos;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nico on 09/04/14.
 */
public class MapaDondeVoy extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {
    private GoogleMap mapa;
    Circle circleDesde;
    Circle circleHasta;
    int radio=300;
    Pair<String,Double> mejor=new Pair<String, Double>("ninguno",99999999.0);
    Double[] mejorRecorrido;
    ArrayList<String> lineasQueLlegan= new ArrayList<String>();
    Polyline rutaDibujada;

    GeocoderTask geo;


    MarkerOptions markDesde;
    MarkerOptions markHasta;
    Marker desde;
    Marker hasta;
    CircleOptions circleDesdeOp;
    CircleOptions circleHastaOp;
    private AnimatingMarkersFragment mapFragment;
    LatLng latLng;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_donde_voy);
        mapFragment = (AnimatingMarkersFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapa = mapFragment.getMap();


        if (mapa != null) {
            markDesde = new MarkerOptions().title("Desde").draggable(true).position(new LatLng(-33.11576, -64.340132)).icon(BitmapDescriptorFactory.fromResource(R.drawable.desdee));
            markHasta = new MarkerOptions().title("Hasta").draggable(true).position(new LatLng(-33.12376, -64.349032)).icon(BitmapDescriptorFactory.fromResource(R.drawable.hasta));
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
            if (mapa.getMyLocation() != null) {
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 13));
            } else {
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(-33.12376, -64.349032), 13));
            }


            desde = mapa.addMarker(markDesde);
            hasta = mapa.addMarker(markHasta);
            circleDesdeOp = new CircleOptions().center(desde.getPosition()).radius(radio).fillColor(Color.parseColor("#80F45555")).strokeWidth(0);
            circleHastaOp = new CircleOptions().center(hasta.getPosition()).radius(radio).fillColor(Color.parseColor("#807CE667")).strokeWidth(0);
            circleDesde = mapa.addCircle(circleDesdeOp);
            circleHasta = mapa.addCircle(circleHastaOp);
            mapa.setOnMarkerDragListener(this);
        } else {
            Toast.makeText(getApplicationContext(), "No se puedo cargar correctamente el mapa",
                    Toast.LENGTH_SHORT).show();
        }
    }






    public LatLng coord(double longitude, double latitude) {
        return new LatLng(latitude, longitude);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mapa_donde_voy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int i=0;
        switch (id){

            case R.id.config:
                lanzarPreferencias(null);
                break;
            case R.id.action_bar_start_animation:
                mapFragment.animator.stopAnimation();
                mapFragment.animator.startAnimation(false);
                break;
            case R.id.action_bar_stop_animation:
                mapFragment.animator.stopAnimation();
                break;
            case R.id.action_bar_toggle_style:
                mapFragment.toggleStyle();
                break;
            case R.id.action_bar_directions:
                startActivityForResult(new Intent(this, DirectionsInputActivity.class),3);
                break;


        }
        return super.onOptionsItemSelected(item);

    }

    public void lanzarPreferencias(View view){
        Intent i = new Intent(this, Preferencias.class);
        i.putExtra("cuadras",radio/100);
        startActivityForResult(i,2);

        

    }



    @Override
    public void onPause() {


            super.onPause();
    }

    @Override
    public void onResume() {


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        radio= 100*(Integer.valueOf(pref.getString("distancia","3")));
        if(mapa!=null) {
            circleHasta.setRadius(radio);
            circleDesde.setRadius(radio);
        }
        super.onResume();

    }

    @Override
    public void onDestroy() {

               super.onDestroy();
    }


    public void resultados(View view){
        if(lineasQueLlegan.size()>0) {
            Intent i = new Intent(this, LineaResult.class);
            i.putExtra("lineas", lineasQueLlegan);
            startActivityForResult(i, 1);
        }
        else{
            Toast.makeText(getApplicationContext(), "No existen lineas para esas coordenadas",
                    Toast. LENGTH_SHORT).show();        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1){


            if(data!=null) {
                if(rutaDibujada!=null)
                    rutaDibujada.remove();
                mapFragment.animator.stopAnimation();
                String lineaSelect = data.getStringExtra("lineaSelect");
                System.out.println("debo mostrar" + lineaSelect);
                mejorRecorrido=retornarLinea(lineaSelect);
                TextView elegida= (TextView) findViewById(R.id.linea_eleg);
                elegida.setText(lineaSelect);
                PolylineOptions a= new PolylineOptions();
                int i=0;
                mapFragment.eliminarRuta();
                while (i < mejorRecorrido.length - 1) {
                    a.add(coord(mejorRecorrido[i], mejorRecorrido[i + 1]));
                    mapFragment.addMarkerToMap(coord(mejorRecorrido[i], mejorRecorrido[i + 1]));
                    i = i + 2;

                }
                a.color(Color.BLUE);
                a.width(2);
                rutaDibujada=mapa.addPolyline(a);





            }
        }

        if(requestCode==3){
            if(data!=null){
                if(data.getStringExtra("desde")!=null && !data.getStringExtra("desde").equals("")){
                    geo=new GeocoderTask();
                            geo.cualMuevo(true);
                    geo.execute(data.getStringExtra("desde")+" Rio Cuarto Argentina");
                }
                if(data.getStringExtra("hasta")!=null && !data.getStringExtra("hasta").equals("")){
                    geo=new GeocoderTask();
                    geo.cualMuevo(false);
                    geo.execute(data.getStringExtra("hasta")+" Rio Cuarto Argentina");
                }
            }
        }


    }

    public void buscar(View view){
        final ProgressDialog ringProgressDialog = ProgressDialog.show(MapaDondeVoy.this, "Por favor, espere", "Realizando búsqueda", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ringProgressDialog.dismiss();
            }
        }).start();
        buscarAux();



    }


    private void buscarAux(){

        RecorridoEs r= new RecorridoEs();
        //desde!
        mapFragment.eliminarRuta();

        Pair<String,Double[]>[] recorridosYNombres= r.recorridosYNombres();
        mejor=new Pair<String, Double>("ninguno",99999999.0);
        PolylineOptions a = new PolylineOptions();
        lineasQueLlegan= new ArrayList<String>();
        int j=0;
        while(j<recorridosYNombres.length){
            Pair<String,Double[]> lin= recorridosYNombres[j];
            double dist=perteneceAlRadio(lin.second ,circleDesde.getCenter(),radio,circleHasta.getCenter());
            if(dist!=-1){
                retornoMinimo(lin.first,dist,lin.second);
                lineasQueLlegan.add(lin.first);
                DecimalFormat twoDForm = new DecimalFormat("#.00");
                lineasQueLlegan.add(twoDForm.format(dist / 1000));

            }
            j++;
        }




            if(mejor.first.equals("ninguno")) {
                System.out.println("el mejor es el" + mejor.first);
                TextView elegida = (TextView) findViewById(R.id.linea_eleg);
                elegida.setText("No existen lineas para estas coordenadas");
                mejorRecorrido=null;
            }
        if(rutaDibujada!=null)
            rutaDibujada.remove();



        int i = 0;

        if(mejorRecorrido!=null) {
            while (i < mejorRecorrido.length - 1) {
                a.add(coord(mejorRecorrido[i], mejorRecorrido[i + 1]));
                mapFragment.addMarkerToMap(coord(mejorRecorrido[i], mejorRecorrido[i + 1]));

                i = i + 2;
            }
            a.color(Color.BLUE);
            a.width(2);
            rutaDibujada = mapa.addPolyline(a);
            TextView elegida = (TextView) findViewById(R.id.linea_eleg);
            elegida.setText("linea " + mejor.first);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if(marker.getTitle().equals("Desde")) {
            circleDesde.setVisible(false);
        }
        else{
            circleHasta.setVisible(false);
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {


    }


    private void retornoMinimo(String linea, Double dist,Double[] reco){
        if(dist< mejor.second){
            mejor= new Pair<String, Double>(linea,dist);
            mejorRecorrido=reco;
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if(marker.getTitle().equals("Desde")) {
            circleDesde.setCenter(marker.getPosition());
            circleDesde.setRadius(radio);
            circleDesde.setFillColor(Color.parseColor("#80F45555"));
            circleDesde.setStrokeWidth(0);
            circleDesde.setVisible(true);

        }
        else{
            circleHasta.setCenter(marker.getPosition());
            circleHasta.setRadius(radio);
            circleHasta.setFillColor(Color.parseColor("#807CE667"));
            circleHasta.setStrokeWidth(0);
            circleHasta.setVisible(true);

        }
    }


    private double perteneceAlRadio (Double[] reco, LatLng point,int radio,LatLng pointHasta){
        GeoPunto geo= new GeoPunto();
        double radioD=(0.0010736987954231836*radio)/100;
        int i = 0;
        double distancia=0 ;
        int aux=0;
        int aux2=0;
        double distAux=0;
        double mejorHastaMomento=99999999999.99;
        while (i < reco.length - 3 ) {
            if(intersect(reco[i], reco[i + 1],reco[i+2], reco[i + 3],point.longitude,point.latitude,radioD)){
                LatLng puntoMedio= puntoMedio(Xaux1, Yaux1, Xaux2, Yaux2);
                distancia= distancia +geo.distancia(puntoMedio.longitude,puntoMedio.latitude, reco[i + 2], reco[i + 3]);
                aux=i;
                i=i+2;

                while(i < reco.length - 3 ){
                    distancia= distancia +geo.distancia(reco[i], reco[i + 1], reco[i + 2], reco[i + 3]);
                    if(intersect(reco[i], reco[i + 1],reco[i+2], reco[i + 3],pointHasta.longitude,pointHasta.latitude,radioD)){
                        LatLng puntoMedioHa= puntoMedio(Xaux1, Yaux1, Xaux2, Yaux2);
                        distancia= distancia -geo.distancia(reco[i], reco[i + 1], reco[i + 2], reco[i + 3]);
                        distancia= distancia +geo.distancia( reco[i], reco[i + 1],puntoMedioHa.longitude,puntoMedioHa.latitude);
                            if(distancia<=mejorHastaMomento) {
                                mejorHastaMomento = distancia;
                            }
                    }
                    i=i+2;
                    
                }

                i=aux;
            }

            distancia=0;
            i = i + 2;
            System.out.println(i);
        }
        if(mejorHastaMomento==99999999999.99)
            return -1;
        else{
          return  mejorHastaMomento;
        }
    }






    //me canse de escribir mucho
    private Double[] retornarLinea(String num){
        RecorridoEs r= new RecorridoEs();

        if(num.equals("LINEA 1 VERDE"))
            return r.recorrido1Verde;
        if(num.equals("LINEA 1 ROJO"))
            return r.recorrido1Rojo;
        if(num.equals("LINEA 2 VERDE"))
            return r.recorrido2Verde;
        if(num.equals("LINEA 2 ROJO"))
            return r.recorrido2Rojo;
        if(num.equals("LINEA 3"))
            return r.recorrido3;
        if(num.equals("LINEA 4"))
            return r.recorrido4;
        if(num.equals("LINEA 5"))
            return r.recorrido5;
        if(num.equals("LINEA 6"))
            return  r.recorrido6;
        if(num.equals("LINEA 7"))
            return r.recorrido7;
        if(num.equals("LINEA 8 VERDE"))
            return r.recorrido8Verde;
        if(num.equals("LINEA 8 ROJO"))
            return r.recorrido8Rojo;
        if(num.equals("LINEA 9 VERDE"))
            return r.recorrido9Verde;
        if(num.equals("LINEA 9 ROJO"))
            return r.recorrido9Rojo;
        if(num.equals("LINEA 10"))
            return r.recorrido10;
        if(num.equals("LINEA 11"))
            return r.recorrido11;
        if(num.equals("LINEA 12"))
            return r.recorrido12;
        if(num.equals("LINEA 13"))
            return r.recorrido13;
        if(num.equals("LINEA 14"))
            return r.recorrido14;
        if(num.equals("LINEA 15"))
            return r.recorrido15;
        if(num.equals("LINEA 16"))
            return r.recorrido16;
        if(num.equals("LINEA 17"))
            return r.recorrido17;
        if(num.equals("LINEA 18"))
            return r.recorrido18;
        else
            return null;
    }

    // An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        boolean desdeTrue;
        public void cualMuevo(boolean desde){
            desdeTrue=desde;
        }


        private ProgressDialog pdia;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdia = new ProgressDialog(MapaDondeVoy.this);

            pdia.setTitle("Por favor, espere.");
            pdia.setMessage("Localizando la dirección solicitada ");
            pdia.show();
        }

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }


        @Override
        protected void onPostExecute(List<Address> addresses) {
            String completar = "";
            if (desdeTrue) {
                completar = "de origen";
            } else {
                completar = "de destino";
            }
            pdia.dismiss();
            if (addresses == null) {
                Toast.makeText(getBaseContext(), "No se ha encontrado la dirección " + completar + " Revise su conexión a internet", Toast.LENGTH_SHORT).show();

            } else {
                if (addresses.size() == 0) {
                    Toast.makeText(getBaseContext(), "No se ha encontrado la dirección " + completar, Toast.LENGTH_SHORT).show();
                } else {
                    // Clears all the existing markers on the map


                    // Adding Markers on Google Map for each matching address
                    for (int i = 0; i < addresses.size(); i++) {

                        Address address = (Address) addresses.get(i);

                        // Creating an instance of GeoPoint, to display in Google Map
                        latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        String addressText = String.format("%s, %s",
                                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                                address.getCountryName());

                        if (desdeTrue) {
                            System.out.println("desde mover");
                            circleDesde.setCenter(latLng);
                            desde.setPosition(latLng);
                        } else {
                            System.out.println("hasta mover");
                            circleHasta.setCenter(latLng);
                            hasta.setPosition(latLng);
                        }


                        // Locate the first location
                        if (i == 0)
                            mapa.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            }
        }
    }




    private double Xaux1;
    private double Yaux1;
    private double Xaux2;
    private double Yaux2;

    public  boolean aplicarFormula(double a, double b, double c) {
        double x1 = (-b + Math.sqrt((b * b) - 4 * a * c)) / (2 * a);
        double x2 = (-b - Math.sqrt((b * b) - 4 * a * c)) / (2 * a);
        Xaux1=x1;
        Xaux2=x2;
        //System.out.println("El valor de x1 es: " + x1);
        //System.out.println("El valor de x2 es: " + x2);
        return(!Double.isNaN(x1)||!Double.isNaN(x2));
    }

    public boolean intersect(double xp, double yp, double xq, double yq, double xC, double yC, double rad){
        double  X=xC; //centro
        double Y= yC; //centro
        double radio= rad;
        double Xp= xp ;
        double Yp= yp;

        double Xq= xq;
        double Yq= yq;

        double m = (Yq-Yp)/(Xq-Xp);
        //System.out.println("m:"+m);
        double c= Yq-m*Xq;
        //System.out.println("c:"+c);

        double a= m*m;
        //System.out.println("m²:"+a);

        double b= 2*m*(c-Y);
        //System.out.println("b:"+b);
        double cPol= (c-Y)*(c-Y);
        //System.out.println("c:"+cPol);
        cPol= cPol-(radio*radio)+(X*X);
        a=a+1;
        //System.out.println("a:"+a);
        b= b- 2*X;
        //System.out.println("b:"+b);
        //System.out.println("c:"+cPol);
        boolean ret=aplicarFormula(a, b, cPol);
         Yaux1=m*Xaux1+c;
         Yaux2=m*Xaux2+c;
        //System.out.println("y1= " +Yaux1);
        //System.out.println("y2= " +Yaux2);
        double limInf= Math.min(xp,xq);
        double limSup= Math.max(xp,xq);
        return (((limInf<=Xaux1&&Xaux1<=limSup)||(limInf<=Xaux2&&Xaux2<=limSup))&&ret);
    }

    private LatLng puntoMedio( double pLongitud, double pLatitud, double p2Longitud, double p2Latitud){
        double medioLong=(pLongitud+p2Longitud)/2;
        double medioLat= (pLatitud+p2Latitud)/2;
        return new LatLng(medioLat,medioLong);
    }

}
