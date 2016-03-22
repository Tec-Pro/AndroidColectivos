package org.tecpro.colectivos.mapa;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import java.util.Locale;

import org.tecpro.colectivos.R;
import org.tecpro.colectivos.utils.GeoPunto;
import org.tecpro.colectivos.utils.RecorridoEs;

import static org.tecpro.colectivos.utils.RecorridoEs.getRecorridoByName;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback ,GoogleMap.OnMarkerDragListener  {

    private GoogleMap mMap;
    private Spinner spinner;
    boolean permission= true;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ListView listViewResultados;
    private AdaptadorResultados adapterResult;

    private TextView txtDesde, txtHasta;

    MarkerOptions markDesde; //Marcador desde Options
    MarkerOptions markHasta; //marcador hasta Options
    Marker desde;
    Marker hasta;
    CircleOptions circleDesdeOp;
    CircleOptions circleHastaOp;
    Circle circleDesde; //circulo desde
    Circle circleHasta; //circulo hasta
    int radio=300; //radio medido en metros, inicial 300, depende de perferencia
    TextView txtLineaMostrada;
    ArrayList<Pair<String, String>> lineasQueLlegan= new ArrayList<Pair<String,String>>(); //String de linas que llegan con distancia, [0] va el nombre, en pos que sigue la distancia en km
    Pair<String,Double> bestBus=new Pair<String, Double>("ninguno",99999999.0); //mejor linea y distancia INDIVIDUAL!
    ArrayList<LatLng> mejorRecorrido; //mejor recorrido, individuaaaal!
    Animator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setToolbar();
        spinner = (Spinner) findViewById(R.id.spn_streets);
        ArrayList<String> s = new ArrayList<>();
        s.add("Caminar hasta 1 cuadra");
        s.add("Caminar hasta 2 cuadras");
        s.add("Caminar hasta 3 cuadras");
        s.add("Caminar hasta 4 cuadras");
        s.add("Caminar hasta 5 cuadras");
        s.add("Caminar hasta 6 cuadras");
        s.add("Caminar hasta 7 cuadras");
        s.add("Caminar hasta 8 cuadras");
        s.add("Caminar hasta 9 cuadras");
        s.add("Caminar hasta 10 cuadras");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, s);
        spinner.setAdapter(adapter);
        loadNavigationDrawer();
        listViewResultados = (ListView) findViewById(R.id.list_view_result);
        adapterResult = new AdaptadorResultados(this, lineasQueLlegan);
        listViewResultados.setAdapter(adapterResult);
        listViewResultados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadLinea(lineasQueLlegan.get(position).first);
                drawerLayout.closeDrawer(Gravity.LEFT);

            }
        });
        txtDesde= (TextView) findViewById(R.id.txt_desde);
        txtHasta= (TextView) findViewById(R.id.txt_hasta);
        txtLineaMostrada = (TextView) findViewById(R.id.txt_linea_mostrada);
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                radio = position*100;
                updateMarker(desde);
                updateMarker(hasta);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadLinea(String linea){

        mMap.clear();
        desde = mMap.addMarker(markDesde);
        hasta = mMap.addMarker(markHasta);
        circleDesdeOp = new CircleOptions().center(desde.getPosition()).radius(radio).fillColor(Color.parseColor("#80F45555")).strokeWidth(0);
        circleHastaOp = new CircleOptions().center(hasta.getPosition()).radius(radio).fillColor(Color.parseColor("#807CE667")).strokeWidth(0);
        circleDesde = mMap.addCircle(circleDesdeOp);
        circleHasta = mMap.addCircle(circleHastaOp);

        txtLineaMostrada.setVisibility(View.VISIBLE);
        txtLineaMostrada.setText("Linea mostrada: " + linea);
        animator.stop();
        animator =new Animator(mMap, RecorridoEs.getRecorridoByName(linea));
        animator.run();
    }

    private void loadNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Integracion boton oficial
        toggle = new ActionBarDrawerToggle(
                this, // Activity
                drawerLayout, // Panel del Navigation Drawer
                R.drawable.ic_menu, // Icono que va a utilizar
                R.string.resultados, // Descripcion al abrir el drawer
                R.string.title_activity_maps // Descripcion al cerrar el drawer
        ) {

            public void onDrawerClosed(View view) {
                // Drawer cerrado
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // Drawer abierto
                //since the drawer might have opened as a results of
                //a click on the left menu, we need to make sure
                //to close it right after the drawer opens, so that
                //it is closed when the drawer is  closed.
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(toggle);
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
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
            //mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            if ( permission && mMap.getMyLocation() != null ) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 12));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(-33.12376, -64.349032), 12));
            }
        }
        markDesde = new MarkerOptions().title("Desde").draggable(true).position(new LatLng(-33.123714, -64.349025)).icon(BitmapDescriptorFactory.fromResource(R.drawable.desdee));
        markHasta = new MarkerOptions().title("Hasta").draggable(true).position(new LatLng(-33.104357, -64.335270)).icon(BitmapDescriptorFactory.fromResource(R.drawable.hasta));
        desde = mMap.addMarker(markDesde);
        hasta = mMap.addMarker(markHasta);
        circleDesdeOp = new CircleOptions().center(desde.getPosition()).radius(radio).fillColor(Color.parseColor("#80F45555")).strokeWidth(0);
        circleHastaOp = new CircleOptions().center(hasta.getPosition()).radius(radio).fillColor(Color.parseColor("#807CE667")).strokeWidth(0);
        circleDesde = mMap.addCircle(circleDesdeOp);
        circleHasta = mMap.addCircle(circleHastaOp);
        updateMarker(desde);
        updateMarker(hasta);
        mMap.setOnMarkerDragListener(this);
        animator =new Animator(mMap, RecorridoEs.getRecorridoByName(bestBus.first));
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

    @Override
    public void onMarkerDragEnd(Marker marker) {
        updateMarker(marker);


    }

    public void buscar(View view) throws IOException {
        new SearchTask(this,circleDesde.getCenter(),circleHasta.getCenter()).execute(txtDesde.getText().toString(),txtHasta.getText().toString());

    }


    private void buscarAux(LatLng desdeCenter,LatLng hastaCenter){
        ArrayList<Pair<String, ArrayList<LatLng>>> recorridosYNombres= RecorridoEs.recorridosYNombres(); //nombre y recorridos!
        bestBus=new Pair<String, Double>("ninguno",99999999.0); // el mejor no existe
        lineasQueLlegan= new ArrayList<>();
        int j=0;
        while(j<recorridosYNombres.size()){
            Pair<String,ArrayList<LatLng>> lin= recorridosYNombres.get(j);
            double dist=perteneceAlRadio(lin.second ,desdeCenter,radio,hastaCenter);
            if(dist!=-1){
                retornoMinimo(lin.first, dist, lin.second);
                DecimalFormat twoDForm = new DecimalFormat("#.00");
                Pair<String,String> lineaQueLlega = new Pair<>(lin.first,twoDForm.format(dist / 1000));
                lineasQueLlegan.add(lineaQueLlega);
            }
            j++;
        }
    }

    private double perteneceAlRadio (ArrayList<LatLng> recorrido, LatLng point,int radio,LatLng pointHasta){
        GeoPunto geo= new GeoPunto();
        double radioD=(0.0010736987954231836*radio)/100;
        int i = 0;
        double distancia=0 ;
        int aux=0;
        int aux2=0;
        double distAux=0;
        double mejorHastaMomento=99999999999.99;
        while (i < recorrido.size()-1 ) {
            if(geo.intersect(recorrido.get(i),recorrido.get(i+1), point.longitude, point.latitude, radioD)){
                LatLng puntoMedio= geo.puntoMedio(geo.getXaux1(), geo.getYaux1(), geo.getXaux2(), geo.getYaux2());
                // distancia= distancia +geo.distancia(puntoMedio.longitude,puntoMedio.latitude, reco[i + 2], reco[i + 3]);
                aux=i;
                boolean primerPasada= true;

                while(i!=aux ||primerPasada){


                    if(geo.intersect(recorrido.get(i),recorrido.get(i+1), pointHasta.longitude, pointHasta.latitude, radioD)){
                        LatLng puntoMedioHa= geo.puntoMedio(geo.getXaux1(), geo.getYaux1(), geo.getXaux2(), geo.getYaux2());
                        if(primerPasada){
                            distancia= geo.distancia(puntoMedio.longitude,puntoMedio.latitude,puntoMedioHa.longitude,puntoMedioHa.latitude);
                        }
                        else{
                            distancia= distancia +geo.distancia(recorrido.get(i).longitude,recorrido.get(i).latitude,puntoMedioHa.longitude,puntoMedioHa.latitude);
                        }
                        //distancia= distancia -geo.distancia(reco[i], reco[i + 1], reco[i + 2], reco[i + 3]);
                        // distancia= distancia +geo.distancia( reco[i], reco[i + 1],puntoMedioHa.longitude,puntoMedioHa.latitude);
                        if(distancia<=mejorHastaMomento) {
                            mejorHastaMomento = distancia;
                        }
                    }
                    else{
                        if(primerPasada)
                            distancia= distancia +geo.distancia(puntoMedio.longitude,puntoMedio.latitude, recorrido.get(i+1).longitude, recorrido.get(i+1).latitude);
                        else
                            distancia= distancia +geo.distancia(recorrido.get(i).longitude, recorrido.get(i).latitude, recorrido.get(i+1).longitude, recorrido.get(i+1).latitude);
                    }

                    i=i+2;
                    if(!(i<recorrido.size() - 1)){
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

    private void retornoMinimo(String linea, Double dist,ArrayList<LatLng> reco){
        if(dist< bestBus.second){
            bestBus= new Pair<String, Double>(linea,dist);
            mejorRecorrido=reco;
        }
    }

    public void getPositionDesde(View v) throws IOException {
        if(permission  && mMap.getMyLocation()!=null)
            new GeocoderTask(this,true,new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude())).execute();
        else
            showAlert();
    }

    public void getPositionHasta(View v) throws IOException {
        if(permission  && mMap.getMyLocation()!=null)
            new GeocoderTask(this,false,new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude())).execute();
        else
            showAlert();
    }


    public Address getAddressForLocation(Context context, LatLng location) throws IOException {
        if (location == null) {
            return null;
        }
        int maxResults = 1;

        Geocoder gc = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gc.getFromLocation(location.latitude, location.longitude, maxResults);

        if (addresses.size() >= 1) {
            return addresses.get(0);
        } else {
            return null;
        }
    }


    public LatLng getLocationForAddress(Context context, String location) throws IOException {
        if (location == null) {
            return null;
        }
        int maxResults = 1;
        Geocoder gc = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gc.getFromLocationName(location + " Río Cuarto", maxResults);
        if (addresses.size() >= 1) {
            return new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
        } else {
            return null;
        }
    }

    private void updateMarker(Marker marker){
        Address address = null;
        try {
            address =getAddressForLocation(this,marker.getPosition() );

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(marker.getTitle().equals("Desde")) {
            markDesde.position(marker.getPosition());
            circleDesde.setCenter(marker.getPosition());
            circleDesde.setRadius(radio);
            circleDesde.setFillColor(Color.parseColor("#80F45555"));
            circleDesde.setStrokeWidth(0);
            circleDesde.setVisible(true);
            if(address!=null)
                txtDesde.setText(address.getAddressLine(0));

        }
        else{
            markHasta.position(marker.getPosition());
            circleHasta.setCenter(marker.getPosition());
            circleHasta.setRadius(radio);
            circleHasta.setFillColor(Color.parseColor("#807CE667"));
            circleHasta.setStrokeWidth(0);
            circleHasta.setVisible(true);
            if(address!=null)
                txtHasta.setText(address.getAddressLine(0));

        }
    }
    public void swapDirs(View v){
        LatLng aux=  desde.getPosition();
        LatLng aux2 = hasta.getPosition();
        desde.setPosition(aux2);
        hasta.setPosition(aux);
        updateMarker(desde);
        updateMarker(hasta);

    }


    public void showAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("¿Usar el servicio de localización de Google?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Esta función necesita el permiso de localización activado.\nTú información será siempre anónima")
                .setCancelable(false)
                .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        Uri packageURI = Uri.parse("package:" + "org.tecpro.colectivos");
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", packageURI);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No activar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
            }
        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    private class SearchTask extends AsyncTask<String,Void,Pair<LatLng,LatLng>>{
        ProgressDialog pdia = new ProgressDialog(MapsActivity.this);
        String desdeString, hastaString;
        Context context;
        LatLng desdeCenter,hastaCenter;

        public SearchTask(Context context, LatLng desdeCenter, LatLng hastaCenter){
            this.context = context;
            this.desdeCenter = desdeCenter;
            this.hastaCenter = hastaCenter;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdia = new ProgressDialog(MapsActivity.this);
            pdia.setTitle("Por favor, espere.");
            pdia.setMessage("Realizando búsqueda de recorridos.");
            pdia.show();
        }

        @Override
        protected Pair<LatLng,LatLng> doInBackground(String... params) {
            try {
                desdeString =params[0];
                hastaString =params[1];
                buscarAux(desdeCenter,hastaCenter);
                LatLng desde= getLocationForAddress(context, params[0]);
                LatLng hasta=getLocationForAddress(context,params[1]);
                return  new Pair<LatLng,LatLng>(desde,hasta);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Pair<LatLng,LatLng> result) {
            adapterResult.setItems(lineasQueLlegan);
            mMap.clear();
            markDesde.position(result.first);
            markHasta.position(result.second);
            desde = mMap.addMarker(markDesde);
            hasta = mMap.addMarker(markHasta);
            circleDesdeOp = new CircleOptions().center(desde.getPosition()).radius(radio).fillColor(Color.parseColor("#80F45555")).strokeWidth(0);
            circleHastaOp = new CircleOptions().center(hasta.getPosition()).radius(radio).fillColor(Color.parseColor("#807CE667")).strokeWidth(0);
            circleDesde = mMap.addCircle(circleDesdeOp);
            circleHasta = mMap.addCircle(circleHastaOp);
            LatLng auxDesde=result.first;
            LatLng auxHasta=result.second;
            if(auxDesde!=null){
                desde.setPosition(auxDesde);
                updateMarker(desde);
                txtDesde.setText(desdeString);
            }
            if(auxHasta!=null){
                hasta.setPosition(auxHasta);
                updateMarker(hasta);
                txtHasta.setText(hastaString);
            }
            if((bestBus.first.equals("ninguno"))){
                // combinandoLineas();
                txtLineaMostrada.setVisibility(View.GONE);
                Snackbar.make(txtDesde, "No existen lineas directas para estas coordenadas",Snackbar.LENGTH_LONG).show();
                animator.stop();
            }else{
                txtLineaMostrada.setVisibility(View.VISIBLE);
                txtLineaMostrada.setText("Linea mostrada: " + bestBus.first);
                animator.stop();
                animator =new Animator(mMap, RecorridoEs.getRecorridoByName(bestBus.first));
                animator.run();
            }
            pdia.cancel();
        }
    }

    // An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, Address> {

        Context context;
        ProgressDialog pdia = new ProgressDialog(MapsActivity.this);
        boolean desdeBoolean;
        LatLng myLocation;

        public GeocoderTask(Context context,boolean desde, LatLng myLocation){
            this.context = context;
            this.desdeBoolean = desde;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdia = new ProgressDialog(MapsActivity.this);
            pdia.setTitle("Por favor, espere.");
            pdia.setMessage("Localizando... ");
            pdia.show();
        }

        @Override
        protected Address doInBackground(String... params) {
                try {
                    return getAddressForLocation(context, myLocation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            pdia.cancel();
            return null;
        }

        @Override
        protected void onPostExecute(Address address) {
            if(address!=null) {
                if (desdeBoolean) {
                    if (desde != null) {
                        desde.setPosition(new LatLng(address.getLatitude(), address.getLongitude()));
                        updateMarker(desde);
                        txtDesde.setText(address.getAddressLine(0));
                    }
                } else {
                    if (hasta != null) {
                        hasta.setPosition(new LatLng(address.getLatitude(), address.getLongitude()));
                        updateMarker(hasta);
                        txtHasta.setText(address.getAddressLine(0));
                    }
                }
                pdia.cancel();
            }
        }
    }
}

