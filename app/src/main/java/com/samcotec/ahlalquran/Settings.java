package com.samcotec.ahlalquran;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.samcotec.ahlalquran.LoadPage.SETTING;

public class Settings extends AppCompatActivity {

    static EditText sInputFullName, sInputEmail, sInputMobile;
    static TextView sInputBirthday;
    static DialogFragment f;
    RadioButton sRadioMale, sRadioFemale;
    CheckBox sCheckNoti;
    Button sButtonSave;

//    private static final String TAG = "Settings";

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_settings);

        //--- change SEX checked

        sRadioMale = (RadioButton) findViewById(R.id.sRadioMale);
        sRadioFemale = (RadioButton) findViewById(R.id.sRadioFemale);
        sCheckNoti = (CheckBox) findViewById(R.id.sCheckNoti);
        sInputBirthday = (TextView) findViewById(R.id.sInputBirthday);

        sInputMobile = (EditText)findViewById(R.id.sInputMobile);
        sInputFullName = (EditText)findViewById(R.id.sInputFullName);
        sInputEmail = (EditText)findViewById(R.id.sInputEmail);


        sButtonSave = (Button) findViewById(R.id.sButtonSave);

        sRadioMale.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sRadioFemale.setChecked(false);
            }
        });

        sRadioFemale.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sRadioMale.setChecked(false);
            }
        });
        f = new DatePickerFragment ( );
        f.setStyle(R.style.Theme_AppCompat,R.style.Theme_AppCompat);

        sInputBirthday.setOnClickListener(new View.OnClickListener(){



//            @Override
            public void onClick(View view) {
//                DialogFragment f = new DatePickerFragment ( );
                f.show( getFragmentManager(), "Picker");
//
//                Calendar cal ;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    cal = Calendar.getInstance();
//                    int year = cal.get(Calendar.YEAR);
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog dialog = new DatePickerDialog(
//                            Settings.this,
//                            android.R.style.Theme_NoTitleBar_Fullscreen,
//                            mDateSetListener,
//                            year,month,day);
////
////                      if( dialog.getWindow() != null ) {
////                          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
////                      }
//
//                    dialog.show();
//                }
            }
        });


//        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
//
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day){
//                month = month + 1;
//                String date = day + "-" + month + "-" + year;
//                sInputBirthday.setText(date);
//            }
//        };
//

        sInputMobile.setText( String.valueOf(SETTING.getMobile()) );
        sInputFullName.setText(SETTING.getFullName());
        sInputBirthday.setText(SETTING.getBirthday());
        sInputEmail.setText(SETTING.getEmail());
        sRadioMale.setChecked( SETTING.getsSEX() == 0 );
        sRadioFemale.setChecked( SETTING.getsSEX() == 1 );
        sCheckNoti.setChecked( SETTING.isNoti() );


//        out.println( SETTING.getBirthday());

        sButtonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                SETTING.setFullName(String.valueOf(sInputFullName.getText()));
                SETTING.setBirthday(String.valueOf(sInputBirthday.getText()));
                SETTING.setEmail(String.valueOf(sInputEmail.getText()));
                SETTING.setsSEX( sRadioMale.isChecked() && !sRadioFemale.isChecked() ? 0 : 1 );
                SETTING.setNoti( sCheckNoti.isChecked()  );
                if( SETTING.doInSql() ){
                    QueApi.create( SETTING.getTable(), SETTING.getData().toString() );
                    tools.alert( Settings.this, "",getResources().getString( R.string.profile_updated ) );
//                    out.println( SETTING.getData() );
                    Intent homePage = new Intent( Settings.this ,Home.class);
                    startActivity ( homePage );
//                    finish();
                }

            }
        });

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month = month + 1;
            String date = day + "-" + month + "-" + year;
            sInputBirthday.setText(date);

        }
    }

}
