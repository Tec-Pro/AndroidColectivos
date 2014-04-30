package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.*;


public class MainActivity extends Activity {
    private AdView adView;
    private LinearLayout lytMain;
    private static final  String AD_UNIT_ID = "ca-app-pub-1344220021901214/4763116885";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView=(AdView) findViewById(R.id.adView);

        //adView = new AdView(this);
        //adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId(AD_UNIT_ID);

        // Buscar LinearLayout suponiendo que se le ha asignado
        // el atributo android:id="@+id/mainLayout".
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutGrande);

        // Añadirle adView.
        //layout.addView(adView);

// Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
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


    public void lanzarSeleccionLineasMedDias(View view){
        Intent i = new Intent(this,SeleccionLineasMediaDist.class);
        startActivity(i);
    }

    public void pgWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/TecProSoftware"));
        startActivity(intent);
    }

}
