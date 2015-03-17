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

public class SeleccionLineas extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private RecorridoEs recorrido;
    private Horarios horarios;
    private Info info;
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
        recorrido= new RecorridoEs();
        horarios = new Horarios();

        info= new Info();

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
        Intent IntentMapa;
        switch (grupo){
            case 0: //1 verde
                switch (hijo){
                    case 0: //lunes-viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader1verde());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable1verde());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi1V());
                        IntentHorario.putExtra("info",info.getInfo1V());
                        startActivity(IntentHorario);
                        break;
                    case 1: //Especiales
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getGetBusStops1verdeE());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable1verdeE());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo1V());
                        startActivity(IntentHorario);
                        break;
                    case 2: //sabados, dom y feri
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops1verdeSyD());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable1verdeSyD());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo1V());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi1V());

                        startActivity(IntentHorario);
                        break;
                    case 3://recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido1Verde());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 1: //1 rojo
                switch (hijo){
                    case 0://lunes a viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops1rojo());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable1rojo());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo1R());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi1R());
                        startActivity(IntentHorario);
                        break;
                    case 1://Especiales
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops1rojoE());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable1rojoE());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo1R());
                        startActivity(IntentHorario);
                        break;
                    case 2://sabados, dom y feri
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops1rojoSyD());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable1rojoSyD());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo1R());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi1R());
                        startActivity(IntentHorario);
                        break;
                    case 3: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido1Rojo());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 2://2 verde
                switch (hijo){
                    case 0: //lunes-viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops2verde());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable2verde());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo2V());
                        startActivity(IntentHorario);
                        break;
                    case 1://Especiales
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops2verdeE());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable2verdeE());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo2V());
                        startActivity(IntentHorario);
                        break;
                    case 2://sabados, dom y feri
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops2verdeSyD());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable2verdeSyD());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo2V());
                        startActivity(IntentHorario);
                        break;
                    case 3://recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido2Verde());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 3://2 rojo
                switch (hijo){
                    case 0://lunes a viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops2rojo());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable2rojo());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo2R());
                        startActivity(IntentHorario);
                        break;
                    case 1://sabados, dom y feri
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops2rojo());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable2rojoSyD());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo2R());
                        startActivity(IntentHorario);
                        break;
                    case 2: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido2Rojo());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 4: //3
                switch (hijo){
                    case 0://lunes a viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops3());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable3());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo3());
                        startActivity(IntentHorario);
                        break;
                    case 1://Especial
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops3E());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable3E());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo3());
                        startActivity(IntentHorario);
                        break;
                    case 2: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido3());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 5: //4
                switch (hijo){
                    case 0://lunes a viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops4());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable4());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo4());
                        startActivity(IntentHorario);
                        break;
                    case 1: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido4());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 6: //5
                switch (hijo){
                    case 0://lunes a viernes
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops5());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable5());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo5());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi5());
                        startActivity(IntentHorario);
                        break;

                    case 1://Especiales
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops5E());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable5E());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo5());
                        startActivity(IntentHorario);
                        break;
                    case 2://sabados, dom y feri
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops5SyD());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable5SyD());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo5());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi5Fin());
                        startActivity(IntentHorario);
                        break;
                    case 3://Verano
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getBusStops5SyD());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable5V());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo5());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi5Ver());

                        startActivity(IntentHorario);
                        break;
                    case 4: //recorrido
                        Intent i = new Intent(this,Mapa.class);
                        i.putExtra("recorrido",recorrido.getRecorrido5());
                        i.putExtra("title",title);
                        startActivity(i);
                        break;
                }
                break;
            case 7: //6
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader6());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable6());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi6());
                        IntentHorario.putExtra("info",info.getInfo6());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader6Esp());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable6Esp());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi6Esp());
                        IntentHorario.putExtra("info",info.getInfo6());
                        startActivity(IntentHorario);
                        break;
                    case 2: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido6());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 8: //7
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader7());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable7());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi7());
                        IntentHorario.putExtra("info",info.getInfo7());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader7Fin());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable7Fin());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi7Fin());
                        IntentHorario.putExtra("info",info.getInfo7());
                        startActivity(IntentHorario);
                        break;
                    case 2: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido7());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 9: //8 verde
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader8V());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable8V());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi8V());
                        IntentHorario.putExtra("info",info.getInfo8v());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader8VSab());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable8VSab());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi8VFin());
                        IntentHorario.putExtra("info",info.getInfo8v());
                        startActivity(IntentHorario);
                        break;
                    case 2: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido8Verde());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 10://8 rojo
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader8R());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable8R());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi8R());
                        IntentHorario.putExtra("info",info.getInfo8R());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader8RSab());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable8RSab());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi8RSab());
                        IntentHorario.putExtra("info",info.getInfo8R());
                        startActivity(IntentHorario);
                        break;
                    case 2:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader8RDom());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable8RDom());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi8RDom());
                        IntentHorario.putExtra("info",info.getInfo8R());
                        startActivity(IntentHorario);
                        break;
                    case 3: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido8Rojo());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 11: //9 verde
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader9V());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable9V());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi9V());
                        IntentHorario.putExtra("info",info.getInfo9V());
                        startActivity(IntentHorario);
                        break;
		    case 1: IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader9VSDF());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable9VSDF());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi9V());
                        IntentHorario.putExtra("info",info.getInfo9V());
                        startActivity(IntentHorario);
			 break;
                    case 2: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido", recorrido.getRecorrido9Verde());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 12: //9 rojo
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader9R());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable9R());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi9R());
                        IntentHorario.putExtra("info",info.getInfo9R());
                        startActivity(IntentHorario);
                        break;
 		    case 1: IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader9RSDF());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable9RSDF());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi9R());
                        IntentHorario.putExtra("info",info.getInfo9R());
                        startActivity(IntentHorario);
 			break;
                    case 2: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido9Rojo());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 13: //10
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader10());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable10());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi10());
                        IntentHorario.putExtra("info",info.getInfo10());
                        startActivity(IntentHorario);
                        break;
                    case 1://reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido", recorrido.getRecorrido10());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 14://11
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader11());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable11());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi11());
                        IntentHorario.putExtra("info",info.getInfo11());
                        startActivity(IntentHorario);
                        break;
                    case 1: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido", recorrido.getRecorrido11());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 15://12
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader12());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable12());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi12());
                        IntentHorario.putExtra("info",info.getInfo12());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader12());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable12Sab());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo12());
                        startActivity(IntentHorario);
                        break;
                    case 2:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader12());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable12Dom());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo12());
                        startActivity(IntentHorario);
                        break;
                    case 3: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido", recorrido.getRecorrido12());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 16://13
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader13());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable13());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi13());
                        IntentHorario.putExtra("info",info.getInfo13());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader13Fin());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable13Fin());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi13Fin());
                        IntentHorario.putExtra("info",info.getInfo13());
                        startActivity(IntentHorario);
                        break;
                    case 2://reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido", recorrido.getRecorrido13());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 17://14
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader14());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable14());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo14());
                        startActivity(IntentHorario);
                        break;
                    case 1: //reco:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido14());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 18://15
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader15());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable15());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo15());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido15());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 19: //16
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader16());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable16());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo16());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido16());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 20://17
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader17());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable17());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo17());
                        startActivity(IntentHorario);
                        break;
                    case 1:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido17());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 21://18
                switch (hijo){
                    case 0:
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader18());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable18());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo18());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi18());
                        startActivity(IntentHorario);
                        break;
                    case 1: //especiales
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader18());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable18Esp());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo18());
                        startActivity(IntentHorario);
                        break;
                    case 2: //Fin
                        IntentHorario = new Intent(this,VistaHoriarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader18());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable18Fin());
                        IntentHorario.putExtra("title",title);
                        IntentHorario.putExtra("info",info.getInfo18());
                        IntentHorario.putExtra("cantBondis",horarios.getCantBondi18());
                        startActivity(IntentHorario);
                        break;
                    case 3://reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido18());
                        IntentMapa.putExtra("title",nomLinea);
                        startActivity(IntentMapa);
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
        listDataHeader.add("Linea 1 verde");
        listDataHeader.add("Linea 1 rojo");
        listDataHeader.add("Linea 2 verde");
        listDataHeader.add("Linea 2 rojo");
        listDataHeader.add("Linea 3");
        listDataHeader.add("Linea 4");
        listDataHeader.add("Linea 5");
        listDataHeader.add("Linea 6");
        listDataHeader.add("Linea 7");
        listDataHeader.add("Linea 8 verde");
        listDataHeader.add("Linea 8 rojo");
        listDataHeader.add("Linea 9 verde");
        listDataHeader.add("Linea 9 rojo");
        listDataHeader.add("Linea 10");
        listDataHeader.add("Linea 11");
        listDataHeader.add("Linea 12");
        listDataHeader.add("Linea 13");
        listDataHeader.add("Linea 14");
        listDataHeader.add("Linea 15");
        listDataHeader.add("Linea 16");
        listDataHeader.add("Linea 17");
        listDataHeader.add("Linea 18");



        // Adding child data
        List<String> linea1Verde = new ArrayList<String>();
        linea1Verde.add("   lunes a viernes");
        linea1Verde.add("   Especiales");
        linea1Verde.add("   Sábados, domingos y feriados ");
        linea1Verde.add("   Recorrido");

        List<String> linea1Rojo = new ArrayList<String>();
        linea1Rojo.add("   Lunes a viernes");
        linea1Rojo.add("   Especiales");
        linea1Rojo.add("   Sábados, domingos y feriados ");
        linea1Rojo.add("   Recorrido");

        List<String> linea2Verde = new ArrayList<String>();
        linea2Verde.add("   Lunes a viernes");
        linea2Verde.add("   Especiales");
        linea2Verde.add("   Sábados, domingos y feriados ");
        linea2Verde.add("   Recorrido");

        List<String> linea2Rojo = new ArrayList<String>();
        linea2Rojo.add("   Lunes a viernes");
        linea2Rojo.add("   Sábados, domingos y feriados ");
        linea2Rojo.add("   Recorrido");

        List<String> linea3 = new ArrayList<String>();
        linea3.add("   Lunes a domingo");
        linea3.add("   Especiales");
        linea3.add("   Recorrido");

        List<String> linea4 = new ArrayList<String>();
        linea4.add("   Lunes a domingo");
        linea4.add("   Recorrido");

        List<String> linea5 = new ArrayList<String>();
        linea5.add("   Lunes a viernes");
        linea5.add("   Especiales");
        linea5.add("   Sábados, domingos y feriados");
        linea5.add("   Verano");
        linea5.add("   Recorrido");

        List<String> linea6 = new ArrayList<String>();
        linea6.add("   Lunes a domingo");
        linea6.add("   Especiales");
        linea6.add("   Recorrido");

        List<String> linea7 = new ArrayList<String>();
        linea7.add("   Lunes a viernes");
        linea7.add("   Sábados, domingos y feriados");
        linea7.add("   Recorrido");

        List<String> linea8Verde = new ArrayList<String>();
        linea8Verde.add("   Lunes a viernes");
        linea8Verde.add("   Sábados");
        linea8Verde.add("   Recorrido");

        List<String> linea8Rojo = new ArrayList<String>();
        linea8Rojo.add("   Lunes a viernes");
        linea8Rojo.add("   Sábados");
        linea8Rojo.add("   Domingos y feriados");
        linea8Rojo.add("   Recorrido");

        List<String> linea9Verde = new ArrayList<String>();
        linea9Verde.add("   Lunes a viernes");
	linea9Verde.add("   Sábados, Domingos y feriados");
        linea9Verde.add("   Recorrido");

        List<String> linea9Rojo = new ArrayList<String>();
        linea9Rojo.add("   Lunes a viernes");
	linea9Rojo.add("   Sábados, Domingos y feriados");
        linea9Rojo.add("   Recorrido");

        List<String> linea10 = new ArrayList<String>();
        linea10.add("   Lunes a domingo");
        linea10.add("   Recorrido");

        List<String> linea11 = new ArrayList<String>();
        linea11.add("   Lunes a domingo");
        linea11.add("   Recorrido");

        List<String> linea12 = new ArrayList<String>();
        linea12.add("   Lunes a viernes");
        linea12.add("   Sábados");
        linea12.add("   Domingos y feriados");
        linea12.add("   Recorrido");

        List<String> linea13 = new ArrayList<String>();
        linea13.add("   Lunes a viernes");
        linea13.add("   Sábados, domingos y feriados");
        linea13.add("   Recorrido");

        List<String> linea14 = new ArrayList<String>();
        linea14.add("   Lunes a domingo");
        linea14.add("   Recorrido");

        List<String> linea15 = new ArrayList<String>();
        linea15.add("   Lunes a domingo");
        linea15.add("   Recorrido");

        List<String> linea16 = new ArrayList<String>();
        linea16.add("   Lunes a domingo");
        linea16.add("   Recorrido");

        List<String> linea17 = new ArrayList<String>();
        linea17.add("   Lunes a domingo");
        linea17.add("   Recorrido");

        List<String> linea18 = new ArrayList<String>();
        linea18.add("   Lunes a viernes");
        linea18.add("   Especiales");
        linea18.add("   Sábados, domingos y feriados");
        linea18.add("   Recorrido");
        listDataChild.put(listDataHeader.get(0), linea1Verde); // Header, Child data
        listDataChild.put(listDataHeader.get(1), linea1Rojo); // Header, Child data
        listDataChild.put(listDataHeader.get(2), linea2Verde); // Header, Child data
        listDataChild.put(listDataHeader.get(3), linea2Rojo); // Header, Child data
        listDataChild.put(listDataHeader.get(4), linea3); // Header, Child data
        listDataChild.put(listDataHeader.get(5), linea4); // Header, Child data
        listDataChild.put(listDataHeader.get(6), linea5); // Header, Child data
        listDataChild.put(listDataHeader.get(7), linea6); // Header, Child data
        listDataChild.put(listDataHeader.get(8), linea7); // Header, Child data
        listDataChild.put(listDataHeader.get(9), linea8Verde); // Header, Child data
        listDataChild.put(listDataHeader.get(10), linea8Rojo); // Header, Child data
        listDataChild.put(listDataHeader.get(11), linea9Verde); // Header, Child data
        listDataChild.put(listDataHeader.get(12), linea9Rojo); // Header, Child data
        listDataChild.put(listDataHeader.get(13), linea10); // Header, Child data
        listDataChild.put(listDataHeader.get(14), linea11); // Header, Child data
        listDataChild.put(listDataHeader.get(15), linea12); // Header, Child data
        listDataChild.put(listDataHeader.get(16), linea13); // Header, Child data
        listDataChild.put(listDataHeader.get(17), linea14); // Header, Child data
        listDataChild.put(listDataHeader.get(18), linea15); // Header, Child data
        listDataChild.put(listDataHeader.get(19), linea16); // Header, Child data
        listDataChild.put(listDataHeader.get(20), linea17); // Header, Child data
        listDataChild.put(listDataHeader.get(21), linea18); // Header, Child data

    }


}
