package org.tecpro.colectivos.lineas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.tecpro.colectivos.R;
import org.tecpro.colectivos.database.DataBaseHelper;

import java.util.List;



/**
 * Created by nico on 13/03/16.
 */
public class AdapterLineas extends RecyclerView.Adapter<AdapterLineas.LineaViewHolder> {
    private List<String> lineas;
    private DataBaseHelper managerDB;
    private View.OnClickListener listener;
    private Context context;

    public static class LineaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView txtLinea,txtColor, txtWhereIsIt,txtTimeTable,txtRoute;
        public ImageView imgHeart;

        public LineaViewHolder(View v) {
            super(v);
            txtLinea = (TextView) v.findViewById(R.id.txt_linea);
            txtColor = (TextView) v.findViewById(R.id.txt_color);
            txtColor.setVisibility(View.GONE);
            imgHeart = (ImageView) v.findViewById(R.id.img_heart);
            txtRoute = (TextView) v.findViewById(R.id.txt_see_route);
            txtTimeTable = (TextView) v.findViewById(R.id.txt_see_timetable);
            txtWhereIsIt = (TextView) v.findViewById(R.id.txt_where_is_it);

        }
    }

    public AdapterLineas(List<String> lineas, Context context,View.OnClickListener listener) {
        this.lineas = lineas;
        //inicio la base de datos
        managerDB = DataBaseHelper.getInstance(context);
        this.listener= listener;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return lineas.size();
    }

    @Override
    public LineaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_linea, viewGroup, false);
        return new LineaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LineaViewHolder viewHolder, final int i) {
        String linea = lineas.get(i);
        if (linea.contains("Rojo")) {
            linea = linea.replace(" Rojo", "");
            viewHolder.txtColor.setText("Rojo");
            viewHolder.txtColor.setTextColor(context.getResources().getColor(R.color.red));
            viewHolder.txtColor.setVisibility(View.VISIBLE);
        } else {
            if (linea.contains("Verde")) {
                linea = linea.replace(" Verde", "");
                viewHolder.txtColor.setText("Verde");
                viewHolder.txtColor.setTextColor(context.getResources().getColor(R.color.green));
                viewHolder.txtColor.setVisibility(View.VISIBLE);
            } else
                viewHolder.txtColor.setVisibility(View.GONE);
        }
        viewHolder.imgHeart.setImageResource(managerDB.existsFav(lineas.get(i)) ? R.drawable.ic_corazon_lleno : R.drawable.ic_corazon);
        viewHolder.txtLinea.setText(linea);
        if (this.listener != null) {
            viewHolder.imgHeart.setOnClickListener(listener);
            viewHolder.txtWhereIsIt.setOnClickListener(listener);
            viewHolder.txtRoute.setOnClickListener(listener);
            viewHolder.txtTimeTable.setOnClickListener(listener);
        }
        if (linea.contains("Cuarto") || linea.contains("UNRC") || linea.contains("Linea A")){
            viewHolder.txtWhereIsIt.setVisibility(View.GONE);
        viewHolder.txtRoute.setVisibility(View.GONE);
        }else{
            viewHolder.txtWhereIsIt.setVisibility(View.VISIBLE);
            viewHolder.txtRoute.setVisibility(View.VISIBLE);
        }
    }


    /*
Permite limpiar todos los elementos del recycler
 */
    public void clear() {
        lineas.clear();
        notifyDataSetChanged();
    }

    /*
Añade una lista completa de items
 */
    public void addAll(List<String> items) {
        lineas.addAll(items);
        notifyDataSetChanged();
    }

    public void set(List<String> items){
        lineas.clear();
        lineas.addAll(items);
        notifyDataSetChanged();
    }

}