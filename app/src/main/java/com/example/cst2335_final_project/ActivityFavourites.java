package com.example.cst2335_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ActivityFavourites extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String TAG = this.getClass().getSimpleName();
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView whatActivityText;
    private static List<FavourtiesList> favsList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

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

        //Favourites ListView
        ListView favListView;
        favListView = (ListView)findViewById(R.id.listView1);

        //set the adapter to populate listview
        MyListAdapter myListAdapter = new MyListAdapter();


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
                Intent goToMainPage = new Intent(ActivityFavourites.this,ActivitySelection.class);
                ActivityFavourites.this.startActivity(goToMainPage);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Help:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.Alert_Title_Help_AF))
                        .setMessage(getString(R.string.Alert_Message_Help_AF))
                        .setNeutralButton(getString(R.string.Alert_Neutral_Button), null)
                        .create();
                dialog.show();
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Help_alert,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Favourites:
//                Intent goToFavPage = new Intent(ActivityImageGen.this,ActivityFavourites.class);
//                ActivityImageGen.this.startActivity(goToFavPage);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_is_Favorites_Activity, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Random_Generator:
                Intent goToRandomPage = new Intent(ActivityFavourites.this,ActivityRandomGen.class);
                ActivityFavourites.this.startActivity(goToRandomPage);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Search_By_Date:
                Intent goToImgGen = new Intent(ActivityFavourites.this,ActivityImageGen.class);
                ActivityFavourites.this.startActivity(goToImgGen);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Logout:
                Intent goToLogout = new Intent(ActivityFavourites.this,MainActivity.class);
                ActivityFavourites.this.startActivity(goToLogout);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Nav_Main:
                Intent goToMainPage = new Intent(ActivityFavourites.this,ActivitySelection.class);
                ActivityFavourites.this.startActivity(goToMainPage);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Fav_List:
//                Intent goToFavPage = new Intent(ActivityFavourites.this,ActivityFavourites.class);
//                ActivityFavourites.this.startActivity(goToFavPage);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_is_Favorites_Activity, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Random_Image:
                Intent goToRandomPage = new Intent(ActivityFavourites.this,ActivityRandomGen.class);
                ActivityFavourites.this.startActivity(goToRandomPage);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Image_Date:
                Intent goToImgGen = new Intent(ActivityFavourites.this,ActivityImageGen.class);
                ActivityFavourites.this.startActivity(goToImgGen);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Logout:
                Intent goToLogout = new Intent(ActivityFavourites.this,MainActivity.class);
                ActivityFavourites.this.startActivity(goToLogout);
                Toast.makeText(ActivityFavourites.this,R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

    //TODO favourites list
    private class FavourtiesList {
        private final String text;

        private FavourtiesList(String text) {
            this.text = text;
        }
    }

    private class MyListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

}
