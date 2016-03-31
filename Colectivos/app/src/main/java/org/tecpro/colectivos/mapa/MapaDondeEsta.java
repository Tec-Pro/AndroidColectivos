package org.tecpro.colectivos.mapa;

/**
 * Created by nico on 04/10/15.
 */

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.tecpro.colectivos.R;
import org.tecpro.colectivos.database.DataBaseHelper;
import org.tecpro.colectivos.model.ModelBondi;
import org.tecpro.colectivos.utils.ConsultasGPS;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static org.tecpro.colectivos.utils.RecorridoEs.getRecorridoByName;

public class MapaDondeEsta extends AppCompatActivity implements OnMapReadyCallback {

    ArrayList<LatLng> recorrido;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    static ArrayList<ModelBondi> coordenadas = new ArrayList<>();
    //estos parametros lo uso para llamar a la pagina
    private String linea,pCodigoLinea,pCodigoOrigen,pCodigoDestino;
    final Handler handler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {
            new AsyncCall().execute(linea,pCodigoLinea,pCodigoOrigen,pCodigoDestino);
        }
    };
    private TextView txtLastUpdate;
    //private DataBaseHelper manager;
    //ArrayList<Integer> coches = new ArrayList<>();
    private AdView adView;
    private ArrayList<Integer> coches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_donde_esta);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setAdMob();

        linea = getIntent().getExtras().getString("linea");
        pCodigoLinea = getIntent().getExtras().getString("pCodigoLinea");
        pCodigoOrigen = getIntent().getExtras().getString("pCodigoOrigen");
        pCodigoDestino = getIntent().getExtras().getString("pCodigoDestino");
        txtLastUpdate = (TextView) findViewById(R.id.txt_last_update);
        setToolbar();
    }

    private void setAdMob(){

        adView=(AdView) findViewById(R.id.adViewHorario);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        //.addTestDevice("A906482D0B3C5F47980E446DD6F1CF85")
                .build();


        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
        handler.removeCallbacks(r);

    }

    @Override
    public void onResume() {
        adView.resume();
        handler.post(r);

        super.onResume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
        handler.removeCallbacks(r);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap(LatLng latLong, String title) {
        mMap.addMarker(new MarkerOptions().position(latLong).title(linea + "-" + "Coche " + title).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            boolean permission = true;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                permission = false;
            }
            mMap.setMyLocationEnabled(permission);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            if (permission && mMap.getMyLocation() != null ) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 12));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(-33.12376, -64.349032), 12));
            }

            coordenadas = new ArrayList<>();


            new AsyncCallCoches().execute(linea);
            PolylineOptions a = new PolylineOptions();
            recorrido = getRecorridoByName(linea);
                a.addAll(recorrido);
            a.color(Color.BLUE);
            a.width(5);
            mMap.addPolyline(a);
        }
    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setSubtitle(linea);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }


    private class AsyncCall extends AsyncTask<String, Void,ArrayList<ModelBondi>> {

        public AsyncCall (){
        }

        @Override
        protected  ArrayList<ModelBondi> doInBackground(String... params) {

            //"Linea 1 Rojo","7","24","206"
            try {
                return ConsultasGPS.getHorarios(params[0],params[1],params[2],params[3]);
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
        protected void onPostExecute(ArrayList<ModelBondi> result) {
            coordenadas = result;
            txtLastUpdate.setVisibility(View.VISIBLE);
            if(!result.isEmpty()) {
                mMap.clear();
                PolylineOptions a = new PolylineOptions();
                a.addAll(recorrido);
                a.color(Color.BLUE);
                a.width(5);
                mMap.addPolyline(a);
                txtLastUpdate.setText("Última actualización: "+getActualTime());
            }else{
                txtLastUpdate.setText("Sin coches encontrados: "+getActualTime());

            }
            for(ModelBondi bondi: result) {
                if(coches.contains(bondi.getCoche()))
                    setUpMap(bondi.getLatLng(), String.valueOf(bondi.getCoche()));
            }

            handler.postDelayed(r, 3000);

        }
    }


    private class AsyncCallCoches extends AsyncTask<String, Void,ArrayList<Integer>> {

        public AsyncCallCoches (){
        }

        @Override
        protected  ArrayList<Integer> doInBackground(String... params) {

            //"Linea 1 Rojo","7","24","206"
            try {
                return ConsultasGPS.getCoches(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<Integer>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(ArrayList<Integer> result) {
            coches = result;
            if(!result.isEmpty()) {
                handler.postDelayed(r, 0);
            }
            else{
                showAlert();
            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int i = 0;
        switch (id) {
          //  case R.id.configurar_coches:
          //      lanzarConfigurarCoches();
           //     break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private String getActualTime(){
        Date dtFechaActual = new Date();
        DateFormat dfLocal = new SimpleDateFormat("HH:mm:ss");

        return dfLocal.format(dtFechaActual);
    }


    public void showAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Aviso");

        // set dialog message
        alertDialogBuilder
                .setMessage(("No se encuentran números de coches registrados, si deseás colaborar con un coche para esta linea, debés enviar a través de Facebook el número de coche interno que figura en el boleto como COCHE seguido de la línea a la que corresponde, nosotros lo agregaremos a la brevedad."))
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                        finishActivity();
                    }
                });



        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    private void finishActivity(){
        this.finish();
    }
}
