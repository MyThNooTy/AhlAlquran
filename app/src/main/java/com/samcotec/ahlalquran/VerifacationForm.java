package com.samcotec.ahlalquran;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.samcotec.ahlalquran.LoadPage.SETTING;

public class VerifacationForm extends AppCompatActivity {

    private String vCode = null ;
     JSONObject userData = new JSONObject();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT );
        setContentView( R.layout.activity_verifacation_form );

        Bundle b = getIntent().getExtras();
        vCode = b.getString( "code" );
        try{
            userData = new JSONObject( b.getString( "user" ) );
        }
        catch (JSONException e ){
            Log.e(this.toString(), e.getMessage(), e);
        }



        SETTING.setMobile( b.getString( "number" ) ) ;
        SETTING.setAPIID( b.getInt( "APIID" ) );

        Button VerifyMe = (Button) findViewById( R.id.sButtonActiveMe );

        VerifyMe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText vXCode = (EditText)findViewById( R.id.sVerifyMe );

                if ( Objects.equals( vXCode.getText().toString(), vCode ) ){
                    Intent newActivity = new Intent( App.getApp() , Settings.class );


                    SETTING.setActive( 1 );
                    if( userData.length() > 0 ){
                        try {
                            SETTING.setNoti( Objects.equals( userData.getString( "noti" ), "1" ) );
                            SETTING.setsSEX(Integer.parseInt( userData.getString("sex") ) );
                            SETTING.setBirthday( userData.getString("b_date") ) ;
                            SETTING.setEmail( userData.getString("email") ); ;
                            SETTING.setFullName( userData.getString("name") ); ;
                            SETTING.setLang( userData.getString("lang").toLowerCase() );
                        }
                        catch (JSONException e ){
                            Log.e(this.toString(), e.getMessage(),e);
                        }

                    }

                    if( SETTING.doInSql( ) ){
                        QueApi.create( SETTING.getTable(), SETTING.getData().toString() );
                    }

                    startActivity( newActivity );
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder( VerifacationForm.this  );
                    String errortitle = getResources().getString( R.string.error_msg );
                    String errormsg = getResources().getString( R.string.ERROR_CODE_MESSEG );
                    String yesbtn = getResources().getString(R.string.yes);

                    builder.setTitle(errortitle);
                    builder.setMessage(errormsg);
                    builder.setPositiveButton(yesbtn,null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }



        });


    }
}
