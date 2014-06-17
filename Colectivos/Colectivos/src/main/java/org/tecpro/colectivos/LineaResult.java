package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nico on 16/05/14.
 */
public class LineaResult extends Activity {
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        ArrayList<String> lineas= extras.getStringArrayList("lineas");
        System.out.println(lineas.size());
        // Display a indeterminate progress bar on title bar

        this.setContentView(R.layout.list_linea_result);

        this.listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new AdaptadorResultados(this, parDistancia(lineas)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {

                Pair<String,String> item =  (Pair<String,String>) listView.getAdapter().getItem(position);
                System.out.println("elegi"+item);
                Intent intentMessage=new Intent();


                // put the message to return as result in Intent
                intentMessage.putExtra("lineaSelect",item.first);
                // Set The Result in Intent
                setResult(1,intentMessage);
                // finish The activity
                finish();
            }
        });


    }

    private LinkedList<Pair<String,String>> parDistancia(ArrayList<String>s){
        int i=0;
        LinkedList<Pair<String,String> >ret= new LinkedList<Pair<String, String>>();
        while(i<s.size()-1){
            ret.add(new Pair<String,String>(s.get(i),s.get(i+1)));
            i=i+2;
        }
        return ret;
    }
    }
