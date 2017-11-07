package com.samcotec.ahlalquran;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Objects;


/**
 * Created by MyTh on 3/7/2017.
 */

class dbClass extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db.db";

    private static String FULL_PATH_DB = null ;

    private static dbClass db = null;
    dbClass(   ) {
       super( App.getApp(), DATABASE_NAME, null, DATABASE_VERSION);
       boolean dbExists = false;
       Context context = App.getApp();

        try{
            FULL_PATH_DB = context.getDatabasePath( DATABASE_NAME ).getPath();
            dbExists = true;
        }
        catch (Exception e ){
            Log.e( this.toString(), e.getMessage(), e );
        }

        if( FULL_PATH_DB.isEmpty() || FULL_PATH_DB == null || Objects.equals( FULL_PATH_DB, "" ) || !dbExists ){
//            tools.alert( context, "Error", "No db connected Support");
            Log.e( this.toString(), "No DB path" );
        }
    }


    //  DataBase exists
    public static boolean exists() {
        SQLiteDatabase checkDB = null;
        boolean exists = false;
        try {
            checkDB = SQLiteDatabase.openDatabase( FULL_PATH_DB ,null ,SQLiteDatabase.OPEN_READONLY );

            exists = true;
        }
        catch (SQLiteException e) {
             Log.e( "dbClass exists():", e.getMessage(), e );
        }
        finally {
            if( checkDB != null )
                checkDB.close();
        }
        return exists ;
    }

    //  table exists
    public static boolean tableExeists( String tableName ){

        boolean tableExists = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try{
            db = SQLiteDatabase.openDatabase( FULL_PATH_DB ,null ,SQLiteDatabase.OPEN_READONLY );
            cursor = db.query( tableName, null, null, null, null, null, null );
            tableExists = ( cursor != null );
        }
        catch (Exception e) {
            Log.d( "dbClass tableExeists", e.getMessage(), e );
        }
        finally {
            if( cursor != null )
                cursor.close();

            if( db != null ) {
                db.close();
            }
        }
        return tableExists;
    }

    //  column exists
    public static boolean checkCol ( Cursor cursor, String columnName ){
        return  cursor.getColumnIndex( columnName ) != -1 ;
    }

    public static dbClass getDb() {
        return db;
    }

    public static void setDb( dbClass db ) {
        dbClass.db = db;
    }


    //   Add Data To database
    public long add( String table, JSONObject vals ){
        SQLiteDatabase db = null;
        ContentValues values = new ContentValues();
        long newId = 0;
        try {
            db = this.getWritableDatabase();
            Iterator<String> len = vals.keys();
            while( len.hasNext() ){
                String key = len.next();
                values.put( key, vals.get( key  ).toString() );
            }

            newId = db.insert( table, null, values );
        }
        catch(Exception e){
            Log.e("dbClass add()", e.getMessage(), e);
        }
        finally{
            if (db != null )
                db.close();
        }
        return newId ;
    }

    public JSONArray getData (String tableName, String where ){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        JSONArray rows = new JSONArray();
        String sqlQ = "SELECT * FROM "+ tableName + " "+ ( where != null  && !where.isEmpty()  ? " WHERE " + where + " ;" : "" );

        try{
            db = this.getReadableDatabase();
            cursor = db.rawQuery( sqlQ, null );

            if( cursor.moveToFirst() ) {
                String[] cols = cursor.getColumnNames();
                do {
                    JSONObject rowsData = new JSONObject();
                    for (String col : cols)
                        rowsData.put(col, String.valueOf(cursor.getString(cursor.getColumnIndex(col))));

                    rows.put(rowsData);
                }
                while ( cursor.moveToNext() );
            }
        }
        catch (Exception e){
            Log.e("dbClass getData()", e.getMessage(), e );
        }
        finally{
            if( db != null )
                db.close();

            if( cursor!= null )
                cursor.close();
        }
        return rows ;
    }


    public int delete( String tableName,
                       String selection,
                       String[] selectionArgs  ){
        SQLiteDatabase db = null;
        int result = 0 ;

        try {
            if( selection == null || Objects.equals( selection , "" )  || selection.isEmpty() ) {
                selection = "";
            }
            if( selectionArgs == null ||  selectionArgs.length < 1 ) {
                selectionArgs = new String[0];
            }

            db = this.getReadableDatabase();
            result = db.delete( tableName, selection, selectionArgs );
        }
        catch (Exception e){
            Log.e( "dbClass delete()", e.getMessage(), e );

        }
        finally{
            if( db != null )
                db.close();
        }
        return result;
    }


    public int upd( String tableName,
                    JSONObject vals,
                    String selection,
                    String[] selectionArgs){
        SQLiteDatabase db = null;
        int result = 0;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Iterator keys = vals.keys();

            while( keys.hasNext() ){
                String key =  keys.next().toString() ;
                values.put( key, vals.get(key).toString() );
            }
            result = db.update( tableName, values, selection, selectionArgs );

        }
        catch ( Exception e ){
            Log.e("dbClass upd()", e.getMessage(), e );
        }
        finally{
            if(db != null)
                db.close();
        }
        return result;
    }


    public  void exec( String sql ){
        SQLiteDatabase db = null;
        if( sql != null && !Objects.equals( sql, "") ){
            try{
                db = this.getWritableDatabase();
                db.execSQL( sql );
            }
            catch (Exception e){
                Log.e( "dbClass exec()", e.getMessage(), e );
            }
            finally {
                if(db != null)
                    db.close();
            }
        }
    }

    private void createDB( SQLiteDatabase db ) {
        BufferedReader reader = null;
        String line = "", folderDB = "database";
        try {
            String[] path = App.getApp().getAssets().list(folderDB);

            for( String sql : path ) {

                reader = new BufferedReader(new InputStreamReader(App.getApp().getAssets().open(folderDB + "/" +sql)));
                while ( ( line = reader.readLine() ) != null) {
                    if (
                            line.startsWith("COMM") || Objects.equals(line, "COMMIT;")
                                    || line.startsWith("BEGIN") || Objects.equals(line, "BEGIN TRANSACTION;")
                                    || Objects.equals(line, "#")
                            )
                        continue;
                    Log.d("create DB", line);
                    db.execSQL(line);
                }
            }

        } catch (IOException e) {
            Log.e(this.toString(),e.getMessage(),e);
        }


    }
    @Override
    public void onCreate( SQLiteDatabase db ) {
        createDB( db );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            err.println("       [onUpgrade]        ");
//        db.execSQL( SQL_DELETE_ENTRIES );
        onCreate( db );
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            err.println("       [onDowngrade]        ");
        onUpgrade(db, oldVersion, newVersion);
    }

    public String toString(){
             return "dbClass";
    }
}