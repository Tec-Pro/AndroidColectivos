package org.tecpro.colectivos;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class VistaHoriarios extends ActionBarActivity {

    private Bundle extras;
    private String[] times;
    String[]busStops;
    private String title;
    private String infor;
    int cantBondis;
    private int width;
    private boolean distingo= true;
    private String[] timesMoldes;
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horarios);


        extras = getIntent().getExtras();
        busStops = extras.getStringArray("header");

        times = extras.getStringArray("timeTable");
        timesMoldes=extras.getStringArray("timeTableMoldes");
        cantBondis = extras.getInt("cantBondis",1);
        title= extras.getString("title");
        infor= extras.getString("info");
        setTitle(title);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        if(times!=null) {
            refreshGrid(times, busStops, cantBondis);
        }
        else{
            if(timesMoldes!=null){
                refreshGridMoldes(timesMoldes,busStops);
            }
        }
        adView=(AdView) findViewById(R.id.adViewHorario);

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
                .addTestDevice("A906482D0B3C5F47980E446DD6F1CF85")
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
        super.onResume();
        adView.resume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }

    public void refreshGrid(String[] timeTable, String[] busStops, int cantBondis){

        setHeaders(busStops);
        if(busStops[0].equals("Termi")){
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

            if(distingo) {
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
                aux++;
            }
            else{
                tr[i].setBackgroundColor(Color.TRANSPARENT);
            }


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
                header.setText(headers[i]);
                //header.setWidth(width/headers.length);

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
                header.setText(headers[i]);
                if(i==0 || i==1 || i==4 || i==5){
                    header.setBackgroundColor(Color.WHITE);
                }
                else{
                    header.setBackgroundColor(Color.GRAY);
                }
                if(i%2 == 0){
                    header.setGravity(Gravity.RIGHT);
                }
                else{
                    header.setGravity(Gravity.LEFT);
                }

            }
            i++;
        }

    }

    private void refreshGridMoldes(String[] timeTable, String[] busStops){
        setHeaders(busStops);
        TableLayout tl_head1 = (TableLayout)findViewById(R.id.tl_head);
        TableLayout tl_child1 = (TableLayout)findViewById(R.id.tl_child);
        tl_child1.removeAllViews();
        String headcol1="";
        TableRow tr[]= new TableRow[2000];
        TextView tv[] = new TextView[1000];
        int k = 0;
        int aux=0;
        int i=0;
        for(int j=0; j<timeTable.length/busStops.length;j++ ){
            tr[j] = new TableRow(this);
            k=j;
            aux=0;
            while(aux<busStops.length ) {
                tv[i]=new TextView(this);
                tv[i].setId(i);
                tv[i].setText(timeTable[k]);
                tv[i].setWidth(dpToPx(90));
                tv[i].setHeight(dpToPx(30));
                tv[i].setGravity(Gravity.CENTER);
                if(headcol1.length() < tv[i].getText().length())
                {
                    headcol1=null;
                    headcol1=tv[aux].getText().toString();
                }
                tr[j].addView(tv[i]);
                i++;
                aux=aux+1;
                k=k+timeTable.length/busStops.length;

            }
            tl_child1.addView(tr[j]);
        }


        TableRow trhead= new TableRow(this);


        tl_head1.addView(trhead);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vista_horarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.info) {
                Intent i = new Intent(this,ViewInfo.class);
                i.putExtra("title",title);
                i.putExtra("info",infor);
                startActivity(i);
        }
        if(id==R.id.distingir){
            distingo= !distingo;
            refreshGrid(times,busStops,cantBondis);

        }
        return super.onOptionsItemSelected(item);
    }

    private int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
