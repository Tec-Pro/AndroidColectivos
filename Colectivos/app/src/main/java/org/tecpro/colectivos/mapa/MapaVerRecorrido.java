package org.tecpro.colectivos.mapa;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.tecpro.colectivos.R;
import org.tecpro.colectivos.utils.RecorridoEs;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.tecpro.colectivos.utils.RecorridoEs.getLugares;
import static org.tecpro.colectivos.utils.RecorridoEs.getRecorridoByName;


/**
 * Created by nico on 09/04/14.
 */
public class MapaVerRecorrido extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener , OnMapReadyCallback {
    private GoogleMap mMap;
    private boolean mostrandoLugares = false;
    private String linea;
    ArrayList<LatLng> recorrido;
    private SupportMapFragment mapFragment;
    private PolylineOptions polylineOptions;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_recorrido);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        linea = getIntent().getExtras().getString("linea");
        recorrido = getRecorridoByName(linea);
        setToolbar();
        setAdMob();

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
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.mostrar_lugares:
                if(mostrandoLugares) {
                    item.setTitle("Mostrar lugares");
                    mostrandoLugares=!mostrandoLugares;
                    mMap.clear();
                    mMap.addPolyline(polylineOptions);
                }
                else{
                    item.setTitle("Ocultar lugares");
                    mostrandoLugares=!mostrandoLugares;
                    ArrayList<Pair<String,LatLng>> marcasLugares = getLugares();
                    for(Pair<String,LatLng> lugar: marcasLugares){
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(lugar.second);
                        markerOptions.title(lugar.first);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
                        mMap.addMarker(markerOptions);
                    }
                }
                break;
            case R.id.action_bar_toggle_style:
                mMap.setMapType(mMap.getMapType() ==GoogleMap.MAP_TYPE_NORMAL? GoogleMap.MAP_TYPE_SATELLITE : GoogleMap.MAP_TYPE_NORMAL);
                break;

        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        adView.resume();

        super.onResume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
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
            if (permission &&  mMap.getMyLocation() != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 12));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(-33.12376, -64.349032), 12));
            }
            polylineOptions = new PolylineOptions();
            polylineOptions.addAll(recorrido);
            polylineOptions.color(Color.BLUE);
            polylineOptions.width(5);
            mMap.addPolyline(polylineOptions);
            new Animator(mMap,recorrido).run();
        }
    }
}
