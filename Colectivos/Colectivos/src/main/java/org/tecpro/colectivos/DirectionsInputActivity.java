package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by nico on 20/05/14.
 */
public class DirectionsInputActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.directions_input);
    }


    public void BuscarCalles(View view){
            Intent intentMessage=new Intent();


            // put the message to return as result in Intent
            intentMessage.putExtra("desde",((EditText)findViewById(R.id.desdeComp)).getText().toString());
            intentMessage.putExtra("hasta",((EditText)findViewById(R.id.hastaComp)).getText().toString());
            // Set The Result in Intent
            setResult(1,intentMessage);
            // finish The activity
            finish();
        }



}
