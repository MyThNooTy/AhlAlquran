package com.samcotec.ahlalquran;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;




public class setting {
    private final String TABLE = "setting";

    private String
            sInputFullName = "",
            sInutEmail = "",
            sInputBirthday = "";

    private int ID;

    public int getAPIID() {
        return APIID;
    }

    public void setAPIID(int APIID) {
        this.APIID = APIID;
    }

    private int APIID;
    private long sInputMobile;
    private int sAGE;
    private int sSEX;
    private int ACTIVE;
    private String LANG;
    private int NOTI;

    public setting( int id ) {

        JSONArray f ;
        JSONObject  data;
        boolean add;
        long newId = 0 ;

        try{
            f = dbClass.getDb().getData( TABLE, "") ;
            if( f.length() > 0 ){
                 if( f.length() > 1){
                     dbClass.getDb().delete( TABLE, " ID != ? ", new String[]{String.valueOf(id)});
                     f = dbClass.getDb().getData( TABLE, "") ;
                 }
                //out.println(f);

                 data = f.getJSONObject(0);
                 prepare( data );
                add = false;
            }
            else
                add = true;

            if( add ){
                JSONObject vals = new JSONObject();

                try {
                    vals.put( "ID", id );
                    vals.put( "NAME", "" );
                    vals.put( "B_DATE", "" );
                    vals.put( "EMAIL", "" );
                    vals.put( "ACTIVE", 0 );
                    vals.put( "APIID", 0 );

                    dbClass.getDb().add(TABLE, vals );
                }
                catch (JSONException e) {
                    Log.e(this.toString(), e.getMessage(), e) ;
                }
            }
        }
        catch (Exception e ){
            Log.e( this.toString(), e.getMessage(), e );
        }
    }

    private void prepare( int id ){
        try {
            JSONArray f = dbClass.getDb().getData( TABLE, " ID = '"+ id +"'" ) ;
            JSONObject data = new JSONObject();
            if( f.length() > 0 ){
                data = f.getJSONObject(0);
                this.prepare(data);
            }
        } catch (JSONException e) {
            Log.e(this.toString(), e.getMessage(), e);
        }
    }

    private void prepare( JSONObject data ){
        try{
            this.ID = data.getInt("ID") ;
            this.sInputFullName = data.getString("NAME");
            this.sInputMobile = data.getInt("NUMBER");
            this.sInutEmail = data.getString("EMAIL");
            this.sAGE = data.getInt("AGE");
            this.sInputBirthday =  data.getString("B_DATE");
            this.ACTIVE = data.getInt("ACTIVE");
            this.NOTI = data.getInt("NOTI");
            this.sSEX = data.getInt("SEX");
            this.APIID = data.getInt("APIID");
            this.LANG = data.getString("LANG");
        }
        catch( JSONException e ){
            Log.e(this.toString(), e.getMessage() ,e );
        }

    }

    private void resetMe( int id ){
        this.ID = id;
        this.reset();
    }

    private void resetMe( ){
        this.ID = 0;
        this.reset();
    }

    private void reset(){
        this.sInputFullName = "";
        this.sInputMobile = 0;
        this.sInutEmail = "";
        this.sInputBirthday = "";
        this.sAGE = 0;
        this.ACTIVE = 0;
        this.NOTI = 0;
        this.sSEX = 0;
        this.APIID = 0;
        this.LANG = "ar";
    }

    public static void prepareAPI(){

    }
    boolean doInSql(){
        JSONObject vals = new JSONObject();
        try {
            vals.put( "NAME", this.sInputFullName );
            vals.put( "NUMBER", this.sInputMobile );
            vals.put( "EMAIL", this.sInutEmail );
            vals.put( "AGE", this.sAGE );
            vals.put( "B_DATE", this.sInputBirthday );
            vals.put( "ACTIVE", this.ACTIVE );
            vals.put( "NOTI", this.NOTI );
            vals.put( "SEX", this.sSEX );
            vals.put( "APIID", this.APIID );
            vals.put( "LANG", this.LANG );
        }
        catch (JSONException e) {
          Log.e(this.toString(), e.getMessage(), e) ;
        }


        int upd = dbClass.getDb().upd( TABLE, vals, "ID = ? ", new String[]{ String.valueOf( this.ID ) });

        return upd > 0 ;
    }

    public JSONObject getData(){
        JSONObject result = new JSONObject();
        try {
            result.put( "ID", this.ID);
            result.put( "NAME", this.sInputFullName );
            result.put( "NUMBER", this.sInputMobile );
            result.put( "EMAIL", this.sInutEmail );
            result.put( "AGE", this.sAGE );
            result.put( "B_DATE", this.sInputBirthday );
            result.put( "ACTIVE", this.ACTIVE );
            result.put( "NOTI", this.NOTI );
            result.put( "SEX", this.sSEX );
            result.put( "APIID", this.APIID );
            result.put( "LANG", this.LANG );
        }
        catch (JSONException e) {
            Log.e(this.toString() + " getData()", e.getMessage(),e );
        }

        return result ;
    }

    public void setID ( int a ){
        this.ID = a;
    }

    public void setID ( long a ){
        this.ID = (int) a;
    }

    public long getID ( ){
        return this.ID;
    }

    public void setFullName ( String a ){
        this.sInputFullName = a;
    }

    public String getFullName ( ){
        return !Objects.equals(this.sInputFullName, "null")  && !this.sInputFullName.isEmpty() ? this.sInputFullName : "" ;
    }

    public void setEmail ( String a ){
        this.sInutEmail = a;
    }

    public String getEmail ( ){
        return !Objects.equals(this.sInutEmail, "null") &&  !this.sInutEmail.isEmpty() ? this.sInutEmail : "";

    }

    public void setBirthday ( String a ){
        this.sInputBirthday = a;
    }
    public String getBirthday ( ){
        return this.sInputBirthday ;
    }

    public void setLang ( String a ){
        this.sInputBirthday = a;
    }
    public String getLang ( ){
        return !Objects.equals(this.LANG, "null") && this.LANG.isEmpty()  ? this.sInputBirthday :"";
    }

    public void setMobile ( String a ){
        this.sInputMobile = Long.parseLong(a) ;
    }

    public long getMobile ( ){
        return this.sInputMobile ;
    }

    public String getTable ( ){
        return TABLE ;
    }

    public void setsAge ( int a ){
        this.sAGE = a;
    }
    public void setsAge ( long a ){
        this.sAGE = (int) a;
    }
    public long getsAge ( ){
        return this.sAGE;
    }

    public void setsSEX ( int a ){
        this.sSEX = a;
    }
    public void setsSEX ( long a ){
        this.sSEX = (int) a;
    }
    public long getsSEX ( ){
        return this.sSEX;
    }

    public void setActive ( int a ){
        this.ACTIVE = a ;
    }

    public boolean isActive(){
        return this.ACTIVE != 0;
    }

    public void setNoti ( boolean a ){
        this.NOTI = a ? 1 : 0;
    }
    public boolean isNoti ( ){
        return this.NOTI != 0;
    }

}
