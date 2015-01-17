package org.tecpro.colectivos;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

;

/**
 * Created by jacinto on 5/14/14.
 */
public class MisHorarios extends ListActivity {
    private AdView adView;
    private DataBaseHelper manager;
    private Cursor c;
    private BaseAdapterMisHorarios cursorAdapter;
    private ListView listV;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_horarios);
        adView=(AdView) findViewById(R.id.adViewMisHorarios);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        manager = new DataBaseHelper(this);
        c = manager.cargarCursorLineasCargadas();
        Vector<String> datos = new Vector<String>();
        HashSet<String> set = new HashSet<String>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            // The Cursor is now set to the right position
            set.add(c.getString(0));
        }
        Iterator it = set.iterator();
        while (it.hasNext()){
            datos.add((String) it.next());
        }
        cursorAdapter = new BaseAdapterMisHorarios(this,datos);
        setListAdapter(cursorAdapter);
        listV = (ListView) findViewById(android.R.id.list);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView tv = (TextView)view.findViewById(R.id.lblListItemH);
                lanzarHorariosAgregados(tv.getText().toString());
            }
        });
    }

    public void lanzarAgregarHorario(){
        Intent i = new Intent(this, AgregarHorario.class);
        startActivity(i);
    }

    public void lanzarHorariosAgregados(String s){
        Intent i = new Intent(this, HorariosAgregados.class);
        i.putExtra("linea", s);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mis_horarios, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.agregar_horario:
                lanzarAgregarHorario();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
        this.stopManagingCursor(this.c);
    }

    @Override
    public void onResume() {
        super.onResume();
        manager = new DataBaseHelper(this);
        c = manager.cargarCursorLineasCargadas();
        Vector<String> datos = new Vector<String>();
        HashSet<String> set = new HashSet<String>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            // The Cursor is now set to the right position
            set.add(c.getString(0));
        }
        Iterator it = set.iterator();
        while (it.hasNext()){
            datos.add((String) it.next());
        }
        cursorAdapter = new BaseAdapterMisHorarios(this,datos);
        setListAdapter(cursorAdapter);
        adView.resume();
    }

    public void onRestart(){
            super.onRestart();
            manager = new DataBaseHelper(this);
            c = manager.cargarCursorLineasCargadas();
            Vector<String> datos = new Vector<String>();
            HashSet<String> set = new HashSet<String>();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // The Cursor is now set to the right position
                set.add(c.getString(0));
            }
            Iterator it = set.iterator();
            while (it.hasNext()){
                datos.add((String) it.next());
            }
            cursorAdapter = new BaseAdapterMisHorarios(this,datos);
            setListAdapter(cursorAdapter);
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }



}
