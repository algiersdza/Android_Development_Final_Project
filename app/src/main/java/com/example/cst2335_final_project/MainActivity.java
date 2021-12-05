package com.example.cst2335_final_project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText editDate;
    Button submit, tempButton;
    Toolbar toolbar;
    final Calendar myCalendar = Calendar.getInstance();
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //loads toolbar -> also calls onCreateOptionsMenu if we need one l8er
        //nav drawer
        DrawerLayout drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggleDrawer = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggleDrawer);
        toggleDrawer.syncState();
        //Navigation View -> from activity_toolbar > navbar contents
        NavigationView navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);

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
                Intent imageIntent = new Intent(MainActivity.this, ActivityImageGen.class);
                startActivity(imageIntent);
            }
        });
        //log
        Log.e(ACTIVITY_NAME, "*****Inside OnCreate()*****");
    }
    //TODO toolbar options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu?
        return super.onCreateOptionsMenu(menu);
    }

    //update label method
    private void updateLabel(){
        String dateFormat = "mm/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.CANADA);

        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    //Milestone 2 -> toolbar
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /*
        //switch case -> action on item selected
        switch(item.getItemId()){
            case R.id.navHome: //TODO : is there a way to just close the drawer ?
                Toast.makeText(this, "Already on Home page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navFav: //open favouritesActivity
                Intent favIntent = new Intent(this, favouritesActivity.class);
                startActivity(favIntent);
                break;
        }

         */

        return true;
    }
}