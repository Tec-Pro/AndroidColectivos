package org.tecpro.colectivos;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by jacinto on 5/17/14.
 */
public class BaseAdapterMisHorarios extends BaseAdapter {
    private final Activity actividad;
    private final Vector<String> datos;

    public BaseAdapterMisHorarios(Activity actividad, Vector<String> datos) {
        super();
        this.actividad = actividad;
        this.datos = datos;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.elementAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list_mis_horarios, null, true);
        TextView textView = (TextView) view.findViewById(R.id.lblListItemH);
        textView.setTypeface(null, Typeface.BOLD);
        if(datos.elementAt(position).contains(" verde")){
            textView.setTextColor(Color.rgb(2, 150, 27));
        }else {
            if (datos.elementAt(position).contains(" rojo")) {
                textView.setTextColor(Color.RED);
            } else {
                textView.setTextColor(Color.BLACK);
            }
        }
        textView.setText(datos.elementAt(position));
        return view;
    }
}
