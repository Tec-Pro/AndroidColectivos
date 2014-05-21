package org.tecpro.colectivos;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
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
    double[] mejorRecorrido;
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
        Recorrido r= new Recorrido();
        Recorrido2 r2= new Recorrido2();
        Recorrido3 r3= new Recorrido3();
        Recorrido4 r4= new Recorrido4();
        Recorrido5 r5= new Recorrido5();
        //desde!
        mapFragment.eliminarRuta();
        int linea1v= perteneceAlRadio(r2.getRecorrido1Verde(),circleDesde.getCenter(),radio);
        int linea1r= perteneceAlRadio(r2.getRecorrido1Rojo(),circleDesde.getCenter(),radio);
        int linea2v= perteneceAlRadio(r2.getRecorrido2Verde(),circleDesde.getCenter(),radio);
        int linea2r= perteneceAlRadio(r2.getRecorrido2Rojo(),circleDesde.getCenter(),radio);
        int linea3= perteneceAlRadio(r2.getRecorrido3(),circleDesde.getCenter(),radio);
        int linea4= perteneceAlRadio(r4.getRecorrido4(),circleDesde.getCenter(),radio);
        int linea5= perteneceAlRadio(r4.getRecorrido5(),circleDesde.getCenter(),radio);
        int linea6= perteneceAlRadio(r4.getRecorrido6(),circleDesde.getCenter(),radio);
        int linea7= perteneceAlRadio(r4.getRecorrido7(),circleDesde.getCenter(),radio);
        int linea8v= perteneceAlRadio(r5.getRecorrido8Verde(),circleDesde.getCenter(),radio);
        int linea8r= perteneceAlRadio(r5.getRecorrido8Rojo(),circleDesde.getCenter(),radio);
        int linea9v= perteneceAlRadio(r3.getRecorrido9Verde(),circleDesde.getCenter(),radio);
        int linea9r= perteneceAlRadio(r4.getRecorrido9Rojo(),circleDesde.getCenter(),radio);
        int linea10= perteneceAlRadio(r3.getRecorrido10(),circleDesde.getCenter(),radio);
        int linea11= perteneceAlRadio(r3.getRecorrido11(),circleDesde.getCenter(),radio);
        int linea12= perteneceAlRadio(r3.getRecorrido12(),circleDesde.getCenter(),radio);
        int linea13= perteneceAlRadio(r3.getRecorrido13(),circleDesde.getCenter(),radio);
        int linea14= perteneceAlRadio(r5.getRecorrido14(),circleDesde.getCenter(),radio);
        int linea15= perteneceAlRadio(r.getRecorrido15(),circleDesde.getCenter(),radio);
        int linea16= perteneceAlRadio(r.getRecorrido16(),circleDesde.getCenter(),radio);
        int linea17= perteneceAlRadio(r.getRecorrido17(),circleDesde.getCenter(),radio);
        int linea18= perteneceAlRadio(r.getRecorrido18(),circleDesde.getCenter(),radio);
        mejor=new Pair<String, Double>("ninguno",99999999.0);
        PolylineOptions a = new PolylineOptions();
        lineasQueLlegan= new ArrayList<String>();
        if(linea1v !=-1) {
            Pair<Double,Integer> distlinea1v = perteneceAlRadioLlegada(r2.getRecorrido1Verde(), circleHasta.getCenter(), radio, linea1v);
            if(distlinea1v.first!=-1){
                System.out.println("Me lleva el 1v");
                retornoMinimo("1v",distlinea1v.first);
                lineasQueLlegan.add("LINEA 1 VERDE");
            }

        }
        if(linea1r !=-1) {
            Pair<Double,Integer> distlinea1r = perteneceAlRadioLlegada(r2.getRecorrido1Rojo(), circleHasta.getCenter(), radio, linea1r);
            if(distlinea1r.first!=-1){
                System.out.println("Me lleva el 1r");
                retornoMinimo("1r",distlinea1r.first);
                lineasQueLlegan.add("LINEA 1 ROJO");
            } }
        if(linea2v !=-1) {
            Pair<Double,Integer> distlinea2v= perteneceAlRadioLlegada(r2.getRecorrido2Verde(),circleHasta.getCenter(),radio,linea2v);
            if(distlinea2v.first!=-1){
                System.out.println("Me lleva el 2v");
                retornoMinimo("2v",distlinea2v.first);
                lineasQueLlegan.add("LINEA 2 VERDE");
            }
        }if(linea2r !=-1) {
            Pair<Double,Integer> distlinea2r= perteneceAlRadioLlegada(r2.getRecorrido2Rojo(),circleHasta.getCenter(),radio,linea2r);
            if(distlinea2r.first!=-1){
                System.out.println("Me lleva el 2r");
                retornoMinimo("2r",distlinea2r.first);
                lineasQueLlegan.add("LINEA 2 ROJO");
            }
        }
        if(linea3 !=-1) {
            Pair<Double,Integer> distlinea3= perteneceAlRadioLlegada(r2.getRecorrido3(),circleHasta.getCenter(),radio,linea3);
            if(distlinea3.first!=-1){
                System.out.println("Me lleva el 3");
                retornoMinimo("3",distlinea3.first);
                lineasQueLlegan.add("LINEA 3");
            }
        }
        if(linea4 !=-1) {
            Pair<Double,Integer> distlinea4= perteneceAlRadioLlegada(r4.getRecorrido4(),circleHasta.getCenter(),radio,linea4);
            if(distlinea4.first!=-1){
                System.out.println("Me lleva el 4");
                retornoMinimo("4",distlinea4.first);
                lineasQueLlegan.add("LINEA 4");
            }}
        if(linea5 !=-1) {
            Pair<Double,Integer> distlinea5= perteneceAlRadioLlegada(r4.getRecorrido5(),circleHasta.getCenter(),radio,linea5);
            if(distlinea5.first!=-1){
                System.out.println("Me lleva el 5");
                retornoMinimo("5",distlinea5.first);
                lineasQueLlegan.add("LINEA 5");
            }}

        if(linea6 !=-1) {
            Pair<Double,Integer> distlinea6= perteneceAlRadioLlegada(r4.getRecorrido6(),circleHasta.getCenter(),radio,linea6);
            if(distlinea6.first!=-1){
                System.out.println("Me lleva el 6");
                retornoMinimo("6",distlinea6.first);
                lineasQueLlegan.add("LINEA 6");
            }}
        if(linea7 !=-1) {
            Pair<Double,Integer> distlinea7= perteneceAlRadioLlegada(r4.getRecorrido7(),circleHasta.getCenter(),radio,linea7);
            if(distlinea7.first!=-1){
                System.out.println("Me lleva el 7");
                retornoMinimo("7",distlinea7.first);
                lineasQueLlegan.add("LINEA 7");
            }}
        if(linea8v !=-1) {
            Pair<Double,Integer> distlinea8v= perteneceAlRadioLlegada(r5.getRecorrido8Verde(),circleHasta.getCenter(),radio,linea8v);
            if(distlinea8v.first!=-1){
                System.out.println("Me lleva el 8v");
                retornoMinimo("8v",distlinea8v.first);
                lineasQueLlegan.add("LINEA 8 VERDE");
            }}
        if(linea8r !=-1) {
            Pair<Double,Integer> distlinea8r= perteneceAlRadioLlegada(r5.getRecorrido8Rojo(),circleHasta.getCenter(),radio,linea8r);
            if(distlinea8r.first!=-1){
                System.out.println("Me lleva el 8r");
                retornoMinimo("8r",distlinea8r.first);
                lineasQueLlegan.add("LINEA 8 ROJO");
            }}

        if(linea9v !=-1) {
            Pair<Double,Integer> distlinea9v= perteneceAlRadioLlegada(r3.getRecorrido9Verde(),circleHasta.getCenter(),radio,linea9v);
            if(distlinea9v.first!=-1){
                System.out.println("Me lleva el 9v");
                retornoMinimo("9v",distlinea9v.first);
                lineasQueLlegan.add("LINEA 9 VERDE");
            }}
        if(linea9r !=-1) {
            Pair<Double,Integer> distlinea9r= perteneceAlRadioLlegada(r4.getRecorrido9Rojo(),circleHasta.getCenter(),radio,linea9r);
            if(distlinea9r.first!=-1){
                System.out.println("Me lleva el 9r");
                retornoMinimo("9r",distlinea9r.first);
                lineasQueLlegan.add("LINEA 9 ROJO");
            }}
        if(linea10 !=-1) {
            Pair<Double,Integer> distlinea10= perteneceAlRadioLlegada(r3.getRecorrido10(),circleHasta.getCenter(),radio,linea10);
            if(distlinea10.first!=-1){
                System.out.println("Me lleva el 10");
                retornoMinimo("10",distlinea10.first);
                lineasQueLlegan.add("LINEA 10");
            }}
        if(linea11 !=-1) {
            Pair<Double,Integer> distlinea11= perteneceAlRadioLlegada(r3.getRecorrido11(),circleHasta.getCenter(),radio,linea11);
            if(distlinea11.first!=-1){
                System.out.println("Me lleva el 11");
                retornoMinimo("11",distlinea11.first);
                lineasQueLlegan.add("LINEA 11");
            }}
        if(linea12 !=-1) {
            Pair<Double,Integer> distlinea12= perteneceAlRadioLlegada(r3.getRecorrido12(),circleHasta.getCenter(),radio,linea12);
            if(distlinea12.first!=-1){
                System.out.println("Me lleva el 12");
                retornoMinimo("12",distlinea12.first);
                lineasQueLlegan.add("LINEA 12");
            }}
        if(linea13 !=-1) {
            Pair<Double,Integer> distlinea13= perteneceAlRadioLlegada(r3.getRecorrido13(),circleHasta.getCenter(),radio,linea13);
            if(distlinea13.first!=-1){
                System.out.println("Me lleva el 13");
                retornoMinimo("13",distlinea13.first);
                lineasQueLlegan.add("LINEA 13");
            }}
        if(linea14 !=-1) {
            Pair<Double,Integer> distlinea14= perteneceAlRadioLlegada(r5.getRecorrido14(),circleHasta.getCenter(),radio,linea14);
            if(distlinea14.first!=-1){
                System.out.println("Me lleva el 14");
                retornoMinimo("14",distlinea14.first);
                lineasQueLlegan.add("LINEA 14");
            }}

        if(linea15 !=-1) {
            Pair<Double,Integer> distlinea15= perteneceAlRadioLlegada(r.getRecorrido15(),circleHasta.getCenter(),radio,linea15);
            if(distlinea15.first!=-1){
                System.out.println("Me lleva el 15");
                retornoMinimo("15", distlinea15.first);
                lineasQueLlegan.add("LINEA 15");
            }}

        if(linea16 !=-1) {
            Pair<Double,Integer> distlinea16= perteneceAlRadioLlegada(r.getRecorrido16(),circleHasta.getCenter(),radio,linea16);
            if(distlinea16.first!=-1){
                System.out.println("Me lleva el 16");
                retornoMinimo("16",distlinea16.first);
                lineasQueLlegan.add("LINEA 16");
            }}
        if(linea17 !=-1) {
            Pair<Double,Integer> distlinea17= perteneceAlRadioLlegada(r.getRecorrido17(),circleHasta.getCenter(),radio,linea17);
            if(distlinea17.first!=-1){
                System.out.println("Me lleva el 17");
                retornoMinimo("17",distlinea17.first);
                lineasQueLlegan.add("LINEA 17");
            }}
        if(linea18 !=-1) {
            Pair<Double,Integer> distlinea18= perteneceAlRadioLlegada(r.getRecorrido18(),circleHasta.getCenter(),radio,linea18);
            if(distlinea18.first!=-1){
                System.out.println("Me lleva el 18");
                retornoMinimo("18",distlinea18.first);
                lineasQueLlegan.add("LINEA 18");
            }}

            if(mejor.first.equals("1v")) {
                System.out.println("el mejor es el" + mejor.first);
                mejorRecorrido=r2.getRecorrido1Verde();
            }
            if(mejor.first.equals("1r")){
                System.out.println("el mejor es el"+ mejor.first);
                mejorRecorrido=r2.getRecorrido1Rojo();
            }
            if(mejor.first.equals("2v"))
                mejorRecorrido=r2.getRecorrido2Verde();
            if(mejor.first.equals("2r"))
                mejorRecorrido=r2.getRecorrido2Rojo();
            if(mejor.first.equals("3"))
                mejorRecorrido=r2.getRecorrido3();
            if(mejor.first.equals("4"))
                mejorRecorrido=r4.getRecorrido4();
            if(mejor.first.equals("5"))
                mejorRecorrido=r4.getRecorrido5();
            if(mejor.first.equals("6"))
                mejorRecorrido=r4.getRecorrido6();
            if(mejor.first.equals("7"))
                mejorRecorrido=r4.getRecorrido7();
            if(mejor.first.equals("8v"))
                mejorRecorrido=r5.getRecorrido8Verde();
            if(mejor.first.equals("8r"))
                mejorRecorrido=r5.getRecorrido8Rojo();
            if(mejor.first.equals("9v"))
                mejorRecorrido=r3.getRecorrido9Verde();
            if(mejor.first.equals("9r"))
                mejorRecorrido=r4.getRecorrido9Rojo();
            if(mejor.first.equals("10"))
                mejorRecorrido=r3.getRecorrido10();
            if(mejor.first.equals("11"))
                mejorRecorrido=r3.getRecorrido11();
            if(mejor.first.equals("12"))
                mejorRecorrido=r3.getRecorrido12();
            if(mejor.first.equals("13"))
                mejorRecorrido=r3.getRecorrido13();
            if(mejor.first.equals("14"))
                mejorRecorrido=r5.getRecorrido14();
            if(mejor.first.equals("15"))
                mejorRecorrido=r.getRecorrido15();
            if(mejor.first.equals("16"))
                mejorRecorrido=r.getRecorrido16();
            if(mejor.first.equals("17"))
                mejorRecorrido=r.getRecorrido17();
            if(mejor.first.equals("18"))
                mejorRecorrido=r.getRecorrido18();
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


    private void retornoMinimo(String linea, Double dist){
        if(dist< mejor.second){
            mejor= new Pair<String, Double>(linea,dist);
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


    private int perteneceAlRadio (double[] reco, LatLng point,int radio){
        GeoPunto geo= new GeoPunto();
        int ret= -1;
        LatLng latlong;
        int i = 0;
        while (i < reco.length - 1 ) {
            latlong=coord(reco[i], reco[i + 1]);
            if(geo.distancia(point.longitude,point.latitude,latlong.longitude,latlong.latitude)<radio){
                return i;
            }
            i = i + 2;
        }
        return ret;
    }

    private Pair<Double,Integer> perteneceAlRadioLlegada (double[] reco, LatLng point,int radio, int desde){
        GeoPunto geo= new GeoPunto();
        double distancia= 0;
        LatLng latlong;
        int i = desde;
        while (i < reco.length - 1 ) {
            latlong=coord(reco[i], reco[i + 1]);
            distancia= distancia +geo.distancia(point.longitude,point.latitude,latlong.longitude,latlong.latitude);
            if(geo.distancia(point.longitude,point.latitude,latlong.longitude,latlong.latitude)<radio){
                return new Pair<Double, Integer>(distancia,i);
            }
            i = i + 2;
        }
        return new Pair<Double, Integer>(-1.0, -1);
    }

    //me canse de escribir mucho
    private double[] retornarLinea(String num){
        Recorrido r= new Recorrido();
        Recorrido2 r2= new Recorrido2();
        Recorrido3 r3= new Recorrido3();
        Recorrido4 r4= new Recorrido4();
        Recorrido5 r5= new Recorrido5();
        if(num.equals("LINEA 1 VERDE"))
            return r2.getRecorrido1Verde();
        if(num.equals("LINEA 1 ROJO"))
            return r2.getRecorrido1Rojo();
        if(num.equals("LINEA 2 VERDE"))
            return r2.getRecorrido2Verde();
        if(num.equals("LINEA 2 ROJO"))
            return r2.getRecorrido2Rojo();
        if(num.equals("LINEA 3"))
            return r2.getRecorrido3();
        if(num.equals("LINEA 4"))
            return r4.getRecorrido4();
        if(num.equals("LINEA 5"))
            return r4.getRecorrido5();
        if(num.equals("LINEA 6"))
            return  r4.getRecorrido6();
        if(num.equals("LINEA 7"))
            return r4.getRecorrido7();
        if(num.equals("LINEA 8 VERDE"))
            return r5.getRecorrido8Verde();
        if(num.equals("LINEA 8 ROJO"))
            return r5.getRecorrido8Rojo();
        if(num.equals("LINEA 9 VERDE"))
            return r3.getRecorrido9Verde();
        if(num.equals("LINEA 9 ROJO"))
            return r4.getRecorrido9Rojo();
        if(num.equals("LINEA 10"))
            return r3.getRecorrido10();
        if(num.equals("LINEA 11"))
            return r3.getRecorrido11();
        if(num.equals("LINEA 12"))
            return r3.getRecorrido12();
        if(num.equals("LINEA 13"))
            return r3.getRecorrido13();
        if(num.equals("LINEA 14"))
            return r5.getRecorrido14();
        if(num.equals("LINEA 15"))
            return r.getRecorrido15();
        if(num.equals("LINEA 16"))
            return r.getRecorrido16();
        if(num.equals("LINEA 17"))
            return r.getRecorrido17();
        if(num.equals("LINEA 18"))
            return r.getRecorrido18();
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
}
