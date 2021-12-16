package com.example.cst2335_final_project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivitySelection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = this.getClass().getSimpleName();
    EditText editDate;
    TextView whatActivityText;
    Button goImgGensubmit, goFavButton, goRandomButton;
    // toolbar+nav
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Calendar myCalendar = Calendar.getInstance();
    String The_yyyy_mm_dd_Date;
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
        View headerView = navigationView.getHeaderView(0);
        whatActivityText = headerView.findViewById(R.id.TextView_Header_Editable);
        whatActivityText.setText(TAG);
        navigationView.setNavigationItemSelectedListener(this);


        //temporary button to go to favourites -> drawer will take to favourites later
        goFavButton = findViewById(R.id.Button_Selection_Fav_List);
        goFavButton.setOnClickListener(click -> {
            Intent favIntent = new Intent(this, ActivityFavourites.class);
            startActivity(favIntent);
        });

        // button to start random generator for image
        goRandomButton = findViewById(R.id.Button_Selection_Random_Generator);
        goRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToRandomGenerator = new Intent(ActivitySelection.this,ActivityRandomGen.class);
                ActivitySelection.this.startActivity(goToRandomGenerator);
            }
        });


        //calendar picker done on edit text
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                The_yyyy_mm_dd_Date = year+"-"+(month+1)+"-"+day;
                editDate.setText(The_yyyy_mm_dd_Date);
            }
        };
        //on click edit text bring DatePickerDialog
        editDate = findViewById(R.id.EditText_Selection_Date_Picker);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActivitySelection.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //submit button takes to activity_image_generated.xml
        goImgGensubmit = findViewById(R.id.Button_Selection_Submit_Date);
        goImgGensubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!editDate.getText().toString().matches("")){
                    //go to activity_generate.xml with date data from editText
                    Intent imageIntent = new Intent(ActivitySelection.this, ActivityImageGen.class);
                    imageIntent.putExtra("the-date",The_yyyy_mm_dd_Date);
                    editDate.setText("");
                    ActivitySelection.this.startActivity(imageIntent);
                } else {
                    Toast.makeText(ActivitySelection.this, getString(R.string.Show_Message_No_Date_Selected), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

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
//                Intent goToMainPage = new Intent(ActivitySelection.this,ActivitySelection.class);
//                ActivitySelection.this.startActivity(goToMainPage);
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_is_Selection_Activity, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Help:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.Alert_Title_Help_AS))
                        .setMessage(getString(R.string.Alert_Message_Help_AS))
                        .setNeutralButton(getString(R.string.Alert_Neutral_Button), null)
                        .create();
                dialog.show();
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Help_alert,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Favourites:
                Intent goToFavPage = new Intent(ActivitySelection.this,ActivityFavourites.class);
                ActivitySelection.this.startActivity(goToFavPage);
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Random_Generator:
                Intent goToRandomPage = new Intent(ActivitySelection.this,ActivityRandomGen.class);
                ActivitySelection.this.startActivity(goToRandomPage);
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Search_By_Date:
                Intent goToImgGen = new Intent(ActivitySelection.this,ActivityImageGen.class);
                ActivitySelection.this.startActivity(goToImgGen);
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Logout:
                Intent goToLogout = new Intent(ActivitySelection.this,MainActivity.class);
                ActivitySelection.this.startActivity(goToLogout);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Nav_Main:
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_is_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Fav_List:
                Intent goToFavPage = new Intent(ActivitySelection.this,ActivityFavourites.class);
                ActivitySelection.this.startActivity(goToFavPage);
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Random_Image:
                Intent goToRandomPage = new Intent(ActivitySelection.this,ActivityRandomGen.class);
                ActivitySelection.this.startActivity(goToRandomPage);
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Image_Date:
                Intent goToImgGen = new Intent(ActivitySelection.this,ActivityImageGen.class);
                ActivitySelection.this.startActivity(goToImgGen);
                Toast.makeText(ActivitySelection.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Logout:
                Intent goToLogout = new Intent(ActivitySelection.this,MainActivity.class);
                ActivitySelection.this.startActivity(goToLogout);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }
}