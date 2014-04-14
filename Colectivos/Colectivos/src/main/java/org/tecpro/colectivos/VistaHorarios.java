package org.tecpro.colectivos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VistaHorarios extends ActionBarActivity {

    private Bundle extras;
    private String[] times;
    String[]busStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horarios);


        extras = getIntent().getExtras();
        busStops = extras.getStringArray("header");

        times = extras.getStringArray("timeTable");


        refreshGrid(times,busStops);

    }

    public void refreshGrid(String[] timeTable, String[] busStops){

        setHeaders(busStops);
        TableLayout tl_head1 = (TableLayout)findViewById(R.id.tl_head);
        TableLayout tl_child1 = (TableLayout)findViewById(R.id.tl_child);

        String headcol1="";
        TableRow tr[]= new TableRow[2000];
        TextView tv[] = new TextView[1000];
        //int j=0;
        //int i;
        int k = 0;

        for(int i=0; i<timeTable.length/busStops.length;i++ )
        {

            tr[i]=new TableRow(this);


            for( int j=0; j<busStops.length; j++)
            {
                tv[j]=new TextView(this);
                tv[j].setId(j);
                tv[j].setText(timeTable[k]);
                tv[j].setWidth(190);
                tv[j].setHeight(60);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
