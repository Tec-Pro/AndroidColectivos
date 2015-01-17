package org.tecpro.colectivos;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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
    private GoogleMap mapa; //Mapa de google
    Circle circleDesde; //circulo desde
    Circle circleHasta; //circulo hasta
    int radio=300; //radio medido en metros, inicial 300, depende de perferencias
    Pair<String,Double> mejor=new Pair<String, Double>("ninguno",99999999.0); //mejor linea y distancia INDIVIDUAL!
    Double[] mejorRecorrido; //mejor recorrido, individuaaaal!
    ArrayList<String> lineasQueLlegan= new ArrayList<String>(); //String de linas que llegan con distancia, [0] va el nombre, en pos que sigue la distancia en km
    Polyline rutaDibujada; //ruta dibujada, para individual
    GeocoderTask geo;
    MarkerOptions markDesde; //Marcador desde Options
    MarkerOptions markHasta; //marcador hasta Options
    Marker desde;
    Marker hasta;
    CircleOptions circleDesdeOp;
    CircleOptions circleHastaOp;
    private AnimatingMarkersFragment mapFragment; //Fragment del mapa
    LatLng latLng;
    Polyline rutaDibujada2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_donde_voy);
        mapFragment = (AnimatingMarkersFragment) getSupportFragmentManager().findFragmentById(R.id.mapa); //inicio el Fragment que contiene el mapa
        mapa = mapFragment.getMap(); //obtengo mapa del fragment
        if (mapa != null) { //Si el mapa es disntito de null quiere decir que se cargó mortal, pongo los marcadores Desde Hasta, seteo mapas y movidas
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

    //convierto las coordenadas, google más la da invertidas.
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

            case R.id.config: //lanzar preferencias
                lanzarPreferencias(null);
                break;
            case R.id.action_bar_start_animation: //iniciar animacion,
                mapFragment.animator.stopAnimation(); //freno una si existía
                mapFragment.animator.startAnimation(false); //inicio la nueva
                break;
            case R.id.action_bar_stop_animation: //freno animación
                mapFragment.animator.stopAnimation();
                break;
            case R.id.action_bar_toggle_style: //cambiar estilo de mapa, satelital o el dibujado
                mapFragment.toggleStyle();
                break;
            case R.id.action_bar_directions: //buscar por direccion
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
            Intent i = new Intent(this, LineaResult.class); //inicio la vista que tiene los resultados de las lineas
            i.putExtra("lineas", lineasQueLlegan); // le paso las lineas que llegan
            startActivityForResult(i, 1); // y espero por la linea seleccionada por el pibe
        }
        else{
            Toast.makeText(getApplicationContext(), "No existen lineas para esas coordenadas",Toast. LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1){
            if(data!=null) { // si se retornó info
                if(rutaDibujada!=null) // si hay una ruta ya dibujada, la limpio
                 rutaDibujada.remove();
                if(rutaDibujada2!=null) // si hay una ruta ya dibujada, la limpio
                    rutaDibujada2.remove();
                mapFragment.animator.stopAnimation(); //freno animación si hay
                String lineaSelect = data.getStringExtra("lineaSelect");
                System.out.println("debo mostrar" + lineaSelect);
                if(!lineaSelect.contains("-")){ // es linea combinada
                    mejorRecorrido=retornarLinea(lineaSelect); //retorno las lineas
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
                    a.width(4);
                    rutaDibujada=mapa.addPolyline(a);

                }else{

                    mapFragment.eliminarRuta();
                    PolylineOptions a = new PolylineOptions();
                    String[] lineas= lineaSelect.split("-");
                    Double[] lineaDesde= retornarLinea(lineas[0]);
                    Double[] lineaHasta= retornarLinea(lineas[1]);
                    int i=0;
                    while (i < lineaDesde.length - 1) {
                        a.add(coord(lineaDesde[i], lineaDesde[i + 1]));
                        mapFragment.addMarkerToMap(coord(lineaDesde[i], lineaDesde[i + 1]));
                        i = i + 2;
                    }
                        a.color(Color.BLUE);
                        a.width(4);
                        rutaDibujada = mapa.addPolyline(a);
                        PolylineOptions b = new PolylineOptions();
                        i=0;
                        while (i < lineaHasta.length - 1) {
                            b.add(coord(lineaHasta[i], lineaHasta[i + 1]));
                            mapFragment.addMarkerToMap(coord(lineaHasta[i], lineaHasta[i + 1]));

                            i = i + 2;
                        }
                    b.color(Color.GREEN);
                    b.width(4);
                    rutaDibujada2 = mapa.addPolyline(b);
                    LinkedList<LatLng> lista= new LinkedList<LatLng>();
                    lista=seCombinan(lineaDesde,lineaHasta,lineaHasta.length-3,radio);
                    i=0;
                    while(i<lista.size()){
                        System.out.println("agrego mar");
                        mapa.addCircle(new CircleOptions().center(lista.get(i)).radius(radio).visible(true)).setVisible(true);
                        i++;
                    }
            }
                TextView elegida = (TextView) findViewById(R.id.linea_eleg);
                elegida.setText("linea/s " + lineaSelect);
            }
        }
        if(requestCode==3){ //opción elegida para buscar por direccion
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
        buscarAux();
        if((mejor.first.equals("ninguno"))){
            combinandoLineas();
        }
    /*    if(mejor.first.equals("ninguno")) {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(MapaDondeVoy.this, "Por favor, espere", "Buscando combinaciones posibles de lineas", true);
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

            combinarLineas();
        }*/




    }

    public void combinandoLineas(){
        final ProgressDialog ringProgressDialog = ProgressDialog.show(MapaDondeVoy.this, "Por favor, espere", "Buscando combinaciones posibles de lineas", true);
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


        combinarLineas();
    }


    private void buscarAux(){

        RecorridoEs r= new RecorridoEs(); //obtengo todos los recorridos
        mapFragment.eliminarRuta(); //limpio el mapita
        Pair<String,Double[]>[] recorridosYNombres= r.recorridosYNombres(); //nombre y recorridos!
        mejor=new Pair<String, Double>("ninguno",99999999.0); // el mejor no existe
        PolylineOptions a = new PolylineOptions(); //ruta
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
                elegida.setText("No existen lineas directas para estas coordenadas");
                mejorRecorrido=null;

            }
        if(rutaDibujada!=null)
            rutaDibujada.remove();
        if(rutaDibujada2!=null)
            rutaDibujada2.remove();
        int i = 0;
        if(mejorRecorrido!=null) {
            while (i < mejorRecorrido.length - 1) {
                a.add(coord(mejorRecorrido[i], mejorRecorrido[i + 1]));
                mapFragment.addMarkerToMap(coord(mejorRecorrido[i], mejorRecorrido[i + 1]));
                i = i + 2;
            }
            a.color(Color.BLUE);
            a.width(4);
            rutaDibujada = mapa.addPolyline(a);
            TextView elegida = (TextView) findViewById(R.id.linea_eleg);
            elegida.setText("linea " + mejor.first);
        }
        if(mejor.first.equals("ninguno")) {
            //combinandoLineas();

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

    private void combinarLineas(){
        RecorridoEs r= new RecorridoEs();
        Pair<String,Double[]>[] recorridosYNombres= r.recorridosYNombres();
        LinkedList<Pair<Pair<String,Double[]>,Integer>>  desde= contienePunto( recorridosYNombres, circleDesde.getCenter(),radio);
        LinkedList<Pair<Pair<String,Double[]>,Integer>> hasta= contienePunto( recorridosYNombres, circleHasta.getCenter(),radio);
        lineasQueLlegan= new ArrayList<String>();
        LinkedList<Pair<Pair<Pair<String, Double[]>, Pair<String, Double[]>>,LinkedList<LatLng>>> lineasElegidas= new LinkedList<Pair<Pair<Pair<String, Double[]>, Pair<String, Double[]>>,LinkedList<LatLng>>>();
        int i=0;
        int j=0;
        LinkedList<LatLng> lista= new LinkedList<LatLng>();
        while(i<desde.size()){
            j=0;
            while(j<hasta.size()){
               lista=seCombinan(desde.get(i).first.second,hasta.get(j).first.second,hasta.get(j).second,radio);
                if(lista.size()>0){
                    lineasQueLlegan.add(desde.get(i).first.first +"-"+ hasta.get(j).first.first);
                    DecimalFormat twoDForm = new DecimalFormat("#.00");
                    lineasQueLlegan.add("¿?");
                    lineasElegidas.add(new Pair<Pair<Pair<String, Double[]>, Pair<String, Double[]>>,LinkedList<LatLng>>(new Pair(desde.get(i).first,hasta.get(j).first),lista));
                    int h=0;

                }
                j++;
            }
            i++;
        }
        PolylineOptions a = new PolylineOptions();
        if(lineasQueLlegan.size()>0){
            Double[] lineaDesde= lineasElegidas.get(0).first.first.second;
            Double[] lineaHasta= lineasElegidas.get(0).first.second.second;
            LinkedList<LatLng> puntosIntersect= lineasElegidas.get(0).second;
            i=0;
            while (i < lineaDesde.length - 1) {
                a.add(coord(lineaDesde[i], lineaDesde[i + 1]));
                mapFragment.addMarkerToMap(coord(lineaDesde[i], lineaDesde[i + 1]));
                i = i + 2;
            }
            a.color(Color.BLUE);
            a.width(4);
            rutaDibujada = mapa.addPolyline(a);
            PolylineOptions b = new PolylineOptions();
            i=0;
            while (i < lineaHasta.length - 1) {
                b.add(coord(lineaHasta[i], lineaHasta[i + 1]));
                mapFragment.addMarkerToMap(coord(lineaHasta[i], lineaHasta[i + 1]));
                i = i + 2;
            }
            b.color(Color.GREEN);
            b.width(4);
            rutaDibujada2=mapa.addPolyline(b);
            i=0;
       while(i<puntosIntersect.size()){
           mapa.addCircle(new CircleOptions().center(puntosIntersect.get(i)).radius(radio).visible(true).strokeWidth(0).fillColor(Color.WHITE));
           i++;
       }
        TextView elegida = (TextView) findViewById(R.id.linea_eleg);
        elegida.setText("lineas " + lineasQueLlegan.get(0));
        }
        else{
            TextView elegida = (TextView) findViewById(R.id.linea_eleg);
            elegida.setText("No existen lineas directas ni combinaciones para estas coordenadas");
        }
    }


    private LinkedList<Pair<Pair<String,Double[]>,Integer>> contienePunto(Pair<String,Double[]>[] recoYNombre, LatLng point,int radio){
        LinkedList<Pair<Pair<String,Double[]>,Integer>> retorno= new LinkedList<Pair<Pair<String,Double[]>,Integer>> ();
        int j=0;
        int i=0;
        int auxJanterior=0;
        double radioD=(0.0010736987954231836*radio)/100;
        while(j<recoYNombre.length){
            Pair<String,Double[]> lin= recoYNombre[j];
            Double[] reco= lin.second;
            i=0;
            auxJanterior=j;
            while (i<lin.second.length-3){
                if(intersect(reco[i], reco[i + 1],reco[i+2], reco[i + 3],point.longitude,point.latitude,radioD)){

                    retorno.add(new Pair(lin,i));
                    i=lin.second.length;


                }
                i=i+2;
            }

            j++;
        }


        return retorno;
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
               // distancia= distancia +geo.distancia(puntoMedio.longitude,puntoMedio.latitude, reco[i + 2], reco[i + 3]);
                aux=i;
                boolean primerPasada= true;

                while(i!=aux ||primerPasada){


                    if(intersect(reco[i], reco[i + 1],reco[i+2], reco[i + 3],pointHasta.longitude,pointHasta.latitude,radioD)){
                        LatLng puntoMedioHa= puntoMedio(Xaux1, Yaux1, Xaux2, Yaux2);
                        if(primerPasada){
                            distancia= geo.distancia(puntoMedio.longitude,puntoMedio.latitude,puntoMedioHa.longitude,puntoMedioHa.latitude);
                        }
                        else{
                            distancia= distancia +geo.distancia( reco[i], reco[i + 1],puntoMedioHa.longitude,puntoMedioHa.latitude);
                        }
                        //distancia= distancia -geo.distancia(reco[i], reco[i + 1], reco[i + 2], reco[i + 3]);
                       // distancia= distancia +geo.distancia( reco[i], reco[i + 1],puntoMedioHa.longitude,puntoMedioHa.latitude);
                            if(distancia<=mejorHastaMomento) {
                                mejorHastaMomento = distancia;
                            }
                    }
                    else{
                        if(primerPasada)
                            distancia= distancia +geo.distancia(puntoMedio.longitude,puntoMedio.latitude, reco[i + 2], reco[i + 3]);
                        else
                            distancia= distancia +geo.distancia(reco[i], reco[i + 1], reco[i + 2], reco[i + 3]);
                    }

                    i=i+2;
                    if(!(i<reco.length - 3)){
                        i=0;
                    }
                    primerPasada=false;
                }

                i=aux;
            }

            distancia=0;
            i = i + 2;

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





    public double modulo2(org.tecpro.colectivos.Pair<Double,Double> puntoA ,org.tecpro.colectivos.Pair<Double,Double>  puntoB) {
        return (puntoB.x -puntoA.x)*(puntoB.x-puntoA.x)+(puntoB.y-puntoA.y)*(puntoB.y-puntoA.y);
    }

    // Distancia punto a punto en 2 dimensiones
    public double distancia(org.tecpro.colectivos.Pair<Double,Double> puntoA, org.tecpro.colectivos.Pair<Double,Double> puntoB) {
        return Math.sqrt(modulo2(puntoA, puntoB));
    }

    // Distancia del segmento al punto C
    public double distanciaPunto(org.tecpro.colectivos.Pair<Double,Double> A, org.tecpro.colectivos.Pair<Double,Double> B,org.tecpro.colectivos.Pair<Double,Double> C){
        // Punto en el segmento al cual se calculará la distancia
        // iniciamos en uno de los extremos
        org.tecpro.colectivos.Pair<Double,Double> P = A;

        // Para prevenir una división por cero se calcula primero el demoninador de
        // la división. (Se puede dar si A y B son el mismo punto).
        // Podría substituirse por [self modulo2:A a:B]
        double denominador = (B.x-A.x)*(B.x-A.x)+(B.y-A.y)*(B.y-A.y);
        if(denominador !=0){
            // Se calcula el parámetro, que indica la posición del punto P en la recta
            // del segmento
            double u = ((C.x-A.x)*(B.x-A.x)+(C.y-A.y)*(B.y-A.y))/denominador;
            // Si u esta en el intervalo [0,1], el punto P pertenece al segmento
            if(u > 0.0 && u < 1.0) {
                P.x = A.x + u*(B.x-A.x);
                P.y = A.y + u*(B.y-A.y);
            }
            // Si P no pertenece al segmento se toma uno de los extremos para calcular
            // la distancia. Si u < 0 el extremo es A. Si u >=1 el extremos es B.
            else{
                if( u>= 1.0)
                    P=B;
            }
        }
        // Se devuelve la distancia entre el punto C y el punto P calculado.
        return distancia(P, C);
    }

    public Pair<Double,LatLng> distanciaMinima(double x1, double y1, double x2, double y2, double rx1, double ry1, double rx2, double ry2) {
  //      Line2D.Double recta = new Line2D.Double(x1, y1, x2, y2);
  //      Line2D.Double recta2 = new Line2D.Double(rx1, ry1, rx2, ry2);
        org.tecpro.colectivos.Pair<Double,Double> a= new org.tecpro.colectivos.Pair<Double, Double>(x1, y1);
        org.tecpro.colectivos.Pair<Double,Double> b= new org.tecpro.colectivos.Pair<Double, Double>(x2, y2);
        org.tecpro.colectivos.Pair<Double,Double> c= new org.tecpro.colectivos.Pair<Double, Double>(rx1, ry1);
        org.tecpro.colectivos.Pair<Double,Double> d= new org.tecpro.colectivos.Pair<Double, Double>(rx2, ry2);

        if (intersects(a, b, c, d)) {
            return new Pair(0.0,new LatLng(intersecty,intersectx));
        }
        else{

            double distABc= distanciaPunto(a, b, c);
            double distABd=distanciaPunto(a, b, d);
            double distCDa=distanciaPunto(c, d, a);
            double distCDb=distanciaPunto(c, d, b);
            double aux1= Math.min(distABc,distABd);
            double aux2= Math.min(distCDa,distCDb);
            double minimo = Math.min(aux1, aux2);
            if(minimo==distABc){
                return new Pair(minimo,new LatLng(c.y,c.x));
            }
            if(minimo==distABd){
                return new Pair(minimo,new LatLng(d.y,d.x));

            }
            if(minimo==distCDa){

            return new Pair(minimo,new LatLng(a.y,a.x));
            }
            if(minimo==distCDb){

            return new Pair(minimo,new LatLng(b.y,b.x));}

        }
return null;


    }

    public LinkedList<LatLng> seCombinan(Double[] desde, Double[] hasta, int indiceHasta, int radio){
        int i=0;
        int j=0;
        double radioD=(0.0010736987954231836*radio)/100;
        LatLng anterior= null;
        LinkedList<LatLng>ret= new LinkedList<LatLng>();
        while(i<desde.length-3){
            j=0;
            while(j<indiceHasta){
                Pair<Double,LatLng> distanciaMin= distanciaMinima(desde[i], desde[i+1],desde[i+2], desde[i+3],desde[i], hasta[j+1],hasta[j+2], hasta[j+3]);
                if(distanciaMin.first<=radioD){
                    if(anterior!=null){
                        if(radioD<distancia(new org.tecpro.colectivos.Pair<Double, Double>(anterior.longitude,anterior.latitude),new org.tecpro.colectivos.Pair<Double, Double>(distanciaMin.second.longitude,distanciaMin.second.latitude))){
                            ret.add(distanciaMin.second);
                        }
                    }
                   anterior= distanciaMin.second;

                }
                j=j+2;
            }
            i=i+2;
        }
        return ret;
    }

    private boolean intersects(org.tecpro.colectivos.Pair<Double,Double> start1,
                               org.tecpro.colectivos.Pair<Double,Double> end1,org.tecpro.colectivos.Pair<Double,Double> start2, org.tecpro.colectivos.Pair<Double,Double> end2) {

        // First find Ax+By=C values for the two lines
        double A1 = end1.y - start1.y;
        double B1 = start1.x - end1.x;
        double C1 = A1 * start1.x + B1 * start1.y;

        double A2 = end2.y - start2.y;
        double B2 = start2.x - end2.x;
        double C2 = A2 * start2.x + B2 * start2.y;

        double det = (A1 * B2) - (A2 * B1);
        intersectx= Double.MAX_VALUE;
        intersecty= Double.MAX_VALUE;
        if (det == 0) {
            // Lines are either parallel, are collinear (the exact same
            // segment), or are overlapping partially, but not fully
            // To see what the case is, check if the endpoints of one line
            // correctly satisfy the equation of the other (meaning the two
            // lines have the same y-intercept).
            // If no endpoints on 2nd line can be found on 1st, they are
            // parallel.
            // If any can be found, they are either the same segment,
            // overlapping, or two segments of the same line, separated by some
            // distance.
            // Remember that we know they share a slope, so there are no other
            // possibilities

            // Check if the segments lie on the same line
            // (No need to check both points)
            if ((A1 * start2.x) + (B1 * start2.y) == C1) {
                // They are on the same line, check if they are in the same
                // space
                // We only need to check one axis - the other will follow
                if ((Math.min(start1.x, end1.x) < start2.x)
                        && (Math.max(start1.x, end1.x) > start2.x))
                    return true;

                // One end point is ok, now check the other
                if ((Math.min(start1.x, end1.x) < end2.x)
                        && (Math.max(start1.x, end1.x) > end2.x))
                    return true;

                // They are on the same line, but there is distance between them
                return false;
            }

            // They are simply parallel
            return false;
        } else {
            // Lines DO intersect somewhere, but do the line segments intersect?
            double x = (B2 * C1 - B1 * C2) / det;
            double y = (A1 * C2 - A2 * C1) / det;

            // Make sure that the intersection is within the bounding box of
            // both segments
            if ((x > Math.min(start1.x, end1.x) && x < Math.max(start1.x,
                    end1.x))
                    && (y > Math.min(start1.y, end1.y) && y < Math.max(
                    start1.y, end1.y))) {
                // We are within the bounding box of the first line segment,
                // so now check second line segment
                if ((x > Math.min(start2.x, end2.x) && x < Math.max(start2.x,
                        end2.x))
                        && (y > Math.min(start2.y, end2.y) && y < Math.max(
                        start2.y, end2.y))) {
                    // The line segments do intersect
                    intersectx=x;
                    intersecty=y;
                    return true;
                }
            }

            // The lines do intersect, but the line segments do not
            return false;
        }
    }
    private double intersectx;
    private double intersecty;

}
