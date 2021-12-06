package com.example.cst2335_final_project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String TAG = this.getClass().getSimpleName();
    Button loginBtn;
    TextView whatActivityText;
    EditText emailString;
    String sharePrefName = "MySharedPref";
    // toolbar+nav
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar+nav
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

        emailString = findViewById(R.id.EditText_Main_Email_Input);
        loginBtn = findViewById(R.id.Button_Main_Login);


        SharedPreferences prefs = getSharedPreferences(sharePrefName,MODE_PRIVATE);
        String s1 = prefs.getString("emailString", "");
        emailString.setText(s1);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivitySelection.class);
                MainActivity.this.startActivity(intent);
                Toast.makeText(MainActivity.this, getString(R.string.Login_Success_Message), Toast.LENGTH_SHORT).show();
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
                Intent goToMainPage = new Intent(MainActivity.this,ActivitySelection.class);
                MainActivity.this.startActivity(goToMainPage);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Help:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.Alert_Title_Help_Login))
                        .setMessage(getString(R.string.Alert_Message_Help_Login))
                        .setNeutralButton(getString(R.string.Alert_Neutral_Button), null)
                        .create();
                dialog.show();
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Help_alert,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Favourites:
                Intent goToFavs = new Intent(MainActivity.this,ActivityFavourites.class);
                MainActivity.this.startActivity(goToFavs);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Random_Generator:
                Intent goToRandomGen = new Intent(MainActivity.this,ActivityRandomGen.class);
                MainActivity.this.startActivity(goToRandomGen);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Search_By_Date:
                Intent goToImageGen = new Intent(MainActivity.this,ActivityImageGen.class);
                MainActivity.this.startActivity(goToImageGen);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Logout:
                Toast.makeText(getApplicationContext(),R.string.Show_Message_is_Logged_Out_Activity,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Nav_Main:
                Intent goToMainPage = new Intent(MainActivity.this,ActivitySelection.class);
                MainActivity.this.startActivity(goToMainPage);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Fav_List:
                Intent goToFavs = new Intent(MainActivity.this,ActivityFavourites.class);
                MainActivity.this.startActivity(goToFavs);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Random_Image:
                Intent goToRandomGen = new Intent(MainActivity.this,ActivityRandomGen.class);
                MainActivity.this.startActivity(goToRandomGen);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Image_Date:
                Intent goToImageGen = new Intent(MainActivity.this,ActivityImageGen.class);
                MainActivity.this.startActivity(goToImageGen);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Logout:
                Toast.makeText(getApplicationContext(),R.string.Show_Message_is_Logged_Out_Activity,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EditText emailString = findViewById(R.id.EditText_Main_Email_Input);
        String sharePrefName = "MySharedPref";
        SharedPreferences prefs = MainActivity.this.getSharedPreferences(sharePrefName, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        myEdit.putString("emailString", emailString.getText().toString());
        myEdit.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        loginBtn = findViewById(R.id.Button_Main_Login);
        emailString = findViewById(R.id.EditText_Main_Email_Input);
        String sharePrefName = "MySharedPref";
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = MainActivity.this.getSharedPreferences(sharePrefName, MODE_PRIVATE);
                SharedPreferences.Editor myEdit = prefs.edit();
                myEdit.putString("emailString", emailString.getText().toString());
                myEdit.commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginBtn = findViewById(R.id.Button_Main_Login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivitySelection.class);
                MainActivity.this.startActivity(intent);
                Toast.makeText(MainActivity.this, getString(R.string.Login_Success_Message), Toast.LENGTH_SHORT).show();
            }
        });

    }

}