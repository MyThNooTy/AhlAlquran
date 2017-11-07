package com.samcotec.ahlalquran;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.TimeZone;

public class LoadPage extends AppCompatActivity {
    public static setting SETTING;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @RequiresApi( api = Build.VERSION_CODES.N )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_load_page);

        TimeZone.setDefault( TimeZone.getTimeZone("Asia/Riyadh") );

        dbClass.setDb( new dbClass(  ) );
        SETTING  = new setting( 1 );


        try{
            Intent newActivity;
            if( SETTING.isActive() ){
                newActivity = new Intent( LoadPage.this, Home.class );
                startActivity(newActivity);
                newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.finish();
            }
            else{
                if( AJAX.isConnected( LoadPage.this ) ){
                    newActivity = new Intent( LoadPage.this, Register.class );
                    newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity( newActivity );
                    this.finish();
                }
                else{
                    tools.alert( LoadPage.this ,"Error Network", "No internet" );
                }
            }
        }
        catch (Exception e) {
            Log.e( "LoadPage", e.getMessage(), e);
        }
    }
}
