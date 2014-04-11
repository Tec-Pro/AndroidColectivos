package org.tecpro.colectivos;

/**
 * Created by nico on 10/04/14.
 */
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
                lanzarMapa(groupPosition,childPosition);
                return true;
            }
        });


        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        recorrido= new Recorrido();

    }


    public void lanzarMapa(int grupo, int hijo){
        final String selectedHijo = (String) listAdapter.getChild(grupo, hijo);
        final String selectedGroup = (String) listAdapter.getGroup(grupo);
        Toast.makeText(getBaseContext(),selectedGroup+" - "+ selectedHijo, Toast.LENGTH_LONG).show();
        switch (grupo){
            case 6:
                switch (hijo){
                    case 3:
                        Intent i = new Intent(this,Mapa.class);
                        i.putExtra("recorrido",recorrido.getRecorrido5());
                        i.putExtra("paradas", recorrido.getParadaslinea5());
                        startActivity(i);
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
        listDataHeader.add("Linea 1");
        listDataHeader.add("Linea 12");
        listDataHeader.add("Linea 13");
        listDataHeader.add("Linea 14");
        listDataHeader.add("Linea 15");
        listDataHeader.add("Linea 16");
        listDataHeader.add("Linea 17");
        listDataHeader.add("Linea 18");



        // Adding child data
        List<String> linea1Verde = new ArrayList<String>();
        linea1Verde.add("Horario lunes a viernes");
        linea1Verde.add("Horarios especiales");
        linea1Verde.add("Horarios sábados, domingos y feriados ");
        linea1Verde.add("Recorrido");

        List<String> linea1Rojo = new ArrayList<String>();
        linea1Rojo.add("Horario lunes a viernes");
        linea1Rojo.add("Horarios especiales");
        linea1Rojo.add("Horarios sábados, domingos y feriados ");
        linea1Rojo.add("Recorrido");

        List<String> linea2Verde = new ArrayList<String>();
        linea2Verde.add("Horario lunes a viernes");
        linea2Verde.add("Horarios");
        linea2Verde.add("Horarios sábados, domingos y feriados ");
        linea2Verde.add("Recorrido");

        List<String> linea2Rojo = new ArrayList<String>();
        linea2Rojo.add("Horario lunes a viernes");
        linea2Rojo.add("Horarios sábados, domingos y feriados ");
        linea2Rojo.add("Recorrido");

        List<String> linea3 = new ArrayList<String>();
        linea3.add("Horario lunes a domingo");
        linea3.add("Horarios especiales");
        linea3.add("Recorrido");

        List<String> linea4 = new ArrayList<String>();
        linea4.add("Horario lunes a domingo");
        linea4.add("Recorrido");

        List<String> linea5 = new ArrayList<String>();
        linea5.add("Horario de lunes a viernes");
        linea5.add("Horarios especiales");
        linea5.add("Horarios sábados, domingos y feriados");
        linea5.add("Recorrido");

        List<String> linea6 = new ArrayList<String>();
        linea6.add("Horario lunes a domingo");
        linea6.add("Horarios especiales");
        linea6.add("Recorrido");

        List<String> linea7 = new ArrayList<String>();
        linea7.add("Horario lunes a viernes");
        linea7.add("Horarios sábados, domingos y feriados");
        linea7.add("Recorrido");

        List<String> linea8Verde = new ArrayList<String>();
        linea8Verde.add("Horario lunes a viernes");
        linea8Verde.add("Horarios sábados");
        linea8Verde.add("Recorrido");

        List<String> linea8Rojo = new ArrayList<String>();
        linea8Rojo.add("Horario lunes a viernes");
        linea8Rojo.add("Horarios sábados");
        linea8Rojo.add("Horarios Domingos y feriados");
        linea8Rojo.add("Recorrido");

        List<String> linea9Verde = new ArrayList<String>();
        linea9Verde.add("Horario lunes a domingo");
        linea9Verde.add("Recorrido");

        List<String> linea9Rojo = new ArrayList<String>();
        linea9Rojo.add("Horario lunes a domingo");
        linea9Rojo.add("Recorrido");

        List<String> linea10 = new ArrayList<String>();
        linea10.add("Horario lunes a domingo");
        linea10.add("Recorrido");

        List<String> linea11 = new ArrayList<String>();
        linea11.add("Horario lunes a domingo");
        linea11.add("Recorrido");

        List<String> linea12 = new ArrayList<String>();
        linea12.add("Horario lunes a viernes");
        linea12.add("Horario sábados");
        linea12.add("Horario domingos y feriados");
        linea12.add("Recorrido");

        List<String> linea13 = new ArrayList<String>();
        linea13.add("Horario lunes a viernes");
        linea13.add("Horarios sábados, domingos y feriados");
        linea13.add("Recorrido");

        List<String> linea14 = new ArrayList<String>();
        linea14.add("Horario lunes a domingo");
        linea14.add("Recorrido");

        List<String> linea15 = new ArrayList<String>();
        linea15.add("Horario lunes a domingo");
        linea15.add("Recorrido");

        List<String> linea16 = new ArrayList<String>();
        linea16.add("Horario lunes a domingo");
        linea16.add("Recorrido");

        List<String> linea17 = new ArrayList<String>();
        linea17.add("Horario lunes a domingo");
        linea17.add("Recorrido");

        List<String> linea18 = new ArrayList<String>();
        linea18.add("Horario de lunes a viernes");
        linea18.add("Horarios especiales");
        linea18.add("Horarios sábados, domingos y feriados");
        linea18.add("Recorrido");
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
