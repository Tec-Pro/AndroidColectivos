package org.tecpro.colectivos;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Vector;

/**
 * Created by jacinto on 5/18/14.
 */
public class HorariosAgregados extends ListActivity {
        private AdView adView;
        private DataBaseHelper manager;
        private Cursor c;
        private ListView listV;
        private BaseAdapterHorariosAgregados cursorAdapter;
        private String lineaParametro[];
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.horarios_agregados);
            adView=(AdView) findViewById(R.id.adViewHorariosAgregados);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            adView.loadAd(adRequest);
            manager = new DataBaseHelper(this);
            Bundle reicieveParams = getIntent().getExtras();
            lineaParametro = new String[1];
            lineaParametro[0] = reicieveParams.getString("linea");
            c = manager.cargarCursorXLinea(lineaParametro);
            TextView titulo = (TextView) findViewById(R.id.tituloHorariosAgregados);
            titulo.setText(lineaParametro[0]);
            if(lineaParametro[0].contains(" verde")){
                titulo.setTextColor(Color.rgb(2, 150, 27));
            }else {
                if (lineaParametro[0].contains(" rojo")) {
                    titulo.setTextColor(Color.RED);
                } else {
                    titulo.setTextColor(Color.BLACK);
                }
            }
            Vector<String> lugares = new Vector<String>();
            Vector<String> dias = new Vector<String>();
            Vector<String> horas = new Vector<String>();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // The Cursor is now set to the right position
                lugares.add(c.getString(1));
                dias.add(c.getString(4));
                horas.add(c.getString(3));
            }
            cursorAdapter = new BaseAdapterHorariosAgregados(this,horas,dias,lugares);
            setListAdapter(cursorAdapter);
            listV = (ListView) findViewById(android.R.id.list);
            registerForContextMenu(listV);
           /* listV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    return false;
                }
            });*/
        }

    /** This will be invoked when an item in the listview is long pressed */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.floating_contextual_menu_horarios_agregados, menu);
    }

    /** This will be invoked when a menu item is selected */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View view = info.targetView;
        TextView tVTitulo = (TextView)this.findViewById(R.id.tituloHorariosAgregados);
        TextView tVLugar = (TextView)view.findViewById(R.id.textView2);
        TextView tVDia = (TextView)view.findViewById(R.id.textView4);
        TextView tVHora = (TextView)view.findViewById(R.id.textView6);
        String lugar = tVLugar.getText().toString();
        String dia = tVDia.getText().toString();
        String hora = tVHora.getText().toString();
        String linea = tVTitulo.getText().toString();
        switch(item.getItemId()){
            case R.id.mnu_edit:
                Intent i = new Intent(this, EdicionAgregarHorario.class);
                i.putExtra("lugar",lugar);
                i.putExtra("dia",dia);
                i.putExtra("linea",linea);
                i.putExtra("hora",hora);
                startActivity(i);
                break;
            case R.id.mnu_delete:
                DataBaseHelper dbh = new DataBaseHelper(this);
                dbh.eliminar(linea,lugar,dia,hora);
                manager = new DataBaseHelper(this);
                c = manager.cargarCursorXLinea(lineaParametro);
                Vector<String> lugares = new Vector<String>();
                Vector<String> dias = new Vector<String>();
                Vector<String> horas = new Vector<String>();
                for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    // The Cursor is now set to the right position
                    lugares.add(c.getString(1));
                    dias.add(c.getString(4));
                    horas.add(c.getString(3));
                }
                cursorAdapter = new BaseAdapterHorariosAgregados(this,horas,dias,lugares);
                setListAdapter(cursorAdapter);
                listV = (ListView) findViewById(android.R.id.list);
                registerForContextMenu(listV);
                break;
        }
        return true;
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
            c = manager.cargarCursorXLinea(lineaParametro);
            TextView titulo = (TextView) findViewById(R.id.tituloHorariosAgregados);
            titulo.setText(lineaParametro[0]);
            if(lineaParametro[0].contains(" verde")){
                titulo.setTextColor(Color.rgb(2, 150, 27));
            }else {
                if (lineaParametro[0].contains(" rojo")) {
                    titulo.setTextColor(Color.RED);
                } else {
                    titulo.setTextColor(Color.BLACK);
                }
            }
            Vector<String> lugares = new Vector<String>();
            Vector<String> dias = new Vector<String>();
            Vector<String> horas = new Vector<String>();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // The Cursor is now set to the right position
                lugares.add(c.getString(1));
                dias.add(c.getString(4));
                horas.add(c.getString(3));
            }
            cursorAdapter = new BaseAdapterHorariosAgregados(this,horas,dias,lugares);
            setListAdapter(cursorAdapter);
            listV = (ListView) findViewById(android.R.id.list);
            registerForContextMenu(listV);
            adView.resume();
        }

        public void onRestart(){
            super.onRestart();
            manager = new DataBaseHelper(this);
            c = manager.cargarCursorXLinea(lineaParametro);
            TextView titulo = (TextView) findViewById(R.id.tituloHorariosAgregados);
            titulo.setText(lineaParametro[0]);
            if(lineaParametro[0].contains(" verde")){
                titulo.setTextColor(Color.rgb(2, 150, 27));
            }else {
                if (lineaParametro[0].contains(" rojo")) {
                    titulo.setTextColor(Color.RED);
                } else {
                    titulo.setTextColor(Color.BLACK);
                }
            }
            Vector<String> lugares = new Vector<String>();
            Vector<String> dias = new Vector<String>();
            Vector<String> horas = new Vector<String>();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                // The Cursor is now set to the right position
                lugares.add(c.getString(1));
                dias.add(c.getString(4));
                horas.add(c.getString(3));
            }
            cursorAdapter = new BaseAdapterHorariosAgregados(this,horas,dias,lugares);
            setListAdapter(cursorAdapter);
            listV = (ListView) findViewById(android.R.id.list);
            registerForContextMenu(listV);
        }

        @Override
        public void onDestroy() {
            adView.destroy();
            super.onDestroy();
        }




}
