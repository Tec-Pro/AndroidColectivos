package org.tecpro.colectivos;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nico on 09/04/14.
 */
public class Mapa extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mapa;
    private Bundle extras;
    private boolean mostrandoLugares=false;
    private Recorrido lugares;
    private String title;
    private Handler handler;
    private Runnable runnable;
    int auxMark=0;
    double[] recorrido;
    LinkedList<Marker> markRe;
    int pos=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        GoogleMapOptions s= new GoogleMapOptions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        extras = getIntent().getExtras();
        title= extras.getString("title");


        this.setTitle("Linea "+title);
        int k = 0;
        mapa = null;
        while (k != 3 && mapa == null) {
            mapa = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapa)).getMap();
            k++;
        }
        if (mapa != null) {
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
            if (mapa.getMyLocation() != null) {
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 12));
            } else {
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(-33.12376, -64.349032), 12));
            }
            PolylineOptions a = new PolylineOptions();
            //linea 5!
            recorrido = extras.getDoubleArray("recorrido");
            int i = 0;
            while (i < recorrido.length - 1) {
                a.add(coord(recorrido[i], recorrido[i + 1]));
                i = i + 2;
            }
            a.color(Color.BLUE);
            a.width(2);
            mapa.addPolyline(a);
            lugares = new Recorrido();


            markRe= obtenerMarcadores();
            markRe.get(0).setVisible(true);
            handler = new Handler();
            handler.postDelayed(runnable,0);
            runnable = new Runnable() {
                @Override
                public void run() {

                    markRe.get(auxMark).setVisible(false);
                    auxMark++;
                    markRe.get(auxMark).setVisible(true);
                    if((markRe.size()-1)==(auxMark)){
                        markRe.get(auxMark).setVisible(false);
                        auxMark=0;
                        markRe.get(auxMark).setVisible(true);
                    }


                    handler.postDelayed(this, 30);
                }
            };
            runnable.run();

        } else {
            Toast.makeText(getApplicationContext(), "No se puedo cargar correctamente el mapa",
                    Toast. LENGTH_SHORT).show();
        }
        }




private LinkedList<Marker> obtenerMarcadores(){
        LinkedList<Marker> lst=new LinkedList<Marker>();
        int i=0;
         while (i < recorrido.length - 1) {
        lst.addLast(mapa.addMarker(new MarkerOptions().position(coord(recorrido[i], recorrido[i + 1])).visible(false)));
        i = i+2;
    }
    return lst;
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
        getMenuInflater().inflate(R.menu.mapa, menu);
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
            case R.id.accion_mostrar_parada:

                Toast.makeText(getApplicationContext(), "¡PRÓXIMAMENTE!", Toast.LENGTH_SHORT).show();
                /*double[] paradas = extras.getDoubleArray("paradas");
                if(paradas!=null) {
                    if(paradas.length % 2==0) {
                        i = 0;
                        while (i < paradas.length - 1) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(coord(paradas[i], paradas[i + 1]));
                            mapa.addMarker(markerOptions);
                            i = i + 2;
                        }
                    }
                }*/
                break;

            case R.id.accion_ocultar_parada:
                Toast.makeText(getApplicationContext(), "¡PRÓXIMAMENTE!", Toast.LENGTH_SHORT).show();
                /*mapa.clear();
                PolylineOptions a = new PolylineOptions();
                double[] recorrido = extras.getDoubleArray("recorrido");
                i = 0;
                while (i < recorrido.length - 1) {
                    a.add(coord(recorrido[i], recorrido[i + 1]));
                    i = i + 2;
                }
                a.color(Color.BLUE);
                a.width(2);
                mapa.addPolyline(a);*/
                break;
            case R.id.mostrar_lugares:
                if(mostrandoLugares) {
                    item.setTitle("Mostrar lugares");
                    mostrandoLugares=!mostrandoLugares;
                    mapa.clear();
                    markRe= obtenerMarcadores();
                    PolylineOptions a = new PolylineOptions();
                    double[] recorrido = extras.getDoubleArray("recorrido");
                    i = 0;
                    while (i < recorrido.length - 1) {
                        a.add(coord(recorrido[i], recorrido[i + 1]));
                        i = i + 2;
                    }
                    a.color(Color.BLUE);
                    a.width(2);
                    mapa.addPolyline(a);                }
                else{
                    item.setTitle("Ocultar lugares");
                    mostrandoLugares=!mostrandoLugares;
                    Object[] marcasLugares = lugares.getLugares();
                    if(marcasLugares!=null) {
                        if(marcasLugares.length % 3==0) {
                            i = 0;
                            while (i < marcasLugares.length - 2) {
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(coord((Double)marcasLugares[i+1], (Double)marcasLugares[i + 2]));
                                markerOptions.title((String) marcasLugares[i]);
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                                mapa.addMarker(markerOptions);
                                i = i + 3;
                            }
                    }
                }
                }




        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
            super.onPause();
    }

    @Override
    public void onResume() {
        handler.postDelayed(runnable, 0);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
