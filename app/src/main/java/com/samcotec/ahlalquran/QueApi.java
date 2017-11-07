package com.samcotec.ahlalquran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.samcotec.ahlalquran.LoadPage.SETTING;

/**
 * Created by MyTh on 12/10/2017.
 */

public class QueApi {
    private static String TABLE  = "que";
     QueApi(){}

    public static JSONArray getData(){
        JSONArray data = dbClass.getDb().getData( TABLE, "");
        return data;
    }

    public static boolean create(String table, String vals ){
        JSONObject data = new JSONObject();
        long result = 0 ;
        try {
            data.put( "tablename", table );
            data.put( "vals", vals );
            if( Objects.equals( table, SETTING.getTable() ) ) {
                JSONArray array = dbClass.getDb().getData(TABLE," tablename = '" + SETTING.getTable()+"'" );
//                Log.d("defff",String.valueOf(array.length()));
                if( array.length() != 0 )
                    result = dbClass.getDb().upd( TABLE, data, " tablename = ? ", new String[]{SETTING.getTable()});
                else
                    result = dbClass.getDb().add( TABLE, data);

            }
            else {
                result = dbClass.getDb().add( TABLE, data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  result != 0;
    }
}
