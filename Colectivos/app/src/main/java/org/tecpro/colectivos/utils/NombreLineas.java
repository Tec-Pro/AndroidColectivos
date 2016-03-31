package org.tecpro.colectivos.utils;

import java.util.ArrayList;

/**
 * Created by nico on 15/03/16.
 */
public class NombreLineas {

    public static  int getCodigo(String linea){
        switch (linea){
            case "Linea 1 Rojo":
                return 1;
            case "Linea 1 Verde":
                return 2;
            case "Linea 2 Rojo":
                return 3;
            case "Linea 2 Verde":
                return 4;
            case "Linea 3":
                return 5;
            case "Linea 4":
                return 6;
            case "Linea 5":
                return 7;
            case "Linea 6":
                return 8;
            case "Linea 7":
                return 9;
            case "Linea 8 Rojo":
                return 10;
            case "Linea 8 Verde":
                return 11;
            case "Linea 9 Rojo":
                return 12;
            case "Linea 9 Verde":
                return 13;
            case "Linea 10":
                return 14;
            case "Linea 11":
                return 15;
            case "Linea 12":
                return 16;
            case "Linea 13":
                return 17;
            case "Linea 14":
                return 18;
            case "Linea 15":
                return 19;
            case "Linea 16":
                return 20;
            case "Linea 17":
                return 21;
            case "Linea 18":
                return 22;
            case"Linea A":
                return 23;
            case "Río Cuarto - Las Higueras":
                return 24;
            case "Río Cuarto - Homlberg":
                return 25;
        }
        return -1;
    }

    private static ArrayList<String> getLineasUrbanas(){
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("Linea 1 Rojo");
        lineas.add("Linea 1 Verde");
        lineas.add("Linea 2 Rojo");
        lineas.add("Linea 2 Verde");
        lineas.add("Linea 3");
        lineas.add("Linea 4");
        lineas.add("Linea 5");
        lineas.add("Linea 6");
        lineas.add("Linea 7");
        lineas.add("Linea 8 Rojo");
        lineas.add("Linea 8 Verde");
        lineas.add("Linea 9 Rojo");
        lineas.add("Linea 9 Verde");
        lineas.add("Linea 10");
        lineas.add("Linea 11");
        lineas.add("Linea 12");
        lineas.add("Linea 13");
        lineas.add("Linea 14");
        lineas.add("Linea 15");
        lineas.add("Linea 16");
        lineas.add("Linea 17");
        lineas.add("Linea 18");
        return lineas;
    }

    private static ArrayList<String> getLineasInterurbanas(){
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("Linea A");
        lineas.add("Río Cuarto - Las Higueras");
        lineas.add("Río Cuarto - Homlberg");
        return lineas;
    }


    public static ArrayList<String> getDaysFromLinea(String linea){
        ArrayList<String> ret= new ArrayList<>();
        switch (linea){
            case "Linea 1 Rojo":
                ret.add("Lunes a Viernes");
                ret.add("Especiales");
                ret.add("Sábados, Domingos y feriados");
                break;
            case "Linea 1 Verde":
                ret.add("Lunes a Viernes");
                ret.add("Especiales");
                ret.add("Sábados, Domingos y feriados");
                break;
            case "Linea 2 Rojo":
                ret.add("Lunes a Viernes");
                ret.add("Sábados, Domingos y feriados");
                break;
            case "Linea 2 Verde":
                ret.add("Lunes a Viernes");
                ret.add("Especiales");
                ret.add("Sábados, Domingos y feriados");
                break;
            case "Linea 3":
                ret.add("Lunes a Domingo");
                ret.add("Especiales");
                break;
            case "Linea 4":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 5":
                ret.add("Lunes a Viernes");
                ret.add("Especiales");
                ret.add("Sábados, Domingos y feriados");
                ret.add("Verano");

                break;
            case "Linea 6":
                ret.add("Lunes a Domingo");
                ret.add("Especiales");
                break;
            case "Linea 7":
                ret.add("Lunes a Viernes");
                ret.add("Sábados, Domingos y feriados");
                break;
            case "Linea 8 Rojo":
                ret.add("Lunes a Viernes");
                ret.add("Sábados");
                ret.add("Domingos y feriados");
                break;
            case "Linea 8 Verde":
                ret.add("Lunes a Viernes");
                ret.add("Sábados");
                break;
            case "Linea 9 Rojo":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 9 Verde":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 10":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 11":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 12":
                ret.add("Lunes a Viernes");
                ret.add("Sábados");
                ret.add("Domingos y feriados");
                break;
            case "Linea 13":
                ret.add("Lunes a Viernes");
                ret.add("Sábados, Domingos y feriados");
                break;
            case "Linea 14":
                ret.add("Lunes a Domingo (primera vuelta)");
                ret.add("Lunes a Domingo");
                break;
            case "Linea 15":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 16":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 17":
                ret.add("Lunes a Domingo");
                break;
            case "Linea 18":
                ret.add("Lunes a Viernes");
                ret.add("Especiales");
                ret.add("Sábados, Domingos y feriados");
                break;
            case"Linea A":
                ret.add("Lunes a Domingo");
                break;
            case "Río Cuarto - Las Higueras":
                ret.add("Lunes a Viernes");
                ret.add("Sábados, Domingos y feriados");
                break;
            case "Río Cuarto - Homlberg":
                ret.add("Lunes a Viernes");
                ret.add("Sábados");
                ret.add("Domingos y feriados");
                break;
        }
        return ret;
    }

    /**
     * 1: urbanas
     * 2: interurbanas
     * @param type
     * @return
     */
    public static ArrayList<String> getLineas(int type){
        switch (type){
            case 1:
                return getLineasUrbanas();
            case 2:
                return getLineasInterurbanas();
        }
        return null;
    }
}
