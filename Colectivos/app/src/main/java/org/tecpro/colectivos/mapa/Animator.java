package org.tecpro.colectivos.mapa;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.tecpro.colectivos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static org.tecpro.colectivos.utils.RecorridoEs.getRecorridoByName;

/**
 * Created by nico on 17/03/16.
 */
public class Animator implements Runnable {

    android.os.Handler mHandler = new android.os.Handler();
    private static final int ANIMATE_SPEEED = 1500;
    private final Interpolator interpolator = new LinearInterpolator();
    LatLng endLatLng = null;
    LatLng beginLatLng = null;
    Marker movingMarker;
    long start;
    ArrayList<LatLng> recorrido;
    int step = 0;

    public Animator(GoogleMap mMap, ArrayList<LatLng> recor) {
        recorrido = recor;
        if(recor!=null) {
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(recorrido);
            polylineOptions.color(Color.BLUE);
            polylineOptions.width(5);
            mMap.addPolyline(polylineOptions);
            endLatLng = recorrido.get(recorrido.size() - 1);
            beginLatLng = recorrido.get(0);
            movingMarker = mMap.addMarker(new MarkerOptions().position(recorrido.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
            start = SystemClock.uptimeMillis();
        }
    }

    @Override
    public void run() {
        if (step == recorrido.size()) {
            step = 0;
        }
        long elapsed = SystemClock.uptimeMillis() - start;
        double t = interpolator.getInterpolation((float) elapsed / ANIMATE_SPEEED);

        double lat = t * endLatLng.latitude + (1 - t) * beginLatLng.latitude;
        double lng = t * endLatLng.longitude + (1 - t) * beginLatLng.longitude;

        movingMarker.setPosition(recorrido.get(step));
        mHandler.postDelayed(this, 200);
        step ++;
    }

    public void stop() {
        if(movingMarker!=null)
            movingMarker.remove();
        mHandler.removeCallbacks(this);

    }

}