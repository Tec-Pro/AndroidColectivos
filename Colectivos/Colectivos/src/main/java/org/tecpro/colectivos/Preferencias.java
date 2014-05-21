package org.tecpro.colectivos;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.SeekBar;

/**
 * Created by nico on 02/04/14.
 */
public class Preferencias extends PreferenceActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
        Bundle extras = getIntent().getExtras();
       // findPreference("distancia").setTitle(String.format("Máximo a caminar:\n  %s cuadras" , extras.get("cuadras").toString()));
        /*findPreference("distancia").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.valueOf(String.valueOf(newValue));
                preference.setTitle(String.format("Máximo a caminar:\n %d cuadras" , progress));
                return true;
            }
        });*/


        }

}