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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeleccionLineasMediaDist extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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

    }


    public void lanzar(int grupo, int hijo){
        String selectedHijo = (String) listAdapter.getChild(grupo, hijo);
        String selectedGroup = (String) listAdapter.getGroup(grupo);
        selectedGroup= selectedGroup.substring(6);
        selectedHijo= selectedHijo.substring(3);
        String title=selectedGroup+"-"+ selectedHijo;
        //Toast.makeText(getBaseContext(),selectedGroup+" - "+ selectedHijo, Toast.LENGTH_LONG).show();

        Intent IntentMapa;
        switch (grupo){
            case 0: //Río Cuarto-Villa Maria
                switch (hijo){
                    case 0: //lunes-viernes

                        break;
                }
                break;

            case 2: //Río cuarto-C. Moldes
                switch (hijo){
                    case 0://recorrido

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
        listDataHeader.add("Río Cuarto - C. Moldes");




        // Adding child data
        List<String> rioCuartoVillaMaria = new ArrayList<String>();
        rioCuartoVillaMaria.add("   Todos los días");




        List<String> rioCuartoCMoldes = new ArrayList<String>();
        rioCuartoCMoldes.add("   Lunes a viernes");
        rioCuartoCMoldes.add("   Sábados");
        rioCuartoCMoldes.add("   Domingos y feriados ");


        listDataChild.put(listDataHeader.get(0), rioCuartoVillaMaria); // Header, Child data
        listDataChild.put(listDataHeader.get(1), rioCuartoCMoldes); // Header, Child data


    }


}
