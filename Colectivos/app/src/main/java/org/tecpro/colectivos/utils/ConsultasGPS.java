package org.tecpro.colectivos.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tecpro.colectivos.model.ModelBondi;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nico on 16/03/16.
 */
public class ConsultasGPS {


    public static ArrayList<ModelBondi> getHorariosMiBondiYa(String linea,String pCodigoLinea,String pCodigoOrigen,String pCodigoDestino) throws IOException {
        URL url;
        HttpURLConnection conn;
        url = new URL("http://mibondiya.cba.gov.ar/MiBondi.asmx/GetHorarios");
        String body = "{\"pCodigoEmpresa\":\"9802\",\"pCodigoLinea\":\""+pCodigoLinea+"\",\"pCodigoOrigen\":\""+pCodigoOrigen+"\",\"pCodigoDestino\":\""+pCodigoDestino+"\",\"pCodigoParada\":\"\"}";
        conn = (HttpURLConnection) url.openConnection();
        //set the output to true, indicating you are outputting(uploading) POST data
        conn.setDoOutput(true);
        //once you set the output to true, you don’t really need to set the request method to post, but I’m doing it anyway
        conn.setRequestMethod("POST");
        //Android documentation suggested that you set the length of the data you are sending to the server, BUT
        // do NOT specify this length in the header by using conn.setRequestProperty("Content-Length", length);
        //use this instead.
        conn.setFixedLengthStreamingMode(body.getBytes().length);
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        //send the POST out
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(body);
        out.close();
        ArrayList<ModelBondi> listResult = new ArrayList<>();
        try {
            JSONArray json= new JSONObject(streamToString(conn.getInputStream())).getJSONArray("d");
            int i=0;
            while(i<json.length()){
                JSONObject jsonObject= json.getJSONObject(i);
                ModelBondi bondi =new ModelBondi();
                String lineaAux=jsonObject.getString("linea").toLowerCase().replace("roja", "rojo");
                lineaAux=lineaAux.replace("negro", "verde");
                if(lineaAux.contains(linea.toLowerCase())) {
                    bondi.setLinea(linea);
                    if(!jsonObject.getString("lat").isEmpty()) {
                        bondi.setLatLng(Double.valueOf(jsonObject.getString("lat")), Double.valueOf(jsonObject.getString("lon")));
                        bondi.setCoche(Integer.valueOf(jsonObject.getString("coche")));
                        if (!listResult.contains(bondi))
                            listResult.add(bondi);
                    }
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listResult;
    }
    public static String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


    public static ArrayList<ModelBondi> getHorarios(String linea,String pCodigoLinea,String pCodigoOrigen,String pCodigoDestino) throws IOException {
        URL url;
        HttpURLConnection conn;
        url=new URL("http://yaviene.com/usuario/respuesta.php");
        conn = (HttpURLConnection) url.openConnection();
        //set the output to true, indicating you are outputting(uploading) POST data
        conn.setDoOutput(true);
        //once you set the output to true, you don’t really need to set the request method to post, but I’m doing it anyway
        conn.setRequestMethod("POST");
        //Android documentation suggested that you set the length of the data you are sending to the server, BUT
        // do NOT specify this length in the header by using conn.setRequestProperty("Content-Length", length);
        //use this instead.
        conn.setDoInput(true);
        conn.setDoOutput(true);

        List<Pair<String,String>> params = new ArrayList<>();
        params.add(new Pair("sel_prov","5"));
        params.add(new Pair("c",""));
        params.add(new Pair("a",""));
        params.add(new Pair("sel_empresa","5=98=2"));
        params.add(new Pair("fecha","30/03/2016"));
        params.add(new Pair("sel_linea","5=98=2="+pCodigoLinea));
        params.add(new Pair("sel_origen","5=98=2="+pCodigoLinea+"="+pCodigoOrigen));
        params.add(new Pair("sel_destino","5=98=2="+pCodigoLinea+"="+pCodigoOrigen+"="+pCodigoDestino));
        params.add(new Pair("sel_origenp","5=98=2="+pCodigoLinea+"="+pCodigoOrigen+"="+pCodigoDestino+"="));
        params.add(new Pair("sel_rampa","off"));
        params.add(new Pair("buscar","Buscar"));
        params.add(new Pair("html5","0"));

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();

        ArrayList<ModelBondi> listResult = new ArrayList<>();
        //build the string to store the response text from the server
        String response= "";

//start listening to the stream

        Scanner inStream = new Scanner(conn.getInputStream());
//process the stream and store it in StringBuilder
        while(inStream.hasNextLine())
            response+=(inStream.nextLine());
        int indexFromCoche = response.indexOf("Coche");
        int indexToCoche = response.indexOf("&nbsp;<a href=\"https:", indexFromCoche);
        int indexFrom = response.indexOf("href=\"https://maps.google.com/?z=9&q=",indexFromCoche);
        int indexTo = response.indexOf("\" target", indexFrom);
        //System.out.println(new String("href=\"https://maps.google.com/?z=9&q=").length());
        boolean encontreRepetido = false; //si encuentro una coordenada que ya existe dejo de recorrer
        while(!encontreRepetido && indexFromCoche!=-1){
            ModelBondi bondi =new ModelBondi();
            bondi.setLinea(linea);
            String coche=response.substring(indexFromCoche+6, indexToCoche);
            bondi.setCoche(Integer.valueOf(coche));

            String[] latLongString= response.subSequence(indexFrom+37,indexTo).toString().split(",");
            bondi.setLatLng(new LatLng(Double.valueOf(latLongString[0]),Double.valueOf(latLongString[1])));
            if (!listResult.contains(bondi)){
                listResult.add(bondi);
                indexFromCoche = response.indexOf("Coche", indexToCoche);
                if(indexFromCoche != -1) {
                    indexToCoche = response.indexOf("&nbsp;<a href=\"https:", indexFrom);
                    indexFrom = response.indexOf("href=\"https://maps.google.com/?z=9&q=", indexTo);
                    indexTo = response.indexOf("\" target", indexFrom);
                }
            }else
                encontreRepetido=true;
        }
        return listResult;
    }


    private static String getQuery(List<Pair<String,String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String,String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }



    public static ArrayList<Integer> getCoches(String linea) throws IOException {
        URL url;
        HttpURLConnection conn;
        url = new URL("https://raw.githubusercontent.com/Tec-Pro/AndroidColectivos/master/coches.json");
        conn = (HttpURLConnection) url.openConnection();
        //set the output to true, indicating you are outputting(uploading) POST data
        conn.setDoOutput(true);
        //once you set the output to true, you don’t really need to set the request method to post, but I’m doing it anyway
        conn.setRequestMethod("GET");
        //Android documentation suggested that you set the length of the data you are sending to the server, BUT
        // do NOT specify this length in the header by using conn.setRequestProperty("Content-Length", length);
        //use this instead.
        //send the POST out
        ArrayList<Integer> listResult = new ArrayList<>();
        try {
            JSONObject response =new JSONObject(streamToString(conn.getInputStream()));
            if(!response.isNull(linea)) {
                JSONArray json = response.getJSONArray(linea);
                int i = 0;
                while (i < json.length()) {
                    JSONObject jsonObject = json.getJSONObject(i);
                    listResult.add(jsonObject.getInt("coche"));
                    i++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listResult;
    }

}
