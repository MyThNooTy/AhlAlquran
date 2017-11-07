package com.samcotec.ahlalquran;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;

import static java.lang.System.out;

public class Register extends AppCompatActivity {

    private Class LoginForm, VERIFACATIONFORM;

    Button sendNumber;
    private static String mynumber;
    private static int vCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_register);


        final Register LoginFrom = this;
        final Class<VerifacationForm> VERIFACATIONFORM = VerifacationForm.class;
        sendNumber = (Button) findViewById(R.id.sButtonSendMobile);

//        cd = new ConnectionDetector(this);

        sendNumber.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                EditText m = (EditText)findViewById( R.id.sMobileNumber );

                String mn = m.getText().toString();

                Random randCode = new Random();
                int
                        Int1 = (randCode.nextInt(100) + 1 ),
                        Int2 = (randCode.nextInt(200) + 3 ),
                        Int3 = (randCode.nextInt(305) + 4 ),
                        Int4 = (randCode.nextInt(500) + 2 );

                vCode += Integer.parseInt(String.valueOf(Int1)) ;
                vCode += Integer.parseInt(String.valueOf(Int2)) ;
                vCode += Integer.parseInt(String.valueOf(Int3)) ;
                vCode += Integer.parseInt(String.valueOf(Int4)) ;

                if( !TextUtils.isEmpty(mn) && mn.length() > 9 ){
                    mynumber = mn;

                    AJAX http = new AJAX(LoginFrom, "192.168.1.5/app/api/?sendmsg&t=l,s", true, new AJAX.CallBack() {
                        @Override
                        public void ajax(String a) {
                            JSONObject
                                    jsonR = null,
                                    r = null,
                                    user = null,
                                    sms = null;
                            try{
                                jsonR = new JSONObject( a );
                                r = jsonR.getJSONObject("client");
                                if(r != null ){
                                    sms = r.getJSONObject("sms");
                                    user = r.getJSONObject("user");
                                }


                                if( sms != null && user != null ){
                                    out.println( user );
                                    if( Objects.equals( sms.getString( "RES" ), "1" ) ) {

                                        Intent newActivity = new Intent( LoginFrom , VERIFACATIONFORM );
                                        Bundle b = new Bundle( );

                                        b.putString( "number" , user.getString( "mobile" ) );
                                        b.putString( "code" , sms.getString( "code" ) );
                                        b.putInt( "APIID" , user.getInt( "ID" ));
                                        b.putString( "user" , user.toString() );

                                        newActivity.putExtras( b );
                                        startActivity( newActivity );
                                        finish();
                                    }
                                    else {}
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                    http.user =  mynumber ;
                    http.execute();

                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder( LoginFrom );
                    String errortitle = getResources().getString(R.string.error_msg);
                    String errormsg = getResources().getString(R.string.no_phone_number);
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
