package org.tecpro.colectivos;

/**
 * Created by nico on 10/04/14.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeleccionLineasMediaDist extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Horarios horarios;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_linea);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();


        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
                lanzar(groupPosition,childPosition);
                //lanzarHorarios(groupPosition, childPosition);
                return true;
            }
        });



        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        horarios = new Horarios();
        adView=(AdView) findViewById(R.id.adViewLinea);

        //adView = new AdView(this);
        //adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId(AD_UNIT_ID);

        // Buscar LinearLayout suponiendo que se le ha asignado
        // el atributo android:id="@+id/mainLayout".

        // Añadirle adView.
        //layout.addView(adView);

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


    public void lanzar(int grupo, int hijo){
        String selectedHijo = (String) listAdapter.getChild(grupo, hijo);
        String selectedGroup = (String) listAdapter.getGroup(grupo);
        selectedGroup= selectedGroup.substring(6);
        selectedHijo= selectedHijo.substring(3);
        String nomLinea= selectedGroup;
        String title=selectedGroup+"-"+ selectedHijo;
        //Toast.makeText(getBaseContext(),selectedGroup+" - "+ selectedHijo, Toast.LENGTH_LONG).show();

        Intent IntentHorario;
        switch (grupo){
            case 0: //Río Cuarto-Villa Maria
                switch (hijo){
                    case 0://Todos dias
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeaderRioVillaM());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeRioVillaM());
                        IntentHorario.putExtra("title","Río IV-V. Maria");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                        break;
                }
                break;
            case 1: //Río Cuarto-Villa Maria
                switch (hijo){
                    case 0://Todos dias
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeaderVillaMRio());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeVillaMRio());
                        IntentHorario.putExtra("title","V. Maria- Río IV");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                        break;
                }
                break;

            case 2: //Río cuarto-C. Moldes
                switch (hijo){
                    case 0://lunes - viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStopsRioCuartoMoldes());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeTableRioCuartoMoldes());
                        IntentHorario.putExtra("title","Río IV-Moldes Lun-Vier");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                            break;
                    case 1://sabado
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStopsRioCuartoMoldes());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeTableRioCuartoMoldesSab());
                        IntentHorario.putExtra("title","Río IV-Moldes Sab");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                        break;
                    case 2://domingos
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStopsRioCuartoMoldes());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeTableRioCuartoMoldesDom());
                        IntentHorario.putExtra("title","Río IV-Moldes Dom y fer");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                        break;
                }
                break;
            case 3: //C. Moldes - Rio Cuarto
                switch (hijo){
                    case 0://lunes - viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStopseMoldesRioCuarto());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeTableMoldesRio());
                        IntentHorario.putExtra("title","Río IV-Moldes Lun-Vier");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                        break;
                    case 1://sabado
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStopseMoldesRioCuarto());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeTableMoldesRioSab());
                        IntentHorario.putExtra("title","Río IV-Moldes Sab");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                        break;
                    case 2://domingos
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStopseMoldesRioCuarto());
                        IntentHorario.putExtra("timeTableMoldes",horarios.getTimeTableMoldesRioDom());
                        IntentHorario.putExtra("title","Río IV-Moldes Dom y fer");
                        IntentHorario.putExtra("cantBondis",1);
                        startActivity(IntentHorario);
                        break;
                }
                break;




        }
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Río Cuarto- Villa María");
        listDataHeader.add("Villa María- Río Cuarto");
        listDataHeader.add("Río Cuarto - C. Moldes");
        listDataHeader.add("C. Moldes -  Río Cuarto");




        // Adding child data
        List<String> rioCuartoVillaMaria = new ArrayList<String>();
        rioCuartoVillaMaria.add("   Todos los días");

        // Adding child data
        List<String> villaRio = new ArrayList<String>();
        villaRio.add("   Todos los días");


        List<String> rioCuartoCMoldes = new ArrayList<String>();
        rioCuartoCMoldes.add("   Lunes a viernes");
        rioCuartoCMoldes.add("   Sábados");
        rioCuartoCMoldes.add("   Domingos y feriados ");
        List<String> moldesRioCuarto = new ArrayList<String>();
        moldesRioCuarto.add("   Lunes a viernes");
        moldesRioCuarto.add("   Sábados");
        moldesRioCuarto.add("   Domingos y feriados ");


        listDataChild.put(listDataHeader.get(0), rioCuartoVillaMaria); // Header, Child data
        listDataChild.put(listDataHeader.get(1), villaRio); // Header, Child data
        listDataChild.put(listDataHeader.get(2), rioCuartoCMoldes); // Header, Child data
        listDataChild.put(listDataHeader.get(3), moldesRioCuarto); // Header, Child data



    }


}
