package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jacinto on 5/15/14.
 */
public class AgregarHorario extends Activity {
    private ArrayList<String> Lineas;
    private ArrayList<String> DiasSpinner;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_horario);
        Lineas = new ArrayList<String>();
        Lineas.add("Linea 1 verde");
        Lineas.add("Linea 1 rojo");
        Lineas.add("Linea 2 verde");
        Lineas.add("Linea 2 rojo");
        Lineas.add("Linea 3");
        Lineas.add("Linea 4");
        Lineas.add("Linea 5");
        Lineas.add("Linea 6");
        Lineas.add("Linea 7");
        Lineas.add("Linea 8 verde");
        Lineas.add("Linea 8 rojo");
        Lineas.add("Linea 9 verde");
        Lineas.add("Linea 9 rojo");
        Lineas.add("Linea 10");
        Lineas.add("Linea 11");
        Lineas.add("Linea 12");
        Lineas.add("Linea 13");
        Lineas.add("Linea 14");
        Lineas.add("Linea 15");
        Lineas.add("Linea 16");
        Lineas.add("Linea 17");
        Lineas.add("Linea 18");
        Lineas.add("Linea A: UNRC - Las Higueras");
        Lineas.add("Río Cuarto - Las Higueras");
        Lineas.add("Río Cuarto - Holmberg");
        Lineas.add("Río Cuarto- Villa María");
        Lineas.add("Villa María- Río Cuarto");
        Lineas.add("Río Cuarto - C. Moldes");
        Lineas.add("C. Moldes -  Río Cuarto");
        DiasSpinner = new ArrayList<String>();
        DiasSpinner.add("Lunes a Viernes");
        DiasSpinner.add("Fin de semana/Feriado");
        Spinner spLineas = (Spinner) findViewById(R.id.spiner_lineas);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Lineas);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLineas.setAdapter(adaptador);
        Spinner spDia = (Spinner) findViewById(R.id.spiner_dia);
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, DiasSpinner );
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDia.setAdapter(adaptador2);
    }

    public void lanzarGuardar(View view) throws Throwable {
        String lugar = ((EditText)findViewById(R.id.lugar)).getText().toString();
        if (lugar.isEmpty()){
            Toast.makeText(getApplicationContext(), "Lugar vacio, por favor complete el lugar",Toast.LENGTH_SHORT).show();
        } else {
            DataBaseHelper dbh = new DataBaseHelper(this);
            Spinner spLineas = (Spinner) findViewById(R.id.spiner_lineas);
            Spinner spDia = (Spinner) findViewById(R.id.spiner_dia);
            TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
            String minutos;
            if (tp.getCurrentMinute() < 9) {
                minutos = "0" + tp.getCurrentMinute().toString();
            } else {
                minutos = tp.getCurrentMinute().toString();
            }
            String time = tp.getCurrentHour().toString() + ":" + minutos;
            Cursor c = dbh.getIdEditar(Lineas.get(spLineas.getSelectedItemPosition()),lugar,DiasSpinner.get(spDia.getSelectedItemPosition()),time);
            if (c.getCount() == 0) {
                dbh.insertar("mis_horarios", lugar, Lineas.get(spLineas.getSelectedItemPosition()), time, DiasSpinner.get(spDia.getSelectedItemPosition()));
                Intent i = new Intent(this, MisHorarios.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Infromacion repetida",Toast.LENGTH_SHORT).show();
            }
        }
    }




}
