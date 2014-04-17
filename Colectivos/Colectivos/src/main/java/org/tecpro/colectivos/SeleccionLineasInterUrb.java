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

public class SeleccionLineasInterUrb extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
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
        horarios = new Horarios();

    }


    public void lanzar(int grupo, int hijo){
        String selectedHijo = (String) listAdapter.getChild(grupo, hijo);
        String selectedGroup = (String) listAdapter.getGroup(grupo);
        selectedGroup= selectedGroup.substring(6);
        selectedHijo= selectedHijo.substring(3);
        String title=selectedGroup+"-"+ selectedHijo;
        //Toast.makeText(getBaseContext(),selectedGroup+" - "+ selectedHijo, Toast.LENGTH_LONG).show();

        Intent IntentHorario;
        Intent IntentMapa;
        switch (grupo){
            case 0: //UNRC-HIGUeras
                switch (hijo){
                    case 0: //lunes-viernes
                        IntentHorario = new Intent(this,VistaHorarios.class);
                        IntentHorario.putExtra("header",horarios.getHeaderUnrcHig());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTableUnrcHig());
                        IntentHorario.putExtra("title",title);
                        startActivity(IntentHorario);
                        break;
                }
                break;
            case 1: //1 Río Cuarto-higeras
                switch (hijo){
                    case 0:

                        break;
                }
                break;
            case 2: //Río cuarto-Holmberg
                switch (hijo){
                    case 0: //lunes-viernes
                        IntentHorario = new Intent(this,VistaHorarios.class);
                        IntentHorario.putExtra("header",horarios.getHeaderRioCuartoHolmberg());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTableRioCuartoHolmberg());
                        IntentHorario.putExtra("title",title);
                        startActivity(IntentHorario);
                        break;
                    case 1: //sabados
                        IntentHorario = new Intent(this,VistaHorarios.class);
                        IntentHorario.putExtra("header",horarios.getHeaderRioCuartoHolmberg());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTableRioCuartoHolmbergSab());
                        IntentHorario.putExtra("title",title);
                        startActivity(IntentHorario);
                        break;
                    case 2: //Feriados y domingos
                        IntentHorario = new Intent(this,VistaHorarios.class);
                        IntentHorario.putExtra("header",horarios.getHeaderRioCuartoHolmberg());
                        IntentHorario.putExtra("timeTable",horarios.getTimeTableRioCuartoHolmbergDom());
                        IntentHorario.putExtra("title",title);
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
        listDataHeader.add("Linea A: UNRC - Las Higueras");
        listDataHeader.add("Río Cuarto - Las Higueras");
        listDataHeader.add("Río Cuarto - Holmberg");




        // Adding child data
        List<String> unrcHig = new ArrayList<String>();
        unrcHig.add("   lunes a domingo");


        List<String> rioCuartoHig = new ArrayList<String>();
        rioCuartoHig.add("   Lunes a viernes");
        rioCuartoHig.add("   Sábados, domingos y feriados ");

        List<String> rioCuartoHolm = new ArrayList<String>();
        rioCuartoHolm.add("   Lunes a viernes");
        rioCuartoHolm.add("   Sábados");
        rioCuartoHolm.add("   Domingos y feriados ");


        listDataChild.put(listDataHeader.get(0), unrcHig); // Header, Child data
        listDataChild.put(listDataHeader.get(1), rioCuartoHig); // Header, Child data
        listDataChild.put(listDataHeader.get(2), rioCuartoHolm); // Header, Child data


    }


}
