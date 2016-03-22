package org.tecpro.colectivos.mapa;

/**
 * Created by nico on 16/05/14.
 */
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import org.tecpro.colectivos.R;

public class AdaptadorResultados extends BaseAdapter {





    private Context context;
    private ArrayList<Pair<String,String>>  items;

    public AdaptadorResultados(Context context, ArrayList<Pair<String,String>> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_linea_result, parent, false);
        }

        // Set data into the view.
        TextView tvTitle = (TextView) rowView.findViewById(R.id.nombre_linea);
        TextView tvdist = (TextView) rowView.findViewById(R.id.dist);
        Pair<String,String> item = items.get(position);

        tvTitle.setText(item.first);
        tvdist.setText(item.second+" km");


        return rowView;

    }


    public void setItems(ArrayList<Pair<String,String>>items){
        this.items = items;
        notifyDataSetChanged();
    }
}