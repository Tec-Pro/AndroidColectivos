package org.tecpro.colectivos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * Created by jacinto on 4/10/14.
 */
public class AcercaDe extends Activity{
        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acerca_de);
        }

    public void pgWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/TecProSoftware"));
        startActivity(intent);
    }
}
