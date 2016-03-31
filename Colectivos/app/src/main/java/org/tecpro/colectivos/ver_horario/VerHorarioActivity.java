package org.tecpro.colectivos.ver_horario;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import org.tecpro.colectivos.R;
import org.tecpro.colectivos.utils.Horarios;
import org.tecpro.colectivos.utils.NombreLineas;


public class VerHorarioActivity extends AppCompatActivity {

    private Spinner spinner;
    private String linea;
    private String[]busStops;
    private String[] times;
    private AdView adView;
    private Horarios horarios;
    private String title;
    private String infor;
    int cantBondis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_horario);
        spinner = (Spinner) findViewById(R.id.spn_day);
        linea = getIntent().getExtras().getString("linea");
        ArrayList<String> lineas = NombreLineas.getDaysFromLinea(linea);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, lineas);
        spinner.setAdapter(adapter);
        setToolbar(); // Añadir la toolbar
        setAdMob();
        horarios = new Horarios();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setGrid(linea, spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setGrid(linea, spinner.getSelectedItemPosition());

    }

    private void setAdMob(){

        adView=(AdView) findViewById(R.id.adViewHorario);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
              //  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        //.addTestDevice("A906482D0B3C5F47980E446DD6F1CF85")
                .build();


        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private void setGrid(String linea, int dia){
        configData(linea, dia);
        if(times!=null) {
            refreshGrid(times, busStops, cantBondis);
        }
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
            ab.setTitle("Horarios");
            ab.setSubtitle(linea);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshGrid(String[] timeTable, String[] busStops, int cantBondis){

        setHeaders(busStops);
        if(busStops[0].equals("Term")){
            setHeadersRioHigueras(busStops);

        }
        TableLayout tl_head1 = (TableLayout)findViewById(R.id.tl_head);
        TableLayout tl_child1 = (TableLayout)findViewById(R.id.tl_child);
        tl_child1.removeAllViews();

        String headcol1="";
        TableRow tr[]= new TableRow[2000];
        TextView tv[] = new TextView[1000];
        //int j=0;
        //int i;
        int k = 0;
        int aux=1;
        for(int i=0; i<timeTable.length/busStops.length;i++ )
        {

            tr[i] = new TableRow(this);

                if (aux > cantBondis) {
                    aux = 1;
                }
                if (aux == 2) {
                    tr[i].setBackgroundColor(Color.rgb(193, 241, 193));
                }
                if (aux == 1) {
                    tr[i].setBackgroundColor(Color.TRANSPARENT);
                }
                if (aux == 3) {
                    tr[i].setBackgroundColor(Color.rgb(193, 241, 217));
                }
                if (aux == 4) {
                    tr[i].setBackgroundColor(Color.rgb(193, 217, 241));
                }
            if(aux == 5) {
                tr[i].setBackgroundColor(Color.rgb(199,235,242));
            }
            if (aux == 6) {
                tr[i].setBackgroundColor(Color.rgb(160,214,180));
            }
            aux++;


            for( int j=0; j<busStops.length; j++)
            {
                tv[j]=new TextView(this);
                tv[j].setId(j);
                tv[j].setText(timeTable[k]);
                tv[j].setWidth(dpToPx(90));
                tv[j].setHeight(dpToPx(30));
                tv[j].setGravity(Gravity.CENTER);

                if(headcol1.length() < tv[j].getText().length())
                {
                    headcol1=null;
                    headcol1=tv[j].getText().toString();
                }
                tr[i].addView(tv[j]);
                k++;
            }

            tl_child1.addView(tr[i]);
        }

        TableRow trhead= new TableRow(this);


        tl_head1.addView(trhead);
    }

    private void setHeaders(String[] headers ){

        int i = 0;
        for (int id : new int[] {R.id.col1, R.id.col2, R.id.col3, R.id.col4, R.id.col5, R.id.col6,R.id.col7,R.id.col8}) {
            TextView header = (TextView) findViewById(id);
            if( i>=headers.length){
                header.setVisibility(View.GONE);
            }
            else{
                header.setVisibility(View.VISIBLE);
                header.setText(headers[i]);

            }
            i++;
        }

    }

    private void setHeadersRioHigueras(String[] headers ){

        int i = 0;
        for (int id : new int[] {R.id.col1, R.id.col2, R.id.col3, R.id.col4, R.id.col5, R.id.col6,R.id.col7,R.id.col8}) {
            TextView header = (TextView) findViewById(id);
            if( i>=headers.length){
                header.setVisibility(View.GONE);
            }
            else{
                TableRow.LayoutParams lp = new TableRow.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                header.setVisibility(View.VISIBLE);
                header.setText(headers[i]);
                if(i%2 == 0){
                    header.setGravity(Gravity.RIGHT);
                    lp.setMargins(2,2,0,2);
                    header.setLayoutParams(lp);
                }
                else{
                    header.setGravity(Gravity.LEFT);
                    lp.setMargins(0,2,2,2);
                    header.setLayoutParams(lp);
                }

            }
            i++;
        }

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

    private void configData(String linea, int dia){
        switch (linea) {
            case "Linea 1 Rojo":
                switch (dia){
                    case 0:
                        busStops =horarios.getBusStops1rojo();
                        times =horarios.getTimeTable1rojo();
                        cantBondis =horarios.getCantBondi1R();
                        break;
                    case 1:
                        busStops =horarios.getBusStops1rojo();
                        times =horarios.getTimeTable1rojoE();
                        cantBondis =1;
                        break;
                    case 2:
                        busStops =horarios.getBusStops1rojoSyD();
                        times =horarios.getTimeTable1rojoSyD();
                        cantBondis =horarios.getCantBondi1R();
                        break;
                };
                break;
            case "Linea 1 Verde":
                switch (dia){
                    case 0:
                        busStops =horarios.getBusStops1verde();
                        times =horarios.getTimeTable1verde();
                        cantBondis =horarios.getCantBondi1V();

                        break;
                    case 1:
                        busStops =horarios.getBusStops1verde();
                        times =horarios.getTimeTable1verdeE();
                        cantBondis = 1;
                        break;
                    case 2:
                        busStops =horarios.getBusStops1verdeSyD();
                        times =horarios.getTimeTable1verdeSyD();
                        cantBondis = horarios.getCantBondi1V();
                        break;
                };
                break;
            case "Linea 2 Rojo":
                switch (dia){
                    case 0:
                        busStops =horarios.getBusStops2rojo();
                        times =horarios.getTimeTable2rojo();
                        cantBondis = horarios.getCantBondi2R();

                        break;
                    case 1:
                        busStops =horarios.getBusStops2rojo();
                        times =horarios.getTimeTable2rojoSyD();
                        cantBondis = horarios.getCantBondi2RFin();
                        break;
                };
                break;
            case "Linea 2 Verde":
                switch (dia){
                    case 0:
                        busStops =horarios.getBusStops2verde();
                        times =horarios.getTimeTable2verde();
                        cantBondis = horarios.getCantBondi2V();
                        break;
                    case 1:
                        busStops =horarios.getBusStops2verdeEspecial();
                        times =horarios.getTimeTable2verdeEspecial();
                        cantBondis = horarios.getCantBondi2VE();
                        break;
                    case 2:
                        busStops =horarios.getBusStops2verde();
                        times =horarios.getTimeTable2verdeSyD();
                        cantBondis = horarios.getCantBondi2VFin();
                        break;
                };
                break;
            case "Linea 3":
                switch (dia){
                    case 0:
                        busStops =horarios.getBusStops3();
                        times =horarios.getTimeTable3();
                        cantBondis = horarios.getCantBondi3();
                        break;
                    case 1:
                        busStops =horarios.getBusStops3E();
                        times =horarios.getTimeTable3E();
                        cantBondis =1;
                        break;
                };
                break;
            case "Linea 4":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops4();
                        times = horarios.getTimeTable4();
                        cantBondis = horarios.getCantBondi4();
                        break;
                }
                break;
            case "Linea 5":
                switch (dia){
                    case 0:
                        busStops =horarios.getBusStops5();
                        times =horarios.getTimeTable5();
                        cantBondis =horarios.getCantBondi5();
                        break;
                    case 1:
                        busStops =horarios.getBusStops5E();
                        times =horarios.getTimeTable5E();
                        cantBondis =1;
                        break;
                    case 2:
                        busStops =horarios.getBusStops5SyD();
                        times =horarios.getTimeTable5SyD();
                        cantBondis =horarios.getCantBondi5Fin();
                        break;
                    case 3:
                        busStops =horarios.getBusStops5SyD();
                        times =horarios.getTimeTable5Verano();
                        cantBondis =horarios.getCantBondi5Ver();
                        break;
                };
                break;
            case "Linea 6":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops6();
                        times = horarios.getTimeTable6();
                        cantBondis =horarios.getCantBondi6();

                        break;
                    case 1:
                        busStops = horarios.getBusStops6E();
                        times = horarios.getTimeTable6E();
                        cantBondis =horarios.getCantBondi6E();
                        break;
                }
                break;
            case "Linea 7":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops7();
                        times = horarios.getTimeTable7();
                        cantBondis =1;
                        break;
                    case 1:
                        busStops = horarios.getBusStops7();
                        times = horarios.getTimeTable7SyD();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 8 Rojo":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops8rojo();
                        times = horarios.getTimeTable8rojo();
                        cantBondis =1;

                        break;
                    case 1:
                        busStops = horarios.getBusStops8rojo();
                        times = horarios.getTimeTable8rojoS();
                        cantBondis =1;
                        break;
                    case 2:
                        busStops = horarios.getBusStops8rojo();
                        times = horarios.getTimeTable8rojoD();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 8 Verde":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops8verde();
                        times = horarios.getTimeTable8verde();
                        cantBondis =1;
                        break;
                    case 1:
                        busStops = horarios.getBusStops8verde();
                        times = horarios.getTimeTable8verdeSyD();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 9 Rojo":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops9rojo();
                        times = horarios.getTimeTable9rojo();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 9 Verde":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops9verde();
                        times = horarios.getTimeTable9verde();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 10":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops10();
                        times = horarios.getTimeTable10();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 11":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops11();
                        times = horarios.getTimeTable11();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 12":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops12();
                        times = horarios.getTimeTable12();
                        cantBondis =1;

                        break;
                    case 1:
                        busStops = horarios.getBusStops12();
                        times = horarios.getTimeTable12Sabado();
                        cantBondis =1;
                        break;
                    case 2:
                        busStops = horarios.getBusStops12();
                        times = horarios.getTimeTable12Domingo();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea 13":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops13();
                        times = horarios.getTimeTable13();
                        cantBondis =horarios.getCantBondi13();
                        break;
                    case 1:
                        busStops = horarios.getBusStops13Finde();
                        times = horarios.getTimeTable13Finde();
                        cantBondis =horarios.getCantBondi13Fin();
                        break;
                }
                break;
            case "Linea 14":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops14Maniana();
                        times = horarios.getTimeTable14Maniana();
                        cantBondis = 1;
                        break;
                    case 1:
                        busStops = horarios.getBusStops14Tarde();
                        times = horarios.getTimeTable14Tarde();
                        cantBondis = 1;
                        break;
                }
                break;
            case "Linea 15":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops15();
                        times = horarios.getTimeTable15();
                        cantBondis = 1;
                        break;
                }
                break;
            case "Linea 16":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops16();
                        times = horarios.getTimeTable16();
                        cantBondis = 1;
                        break;
                }
                break;
            case "Linea 17":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops17();
                        times = horarios.getTimeTable17();
                        cantBondis = 1;
                        break;
                }
                break;
            case "Linea 18":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStops18();
                        times = horarios.getTimeTable18();
                        cantBondis =horarios.getCantBondi18();

                        break;
                    case 1:
                        busStops = horarios.getBusStops18();
                        times = horarios.getTimeTable18Especial();
                        cantBondis = 1;
                        break;
                    case 2:
                        busStops = horarios.getBusStops18();
                        times = horarios.getTimeTable18Finde();
                        cantBondis =1;
                        break;
                }
                break;
            case "Linea A":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStopsA();
                        times = horarios.getTimeTableA();
                        cantBondis = 1;
                        break;
                }
                break;
            case "Río Cuarto - Las Higueras":
                switch (dia) {
                    case 0:
                        busStops = horarios.getBusStopsRioHigueras();
                        times = horarios.getTimeTableRioHigueras();
                        break;
                    case 1:
                        busStops = horarios.getBusStopsRioHigueras();
                        times = horarios.getTimeTableRioHiguerasSyd();
                        break;
                }
                break;
            case "Río Cuarto - Homlberg":
                switch (dia) {
                    case 0:
                        busStops = horarios.getHeaderRioCuartoHolmberg();
                        times = horarios.getTimeTableRioCuartoHolmberg();
                        break;
                    case 1:
                        busStops = horarios.getHeaderRioCuartoHolmberg();
                        times = horarios.getTimeTableRioCuartoHolmbergSab();
                        break;
                    case 2:
                        busStops = horarios.getHeaderRioCuartoHolmberg();
                        times = horarios.getTimeTableRioCuartoHolmbergDom();
                        break;
                }
                break;
        }
    }


    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }


}
