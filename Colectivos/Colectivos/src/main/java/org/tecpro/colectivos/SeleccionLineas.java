package org.tecpro.colectivos;

/**
 * Created by nico on 10/04/14.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.Toast;

public class SeleccionLineas extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Recorrido recorrido;
    private Horarios horarios;

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
        recorrido= new Recorrido();
        horarios = new Horarios();

    }


    public void lanzar(int grupo, int hijo){
        final String selectedHijo = (String) listAdapter.getChild(grupo, hijo);
        final String selectedGroup = (String) listAdapter.getGroup(grupo);
        Toast.makeText(getBaseContext(),selectedGroup+" - "+ selectedHijo, Toast.LENGTH_LONG).show();
        Intent IntentHorario;
        Intent IntentMapa;
        switch (grupo){
            case 0: //1 verde
                switch (hijo){
                    case 0: //lunes-viernes
                        IntentHorario = new Intent(this,VistaHorarios.class);
                        IntentHorario.putExtra("header",horarios.getHeader1verde());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTable1verde());
                        startActivity(IntentHorario);
                        break;
                    case 3://recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido1Verde());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 1: //1 rojo
                switch (hijo){
                    case 3: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido1Rojo());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 2://2 verde
                switch (hijo){
                    case 3://recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido2Verde());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 3://2 rojo
                switch (hijo){
                    case 2: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido2Verde());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 4: //3
                switch (hijo){
                    case 2: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido3());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 5: //4
                switch (hijo){
                    case 1: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido4());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 6: //5
                switch (hijo){
                    case 3: //recorrido
                        Intent i = new Intent(this,Mapa.class);
                        i.putExtra("recorrido",recorrido.getRecorrido5());
                        i.putExtra("paradas", recorrido.getParadaslinea5());
                        startActivity(i);
                        break;
                }
                break;
            case 7: //6
                switch (hijo){
                    case 2: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido6());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 8: //7
                switch (hijo){
                    case 2: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido7());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 9: //8 verde
                switch (hijo){
                    case 2: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido8Verde());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 10://8 rojo
                switch (hijo){
                    case 3: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido8Rojo());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 11: //9 verde
                switch (hijo){
                    case 1: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido9Verde());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 12: //9 rojo
                switch (hijo){
                    case 1: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido9Rojo());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 13: //10
                switch (hijo){
                    case 1://reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido10());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 14://11
                switch (hijo){
                    case 1: //reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido11());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 15://12
                switch (hijo){
                    case 3: //recorrido
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido12());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 16://13
                switch (hijo){
                    case 2://reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido13());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 17://14
                switch (hijo){
                    case 1: //reco:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido14());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 18://15
                switch (hijo){
                    case 1:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido15());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 19: //16
                switch (hijo){
                    case 1:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido16());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 20://17
                switch (hijo){
                    case 1:
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido17());
                        startActivity(IntentMapa);
                        break;
                }
                break;
            case 21://18
                switch (hijo){
                    case 3://reco
                        IntentMapa = new Intent(this,Mapa.class);
                        IntentMapa.putExtra("recorrido",recorrido.getRecorrido18());
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
        linea9Verde.add("   Lunes a domingo");
        linea9Verde.add("   Recorrido");

        List<String> linea9Rojo = new ArrayList<String>();
        linea9Rojo.add("   Lunes a domingo");
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
