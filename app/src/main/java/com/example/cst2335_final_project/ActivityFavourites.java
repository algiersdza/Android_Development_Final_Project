package com.example.cst2335_final_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    ListView listView;
    private static List<FavourtiesList> favsList = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;




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
        listView = findViewById(R.id.listView1);
        MyListAdapter myListAdapter = new MyListAdapter();
        listView.setAdapter(myListAdapter);
        sqLiteDatabase = new MyOpener(this).getWritableDatabase();

        //load from db
        favsList = loadDataFromDatabase(sqLiteDatabase);
        if (!favsList.isEmpty()){
            myListAdapter.notifyDataSetChanged();
        }

    }

    /**
     *
     * @param menu
     * @return navbar
     */
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

    /**
     *
     * @param db
     * @return
     */

    private List<FavourtiesList> loadDataFromDatabase(SQLiteDatabase db) {
        Cursor c = db.query(MyOpener.TABLE_NAME, null, null, null, null, null, null);
        List<FavourtiesList> results = new ArrayList<>();

        while (c.moveToNext()) {
            results.add(new MyListAdapter(c.getLong(c.getColumnIndex(MyOpener.COL_ID)), c.getString(c.getColumnIndex(MyOpener.COL_DATE)), c.getBlob(c.getColumnIndex(MyOpener.COL_IMAGE)) == 1));
        }

        return results;
    }


    /**
     * Adapter
     */
    private class MyListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return favsList.size();
        }

        @Override
        public Object getItem(int position) {
            return favsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            FavourtiesList msg = favsList.get(position);
            View view = inflater.inflate(R.layout.list_row,parent,false);
            //((TextView) view.findViewById(R.id.Text_List_lab4)).setText(msg.text);
            return view;
        }
    }

}
