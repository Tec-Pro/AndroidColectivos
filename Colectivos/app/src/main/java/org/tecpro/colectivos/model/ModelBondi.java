package org.tecpro.colectivos.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by nico on 20/03/16.
 */
public class ModelBondi {


    int coche;
    String linea;
    LatLng latLng;

    public ModelBondi(int coche, String linea) {

        this.coche = coche;
        this.linea = linea;
    }

    public ModelBondi(){

    }


    public String getLinea() {
        return linea;
    }

    public int getCoche() {
        return coche;
    }



    public void setLinea(String linea) {
        this.linea = linea;
    }

    public void setCoche(int coche) {
        this.coche = coche;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setLatLng(double latitude, double longitude){
        latLng =new LatLng(latitude,longitude);
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
