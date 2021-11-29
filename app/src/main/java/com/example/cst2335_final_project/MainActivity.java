package com.example.cst2335_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editDate;
    Button submit;
    final Calendar myCalendar = Calendar.getInstance();

    Button tempButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //temporary button to go to favourites -> drawer will take to favourites later
        tempButton = (Button) findViewById(R.id.tempButton);
        tempButton.setOnClickListener(click -> {
            Intent favIntent = new Intent(this, favouritesActivity.class);
            startActivity(favIntent);
        });

        //calendar picker done on edit text
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        //on click edit text bring DatePickerDialog
        editDate = (EditText) findViewById(R.id.pickDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //submit button takes to activity_image_generated.xml
        submit = (Button) findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //go to activity_generate.xml with date data from editText
                //TODO [Submit] test if mainactivity.this works or just .this
                Intent imageIntent = new Intent(MainActivity.this, ActivityImageGen.class);
                startActivity(imageIntent);
            }
        });
    }

    //update label method
    private void updateLabel(){
        String dateFormat = "mm/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.CANADA);

        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
    //favourites activity
    public void openFavouritesActivity(){
        //TODO [openFavActivity] test if mainactivity.this works or just .this
        Intent favIntent = new Intent(MainActivity.this, favouritesActivity.class);
        startActivity(favIntent);
    }

}