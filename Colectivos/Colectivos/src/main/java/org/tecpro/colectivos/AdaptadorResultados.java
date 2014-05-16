package org.tecpro.colectivos;

/**
 * Created by nico on 16/05/14.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class AdaptadorResultados extends BaseAdapter {





    private Context context;
    private List<String> items;

    public AdaptadorResultados(Context context, List<String> items) {
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
            rowView = inflater.inflate(R.layout.lineas_result, parent, false);
        }

        // Set data into the view.
        TextView tvTitle = (TextView) rowView.findViewById(R.id.nombre_linea);
        String item = items.get(position);

        tvTitle.setText(item);
        if(tvTitle.getText().toString().contains(" VERDE")){
            tvTitle.setTextColor(Color.rgb(2, 150, 27));
        }else {
            if (tvTitle.getText().toString().contains(" ROJO")) {
                tvTitle.setTextColor(Color.RED);
            } else {
                tvTitle.setTextColor(Color.BLACK);
            }
        }

        return rowView;

    }

}