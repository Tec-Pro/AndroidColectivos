package org.tecpro.colectivos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by nico on 25/04/14.
 */
public class ViewInfo extends Activity {

    Bundle extras;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_info);
        extras = getIntent().getExtras();
        this.setTitle(extras.getString("title"));
        TextView inf= (TextView) findViewById(R.id.infoText);
        inf.setText(extras.getString("info"));
    }

}
