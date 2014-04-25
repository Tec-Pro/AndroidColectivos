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

public class VistaHoriarios extends ActionBarActivity {

    private Bundle extras;
    private String[] times;
    String[]busStops;
    private String title;
    private String infor;
    int cantBondis;
    private int width;
    private boolean distingo= true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horarios);


        extras = getIntent().getExtras();
        busStops = extras.getStringArray("header");

        times = extras.getStringArray("timeTable");
        cantBondis = extras.getInt("cantBondis",1);
        title= extras.getString("title");
        infor= extras.getString("info");
        setTitle(title);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        refreshGrid(times,busStops,cantBondis);

    }

    public void refreshGrid(String[] timeTable, String[] busStops, int cantBondis){

        setHeaders(busStops);
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
