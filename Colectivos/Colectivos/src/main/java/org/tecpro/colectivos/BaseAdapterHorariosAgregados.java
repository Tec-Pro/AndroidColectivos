package org.tecpro.colectivos;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by jacinto on 5/18/14.
 */
public class BaseAdapterHorariosAgregados extends BaseAdapter {
    private final Activity actividad;
    private final Vector<String> horas;
    private final Vector<String> dias;
    private final Vector<String> lugares;

    public BaseAdapterHorariosAgregados(Activity actividad, Vector<String> horas, Vector<String> dias, Vector<String> lugares) {
        this.actividad = actividad;
        this.horas = horas;
        this.dias = dias;
        this.lugares = lugares;
    }

    @Override
    public int getCount() {
        return lugares.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list_horarios, null, true);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView4 = (TextView) view.findViewById(R.id.textView4);
        TextView textView6 = (TextView) view.findViewById(R.id.textView6);
        textView2.setTypeface(null, Typeface.BOLD);
        textView4.setTypeface(null, Typeface.BOLD);
        textView6.setTypeface(null, Typeface.BOLD);
        textView2.setText(lugares.get(position));
        textView4.setText(dias.get(position));
        textView6.setText(horas.get(position));
        return view;
    }

    public Vector<String> getHoras() {
        return horas;
    }

    public Vector<String> getDias() {
        return dias;
    }

    public Vector<String> getLugares() {
        return lugares;
    }


}
