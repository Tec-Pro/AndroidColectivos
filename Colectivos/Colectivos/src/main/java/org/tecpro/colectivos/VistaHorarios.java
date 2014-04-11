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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horarios);
        List<String> busStops = new ArrayList<String>(5);
        busStops.add("p1");
        busStops.add("p2");
        busStops.add("p3");
        busStops.add("p4");
        busStops.add("p5");
        List<List<String>> times = new ArrayList<List<String>>();

        for(int i=0;i<=9;i++){
            List<String> a = new ArrayList<String>(9);
            for(int j=0;j<=9;j++){

                a.add(i + ","+ j+"");
            }
            times.add(a);
        }

        refreshGrid(times,busStops);

    }

    public void refreshGrid(List<List<String>> timeTable, List<String> busStops){

        setHeaders(busStops);
        TableLayout tl_head1 = (TableLayout)findViewById(R.id.tl_head);
        TableLayout tl_child1 = (TableLayout)findViewById(R.id.tl_child);

        String headcol1="";
        TableRow tr[]= new TableRow[40];
        TextView tv[] = new TextView[1000];
        for(int i=0; i <timeTable.size(); i++)
        {

            tr[i]=new TableRow(this);


            for(int j=0; j<busStops.size(); j++)
            {
                tv[j]=new TextView(this);
                tv[j].setId(j);
                tv[j].setText(timeTable.get(i).get(j));
                tv[j].setWidth(190);
                tv[j].setHeight(60);
                tv[j].setGravity(Gravity.CENTER);

                if(headcol1.length() < tv[j].getText().length())
                {
                    headcol1=null;
                    headcol1=tv[j].getText().toString();
                }
                tr[i].addView(tv[j]);
            }

            tl_child1.addView(tr[i]);
        }

        TableRow trhead= new TableRow(this);

        tl_head1.addView(trhead);
    }

    private void setHeaders(List<String> headers ){
        int i = 0;
        for (int id : new int[] {R.id.col1, R.id.col2, R.id.col3, R.id.col4, R.id.col5, R.id.col6,R.id.col7,R.id.col8}) {
            TextView header = (TextView) findViewById(id);
            if(headers.size() > i){
                header.setText(headers.get(i));
            }
            else{
                header.setVisibility(View.GONE);
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
