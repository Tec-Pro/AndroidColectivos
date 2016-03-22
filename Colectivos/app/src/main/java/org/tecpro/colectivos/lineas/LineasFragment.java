package org.tecpro.colectivos.lineas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.tecpro.colectivos.R;
import org.tecpro.colectivos.database.DataBaseHelper;
import org.tecpro.colectivos.mapa.MapaDondeEsta;
import org.tecpro.colectivos.mapa.MapaVerRecorrido;
import org.tecpro.colectivos.ver_horario.VerHorarioActivity;

import java.util.ArrayList;
import java.util.List;

import static org.tecpro.colectivos.utils.NombreLineas.getCodigo;
import static org.tecpro.colectivos.utils.NombreLineas.getLineas;


/**
 * Created by nico on 13/03/16.
 */
public class LineasFragment extends Fragment {

    /*
Declarar instancias globales
*/
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private View view;
    private AdapterLineas adapter;
    private boolean isForFavorites = false;
    private DataBaseHelper managerDB;
    private List<String> lineas;
    private int typeLinea = 1;
    private Spinner spnTypeLinea;

    public  LineasFragment(){
    }

    public void setIsForFavorites(boolean isForFavorites) {
        this.isForFavorites = isForFavorites;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.lineas_fragment, container, false);

        // Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.list_recycler);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(lManager);
        //inicio la base de datos
        managerDB = DataBaseHelper.getInstance(getActivity());
        View.OnClickListener onClickListener =new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int i = 0;
                boolean found = false;
                while (i < recycler.getChildCount() && !found) {
                    TextView txtColor = (TextView)recycler.getChildAt(i).findViewById(R.id.txt_color);
                    TextView txtLinea = (TextView)recycler.getChildAt(i).findViewById(R.id.txt_linea);
                    String linea= txtLinea.getText().toString();
                    if(txtColor.getVisibility()== View.VISIBLE)
                        linea = linea + " "+ txtColor.getText().toString();

                    if (v == recycler.getChildAt(i).findViewById(R.id.img_heart)) {
                        found = true;
                        clickFav(linea, (ImageView)v);
                    }
                    if (v == recycler.getChildAt(i).findViewById(R.id.txt_see_timetable)) {
                        found = true;
                        Intent intent = new Intent(getContext(),VerHorarioActivity.class);
                        intent.putExtra("linea", linea);
                        startActivity(intent);
                    }
                    if (v == recycler.getChildAt(i).findViewById(R.id.txt_see_route)) {
                        //ver recorrido
                        found = true;
                        Intent intent = new Intent(getContext(),MapaVerRecorrido.class);
                        intent.putExtra("linea", linea);
                        startActivity(intent);
                    }
                    if (v == recycler.getChildAt(i).findViewById(R.id.txt_where_is_it)) {
                        //ver donde esta
                        found = true;
                        Intent intent = new Intent(getContext(), MapaDondeEsta.class);
                        switch (linea){
                            case "Linea 1 Rojo":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","7");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","206");
                                startActivity(intent);
                                break;
                            case "Linea 1 Verde":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","6");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","206");
                                startActivity(intent);
                                break;
                            case "Linea 2 Verde":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","9");
                                intent.putExtra("pCodigoOrigen","212");
                                intent.putExtra("pCodigoDestino","215");
                                startActivity(intent);
                                break;
                            case "Linea 2 Rojo":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","8");
                                intent.putExtra("pCodigoOrigen","212");
                                intent.putExtra("pCodigoDestino","215");
                                startActivity(intent);
                                break;
                            case "Linea 3":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","10");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","218");
                                startActivity(intent);
                                break;
                            case "Linea 4":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","22");
                                intent.putExtra("pCodigoOrigen","232");
                                intent.putExtra("pCodigoDestino","24");
                                startActivity(intent);
                                break;
                            case "Linea 5":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","11");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","222");
                                startActivity(intent);
                                break;
                            case "Linea 6":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","21");
                                intent.putExtra("pCodigoOrigen","212");
                                intent.putExtra("pCodigoDestino","24");
                                startActivity(intent);
                                break;
                            case "Linea 7":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","27");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","126");
                                startActivity(intent);
                                break;
                            case "Linea 8 Rojo":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","13");
                                intent.putExtra("pCodigoOrigen","212");
                                intent.putExtra("pCodigoDestino","200");
                                startActivity(intent);
                                break;
                            case "Linea 8 Verde":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","12");
                                intent.putExtra("pCodigoOrigen","212");
                                intent.putExtra("pCodigoDestino","24");
                                startActivity(intent);
                                break;
                            case "Linea 9 Verde":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","14");
                                intent.putExtra("pCodigoOrigen","232");
                                intent.putExtra("pCodigoDestino","24");
                                startActivity(intent);
                                break;
                            case "Linea 10":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","23");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","100");
                                startActivity(intent);
                                break;
                            case "Linea 11":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","24");
                                intent.putExtra("pCodigoOrigen","232");
                                intent.putExtra("pCodigoDestino","24");
                                startActivity(intent);
                                break;
                            case "Linea 12":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","15");
                                intent.putExtra("pCodigoOrigen","232");
                                intent.putExtra("pCodigoDestino","24");
                                startActivity(intent);
                                break;
                            case "Linea 13":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","16");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","224");
                                startActivity(intent);
                                break;
                            case "Linea 14":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","26");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","125");
                                startActivity(intent);
                                break;
                            case "Linea 15":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","17");
                                intent.putExtra("pCodigoOrigen","24");
                                intent.putExtra("pCodigoDestino","212");
                                startActivity(intent);
                                break;
                            case "Linea 16":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","25");
                                intent.putExtra("pCodigoOrigen","204");
                                intent.putExtra("pCodigoDestino","182");
                                startActivity(intent);
                                break;
                            case "Linea 17":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","18");
                                intent.putExtra("pCodigoOrigen","25");
                                intent.putExtra("pCodigoDestino","1");
                                startActivity(intent);
                                break;
                            case "Linea 18":
                                intent.putExtra("linea",linea);
                                intent.putExtra("pCodigoLinea","19");
                                intent.putExtra("pCodigoOrigen","184");
                                intent.putExtra("pCodigoDestino","24");
                                startActivity(intent);
                                break;
                            default:
                                Snackbar.make(v,"Próximamente esta linea", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    i++;
                }
            }
        };
        spnTypeLinea = (Spinner)view.findViewById(R.id.spn_type_linea);
        if (!isForFavorites){
            lineas = getLineas(typeLinea);
            spnTypeLinea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    typeLinea = position + 1;
                    lineas = getLineas(typeLinea);
                    adapter.set(lineas);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else{
            lineas = managerDB.getFavs();

            spnTypeLinea.setVisibility(View.GONE);
        }
        adapter = new AdapterLineas(lineas,getContext(), onClickListener);

        // Crear un nuevo adaptador
        recycler.setAdapter(adapter);
        if(isForFavorites && adapter.getItemCount()==0){
            view.findViewById(R.id.noDataLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.regular_layout).setVisibility(View.GONE);
        }
        return view;
    }

    public void reload(){
        if(isForFavorites)
            loadFavorites();
        else
            adapter.notifyDataSetChanged();
    }

    private void loadFavorites(){
        adapter.set(managerDB.getFavs());
        if(isForFavorites && adapter.getItemCount()==0) {
            view.findViewById(R.id.noDataLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.regular_layout).setVisibility(View.GONE);
        }
        else{
            view.findViewById(R.id.noDataLayout).setVisibility(View.GONE);
            view.findViewById(R.id.regular_layout).setVisibility(View.VISIBLE);
        }
    }



    public void clickFav(final String linea,ImageView viewFav){
        final int index=lineas.indexOf(linea);
        if(managerDB.existsFav(linea)){
            managerDB.deleteFav(linea);
            if(isForFavorites) {
                lineas.remove(index);
                adapter.notifyItemRemoved(index);
            }
            else {
                adapter.notifyItemChanged(index);
            }
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    managerDB.insertFav(linea,getCodigo(linea));
                    if(isForFavorites)
                        loadFavorites();
                    else
                        adapter.notifyItemChanged(index);
                }
            };
            Snackbar.make(viewFav, "Se ha eliminado de favoritos", Snackbar.LENGTH_LONG).setAction("Deshacer",onClickListener).setActionTextColor(getResources().getColor(R.color.red)).show();

        }
        else{
            managerDB.insertFav(lineas.get(index), getCodigo(lineas.get(index)));
            viewFav.setImageResource(R.drawable.ic_corazon_lleno);
        }
    }



    public void filter(String filter){
        if(!isForFavorites) {
            if (filter.isEmpty()) {
                lineas = getLineas(typeLinea);
            }else{
                ArrayList<String> lineasAux = new ArrayList<>();
                for (String linea : getLineas(typeLinea)) {
                    if (linea.contains(filter)) {
                        lineasAux.add(linea);
                    }
                }
                lineas = lineasAux;
            }
            adapter.set(lineas);
            adapter.notifyDataSetChanged();
        }
    }



}