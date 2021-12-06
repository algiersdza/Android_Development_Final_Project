package com.example.cst2335_final_project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ActivityRandomGen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String TAG = this.getClass().getSimpleName();
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView whatActivityText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_gen);


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
                Intent goToMainPage = new Intent(ActivityRandomGen.this,ActivitySelection.class);
                ActivityRandomGen.this.startActivity(goToMainPage);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Help:

                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Help_alert,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Favourites:
                Intent goToFavPage = new Intent(ActivityRandomGen.this,ActivityFavourites.class);
                ActivityRandomGen.this.startActivity(goToFavPage);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Random_Generator:
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_is_Random_Activity,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Search_By_Date:
                Intent goToImgGen = new Intent(ActivityRandomGen.this,ActivityImageGen.class);
                ActivityRandomGen.this.startActivity(goToImgGen);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Logout:
                Intent goToLogout = new Intent(ActivityRandomGen.this,MainActivity.class);
                ActivityRandomGen.this.startActivity(goToLogout);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Nav_Main:
                Intent goToMainPage = new Intent(ActivityRandomGen.this,ActivitySelection.class);
                ActivityRandomGen.this.startActivity(goToMainPage);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Fav_List:
                Intent goToFavPage = new Intent(ActivityRandomGen.this,ActivityFavourites.class);
                ActivityRandomGen.this.startActivity(goToFavPage);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Random_Image:
//                Intent goToRandomPage = new Intent(ActivityRandomGen.this,ActivityRandomGen.class);
//                ActivityRandomGen.this.startActivity(goToRandomPage);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_is_Random_Activity,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Image_Date:
                Intent goToImgGen = new Intent(ActivityRandomGen.this,ActivityImageGen.class);
                ActivityRandomGen.this.startActivity(goToImgGen);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Logout:
                Intent goToLogout = new Intent(ActivityRandomGen.this,MainActivity.class);
                ActivityRandomGen.this.startActivity(goToLogout);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

}