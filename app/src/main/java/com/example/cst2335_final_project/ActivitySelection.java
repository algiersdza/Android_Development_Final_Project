package com.example.cst2335_final_project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ActivitySelection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText editDate;
    Button submit, tempButton;
    // toolbar+nav
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    final Calendar myCalendar = Calendar.getInstance();
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);


        toolbar = findViewById(R.id.ToolBar_ID);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Show_Drawer_Open,R.string.Show_Drawer_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.Nav_View);
        navigationView.setNavigationItemSelectedListener(this);

//        //toolbar
//        toolbar = (Toolbar) findViewById(R.id.toolBar_ID);
//        setSupportActionBar(toolbar); //loads toolbar -> also calls onCreateOptionsMenu if we need one l8er
//        //nav drawer
//        DrawerLayout drawer = findViewById(R.id.DrawerLayout);
//        ActionBarDrawerToggle toggleDrawer = new ActionBarDrawerToggle(this, drawer,
//                toolbar, R.string.open, R.string.close);
//        drawer.addDrawerListener(toggleDrawer);
//        toggleDrawer.syncState();
//        //Navigation View -> from activity_toolbar > navbar contents
//        NavigationView navView = findViewById(R.id.navView);
//        navView.setNavigationItemSelectedListener(this);

        //temporary button to go to favourites -> drawer will take to favourites later
        tempButton = (Button) findViewById(R.id.Button_Selection_Fav_List);
        tempButton.setOnClickListener(click -> {
            Intent favIntent = new Intent(this, ActivityFavourites.class);
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
        editDate = (EditText) findViewById(R.id.EditText_Selection_Date_Picker);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActivitySelection.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //submit button takes to activity_image_generated.xml
        submit = (Button) findViewById(R.id.Button_Selection_Submit_Date);
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //go to activity_generate.xml with date data from editText
                Intent imageIntent = new Intent(ActivitySelection.this, ActivityImageGen.class);
                startActivity(imageIntent);
            }
        });
        //log
        Log.e(ACTIVITY_NAME, "*****Inside OnCreate()*****");
    }

    // ibra starts here

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Toolbar_Main:

                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Main_Activity, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Help:

                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Help_alert,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Favourites:

                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Random_Generator:

                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Search_By_Date:

                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Logout:

                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Nav_Main:
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Main_Activity, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Fav_List:
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Random_Image:
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Image_Date:
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Logout:

                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }



    //update label method
    private void updateLabel(){
        String dateFormat = "mm/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.CANADA);

        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}