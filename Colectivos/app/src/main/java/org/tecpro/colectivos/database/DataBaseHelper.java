package org.tecpro.colectivos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by nico on 14/03/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    private static DataBaseHelper sInstance;
    private static String DB_NAME = "colecticosApp";
    private static int DB_SCHEME_VERSION = 4;

    private static String tabla_favs ="favs";//tabla para tener los favoritos

    private Context context;


    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
        this.context = context;
    }


    public static synchronized DataBaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE '" + tabla_favs + "' (\n" +
                "\t'_id'\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t'linea'\tTEXT NOT NULL\n," +
                "\t'codigo'\tINTEGER NOT NULL\n" +
                ");");
        System.out.println("onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==4 ){
            try {
                db.execSQL("DROP TABLE favs;");
                System.out.println("drop");
            }catch (SQLiteException e){

            }
            try {
                db.execSQL("DROP TABLE mis_horarios;");
                System.out.println("drop");
            }catch (SQLiteException e){

            }
            onCreate(db);
        }
    }

    /**
     * retorna true si una linea es favorita
     * @param linea
     * @return
     */
    public boolean existsFav(String linea){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql=  "SELECT DISTINCT * FROM "+tabla_favs+" where linea = '"+linea+"' ;" ;
        Cursor c = db.rawQuery(sql, null);
        try {
            return !(c.getCount() == 0);
        } catch (Exception e){}
        return false ;
    }

    public void deleteFav(String linea) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] params = {linea};
        db.delete(tabla_favs, "linea =?", params);
    }

    public void insertFav(String linea,int codigo){
        if(!existsFav(linea)){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues register = new ContentValues();
            register.put("linea", linea);
            register.put("codigo", codigo);

            long i = db.insert(tabla_favs, null, register);

        }
    }

    public ArrayList<String> getFavs(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> ret = new ArrayList<>();
        String sql=  "SELECT DISTINCT linea FROM "+tabla_favs+" ORDER BY codigo;" ;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                ret.add(c.getString(0));
            }while(c.moveToNext());
        }
        return ret;
    }

}
