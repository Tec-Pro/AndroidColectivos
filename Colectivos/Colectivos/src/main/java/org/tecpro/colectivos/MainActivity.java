package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }

    public void lanzarSeleccionLineas(View view){
        Intent i = new Intent(this,SeleccionLineas.class);
        startActivity(i);
    }

    public void lanzarSeleccionInterUrb(View view){
        Intent i = new Intent(this,SeleccionLineasInterUrb.class);
        startActivity(i);
    }

    public void lanzarSeleccionMediaDist(View view){
        Intent i = new Intent(this,SeleccionLineasMediaDist.class);
        startActivity(i);
    }

}
