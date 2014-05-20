package org.tecpro.colectivos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jacinto on 5/12/14.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "colecticosApp";
    private static int DB_SCHEME_VERSION = 1;
    public static final String CREATE_TABLE_MIS_HORARIOS = "CREATE TABLE mis_horarios ( _id INTEGER PRIMARY KEY AUTOINCREMENT, lugar text not null, linea text not null, horario text not null, dia text not null);";
    private String[] columnas={"_id", "lugar", "linea", "horario", "dia"};


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS misHorarios");
        db.execSQL("DROP TABLE IF EXISTS mis_horarios");
            db.execSQL(CREATE_TABLE_MIS_HORARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS misHorarios");
        onCreate(db);
    }

    public long insertar (String tabla, String lugar,String linea, String horario, String dia){
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert(tabla,null,generarContentValues(lugar,linea,horario,dia));
        db.close();
        return i;
    }

    private ContentValues generarContentValues(String lugar,String linea, String horario,String dia){
        ContentValues contenedor = new ContentValues();
        //contenedor.put("_id", (byte[]) null);
        contenedor.put("lugar",lugar);
        contenedor.put("linea",linea);
        contenedor.put("horario",horario);
        contenedor.put("dia",dia);
        return contenedor;
    }

    public Cursor cargarCursorMisParadas(String[] columnas){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("mis_horarios",columnas, null,null,null,null,null);
        db.close();
        return c;
    }

    public Cursor cargarCursorXLinea(String[] linea){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("mis_horarios",columnas,"linea=?",linea,null,null,null);
        c.getCount();
        db.close();
        return c;
    }

    public Cursor cargarCursorTodasMisParadas(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("mis_horarios",columnas, null,null,null,null,null);
        db.close();
        return c;
    }
    public Cursor cargarCursorLineasCargadas(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] linea={"linea"};
        Cursor c = db.query("mis_horarios",linea, null,null,null,null,"linea");
        c.getCount();
        db.close();
        return c;
    }

    public Cursor getIdEditar(String linea,String lugar,String dia,String hora){
        SQLiteDatabase db = this.getWritableDatabase();
        String seleccion = ("linea ='"+linea+"'and lugar='"+lugar+"'and dia='"+dia+"'and horario='"+hora+"'");
        String[] id= {"_id"};
        Cursor c = db.query("mis_horarios",id, seleccion ,null,null,null,null);
        c.getCount();
        db.close();
        return c;
    }

    public void modificar(String lugar,String linea, String horario,String dia, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update("mis_horarios",generarContentValues(lugar,linea,horario,dia),"_id =?"+String.valueOf(id),null);
        db.close();
    }

    public void eliminar(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("mis_horarios","_id =?"+String.valueOf(id),null);
        db.close();
    }

    public void eliminar(String linea,String lugar,String dia,String hora){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("mis_horarios","linea ='"+linea+"'and lugar='"+lugar+"'and dia='"+dia+"' and horario='"+hora+"'",null);
        db.close();
    }
}
