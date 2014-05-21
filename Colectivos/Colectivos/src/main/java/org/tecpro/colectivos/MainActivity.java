package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.*;


public class MainActivity extends Activity {
    private AdView adView;
    private LinearLayout lytMain;
    private static final  String AD_UNIT_ID = "ca-app-pub-1344220021901214/4763116885";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper db = new DataBaseHelper(this);
        adView=(AdView) findViewById(R.id.adView);

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
                //.addTestDevice("A906482D0B3C5F47980E446DD6F1CF85") //cel orca
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
        adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }

    public void lanzarAcercaDe(){
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


    public void lanzarSeleccionLineasMedDias(View view){
        Intent i = new Intent(this,SeleccionLineasMediaDist.class);
        startActivity(i);
    }

    public void lanzarMisHorarios(View view){
        Intent i = new Intent(this,MisHorarios.class);
        startActivity(i);
    }

    public void lanzarDondeVoy(View view){
        Intent i = new Intent(this,MapaDondeVoy.class);
        startActivity(i);
    }
    public void pgWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/TecProSoftware"));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int i = 0;
        switch (id) {
            case R.id.acerca_de:
                lanzarAcercaDe();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
