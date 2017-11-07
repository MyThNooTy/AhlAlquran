package com.samcotec.ahlalquran;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import static com.samcotec.ahlalquran.LoadPage.SETTING;

public class CreateGroup extends AppCompatActivity {

    static Spinner gSpinnerReadType, gSpinnerFrom, gSpinnerTo;
    static TextView textViewFrom, textViewTo, gGroupName;
    static Button gDateStart,gDateEnd,gCreate;
    static CheckedTextView gSatCheck,gSunCheck,gMonCheck,gTueCheck,gWedCheck,gThuCheck,gFriCheck;
    static CheckBox checkBox;

    static ArrayAdapter<CharSequence> spinneradapter;
    static ArrayAdapter<CharSequence> fromspinneradapter;
    ArrayAdapter<CharSequence> tospinneradapter;

    GroupsAdapter group = null;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_create_group);

        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        // New codes

        gGroupName = (TextView) findViewById(R.id.gInputName);

        String gInputSDate = year + "-" + month + "-" + day;
        String gInputEDate = year + "-" + month + "-" + day;
        String gInputName = null;

        checkBox = (CheckBox) findViewById(R.id.checkBox);


        gSpinnerReadType = (Spinner) findViewById(R.id.gSpinnerReadType);
        gSpinnerFrom = (Spinner) findViewById(R.id.gSpinnerFrom);
        gSpinnerTo = (Spinner) findViewById(R.id.gSpinnerTo);
        textViewFrom = (TextView) findViewById(R.id.textViewFrom);
        textViewTo = (TextView) findViewById(R.id.textViewTo);
        gDateStart = (Button) findViewById(R.id.gDateStart);
        gDateEnd = (Button) findViewById(R.id.gDateEnd);
        gCreate = (Button) findViewById(R.id.gCreate);
        gSatCheck = (CheckedTextView) findViewById(R.id.gSatCheck);
        gSunCheck = (CheckedTextView) findViewById(R.id.gSunCheck);
        gMonCheck = (CheckedTextView) findViewById(R.id.gMonCheck);
        gTueCheck = (CheckedTextView) findViewById(R.id.gTueCheck);
        gWedCheck = (CheckedTextView) findViewById(R.id.gWedCheck);
        gThuCheck = (CheckedTextView) findViewById(R.id.gThuCheck);
        gFriCheck = (CheckedTextView) findViewById(R.id.gFriCheck);

        gDateStart.setText( gInputSDate );
        gDateEnd.setText( gInputEDate );


        textViewFrom.setEnabled( false );
        textViewTo.setEnabled( false );
        gSpinnerFrom.setEnabled( false );
        gSpinnerTo.setEnabled( false );

        spinneradapter = ArrayAdapter.createFromResource(this,R.array.group_type,android.R.layout.simple_spinner_item);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gSpinnerReadType.setAdapter(spinneradapter);
        gSpinnerReadType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if(parent.getItemIdAtPosition(position) == 0)
                {
                    textViewFrom.setEnabled(false);
                    textViewTo.setEnabled(false);
                    gSpinnerFrom.setEnabled(false);
                    gSpinnerTo.setEnabled(false);
                }
                else if(parent.getItemIdAtPosition(position) == 1){
                    textViewFrom.setEnabled(true);
                    textViewTo.setEnabled(true);
                    gSpinnerFrom.setEnabled(true);
                    gSpinnerTo.setEnabled(true);
                    fromspinneradapter = ArrayAdapter.createFromResource(CreateGroup.this,R.array.quran_parts,android.R.layout.simple_spinner_item);
                    fromspinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gSpinnerFrom.setAdapter(fromspinneradapter);

                    tospinneradapter = ArrayAdapter.createFromResource(CreateGroup.this,R.array.quran_parts,android.R.layout.simple_spinner_item);
                    tospinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gSpinnerTo.setAdapter(tospinneradapter);

                }
                else if(parent.getItemIdAtPosition(position) == 2){
                    textViewFrom.setEnabled(true);
                    textViewTo.setEnabled(true);
                    gSpinnerFrom.setEnabled(true);
                    gSpinnerTo.setEnabled(true);
                    fromspinneradapter = ArrayAdapter.createFromResource(CreateGroup.this,R.array.quran_sorah,android.R.layout.simple_spinner_item);
                    fromspinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gSpinnerFrom.setAdapter(fromspinneradapter);

                    tospinneradapter = ArrayAdapter.createFromResource(CreateGroup.this,R.array.quran_sorah,android.R.layout.simple_spinner_item);
                    tospinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gSpinnerTo.setAdapter(tospinneradapter);
                }
                else if(parent.getItemIdAtPosition(position) == 3){
                    textViewFrom.setEnabled(true);
                    textViewTo.setEnabled(true);
                    gSpinnerFrom.setEnabled(true);
                    gSpinnerTo.setEnabled(true);
                    fromspinneradapter = ArrayAdapter.createFromResource(CreateGroup.this,R.array.quran_pages,android.R.layout.simple_spinner_item);
                    fromspinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gSpinnerFrom.setAdapter(fromspinneradapter);

                    tospinneradapter = ArrayAdapter.createFromResource(CreateGroup.this,R.array.quran_pages,android.R.layout.simple_spinner_item);
                    tospinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gSpinnerTo.setAdapter(tospinneradapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datepickers = new DatePickerDialog(CreateGroup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yyear, int mmonth, int dday) {
                        gDateStart.setText(yyear+"-"+mmonth+"-"+dday);
                    }
                },year,month,day);
                datepickers.setTitle("Select Date Start");
                datepickers.show();
            }
        });

        gDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datepickers = new DatePickerDialog(CreateGroup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yyear, int mmonth, int dday) {
                        gDateEnd.setText(yyear+"-"+mmonth+"-"+dday);
                    }
                },year,month,day);
                datepickers.setTitle("Select Date End");
                datepickers.show();
            }
        });

        gSatCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gSatCheck.isChecked()){
                    gSatCheck.setChecked(true);
                    gSatCheck.setCheckMarkDrawable(R.drawable.ic_selected);
                }
                else
                {
                    gSatCheck.setChecked(false);
                    gSatCheck.setCheckMarkDrawable(R.drawable.ic_no_select);
                }
            }
        });
        gSunCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gSunCheck.isChecked()){
                    gSunCheck.setChecked(true);
                    gSunCheck.setCheckMarkDrawable(R.drawable.ic_selected);
                }
                else
                {
                    gSunCheck.setChecked(false);
                    gSunCheck.setCheckMarkDrawable(R.drawable.ic_no_select);
                }
            }
        });
        gMonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gMonCheck.isChecked()){
                    gMonCheck.setChecked(true);
                    gMonCheck.setCheckMarkDrawable(R.drawable.ic_selected);
                }
                else
                {
                    gMonCheck.setChecked(false);
                    gMonCheck.setCheckMarkDrawable(R.drawable.ic_no_select);
                }
            }
        });
        gTueCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gTueCheck.isChecked()){
                    gTueCheck.setChecked(true);
                    gTueCheck.setCheckMarkDrawable(R.drawable.ic_selected);
                }
                else
                {
                    gTueCheck.setChecked(false);
                    gTueCheck.setCheckMarkDrawable(R.drawable.ic_no_select);
                }
            }
        });
        gWedCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gWedCheck.isChecked()){
                    gWedCheck.setChecked(true);
                    gWedCheck.setCheckMarkDrawable(R.drawable.ic_selected);
                }
                else
                {
                    gWedCheck.setChecked(false);
                    gWedCheck.setCheckMarkDrawable(R.drawable.ic_no_select);
                }
            }
        });
        gThuCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gThuCheck.isChecked()){
                    gThuCheck.setChecked(true);
                    gThuCheck.setCheckMarkDrawable(R.drawable.ic_selected);
                }
                else
                {
                    gThuCheck.setChecked(false);
                    gThuCheck.setCheckMarkDrawable(R.drawable.ic_no_select);
                }
            }
        });
        gFriCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!gFriCheck.isChecked()){
                    gFriCheck.setChecked(true);
                    gFriCheck.setCheckMarkDrawable(R.drawable.ic_selected);
                }
                else
                {
                    gFriCheck.setChecked(false);
                    gFriCheck.setCheckMarkDrawable(R.drawable.ic_no_select);
                }
            }
        });

        gCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( view == findViewById(R.id.gCreate))   {
                    String days = "";
                    int ageType =  Integer.parseInt(String.valueOf(gSpinnerReadType.getSelectedItemPosition())) > 3 ? 1: 2 ;
                    days += (gSatCheck.isChecked() ? (days.isEmpty() ? "":",")  + "1": "") ;
                    days += (gSunCheck.isChecked() ? (days.isEmpty() ? "":",") + "2": "") ;
                    days += (gMonCheck.isChecked() ? (days.isEmpty() ? "":",") + "3": "") ;
                    days += (gThuCheck.isChecked() ? (days.isEmpty() ? "":",") + "4": "") ;
                    days += (gWedCheck.isChecked() ? (days.isEmpty() ? "":",") + "5": "") ;
                    days += (gThuCheck.isChecked() ? (days.isEmpty() ? "":",") + "6": "") ;
                    days += (gFriCheck.isChecked() ? (days.isEmpty() ? "":",") + "7": "") ;

                group = new GroupsAdapter(
                gGroupName.getText().toString(),
                gDateStart.getText().toString(),
                gDateEnd.getText().toString(),
                days,
                ( SETTING.getAPIID() != 0 ? SETTING.getAPIID() : (int) SETTING.getID() ),
                ( checkBox.isChecked() ? 1 : 2 ),
                Integer.parseInt( String.valueOf( gSpinnerReadType.getSelectedItemPosition() ) ),
                Integer.parseInt( String.valueOf( gSpinnerFrom.getSelectedItemPosition() ) ),
                Integer.parseInt( String.valueOf( gSpinnerTo.getSelectedItemPosition() ) ),
                ageType
                );

                    if( group.doInSql() )  {
                        QueApi.create( group.getTable(), group.getData().toString() );
                        tools.alert( CreateGroup.this,"" ,"تم إنشاء المجموعة");
                        Intent gSettings = new Intent( App.getApp(), Home.class );
                        startActivity(gSettings);
                        finish();
                    }
                }
            }
        });

    }
}
