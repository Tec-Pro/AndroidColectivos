package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.*;


public class MainActivity extends Activity {
    private AdView adView;
    private LinearLayout lytMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear adView.
       // adView = new AdView(this);
       // adView.setAdUnitId("ca-app-pub-1344220021901214/4763116885");
      //  adView.setAdSize(AdSize.SMART_BANNER);

        // Buscar LinearLayout suponiendo que se le ha asignado
        // el atributo android:id="@+id/mainLayout".
        //LinearLayout layout = (LinearLayout)findViewById(R.id.LinearMain);

        // Añadirle adView.
       // layout.addView(adView);

        // Iniciar una solicitud genérica.
       // AdRequest adRequest = new AdRequest.Builder().build();

        // Cargar adView con la solicitud de anuncio.
       // adView.loadAd(adRequest);

       //AdView adView = (AdView)this.findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);

    // Crear adView.
    adView = new AdView(this);
    adView.setAdUnitId("ca-app-pub-1344220021901214/4763116885");
    adView.setAdSize(AdSize.SMART_BANNER);

    // Buscar LinearLayout suponiendo que se le ha asignado
    // el atributo android:id="@+id/mainLayout".
    LinearLayout layout = (LinearLayout)findViewById(R.id.LinearMain);

    // Añadirle adView.
    layout.addView(adView);

    // Iniciar una solicitud genérica.
    AdRequest adRequest = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)       // Emulator
            .addTestDevice("44C97DC57E38580486B7D0F1922432A6") // My test phone
            .build();

    // Cargar adView con la solicitud de anuncio.
    adView.loadAd(adRequest);
        adView.bringToFront();
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

    public void pgWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/TecProSoftware"));
        startActivity(intent);
    }

}
