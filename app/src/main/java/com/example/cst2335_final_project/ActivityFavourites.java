package com.example.cst2335_final_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

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
    private static List<FavouritesList> favsList = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;
    public static final String ITEM_ID = "ID";
    public static final String TEXT_TITLE ="TITLE";
    public static final String TEXT_DATE ="DATE";
    public static final String BYTE_IMAGE ="IMAGE";
    public static final String BITMAP_IMAGE ="BIT_IMAGE";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        toolbar = findViewById(R.id.ToolBar_ID);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Show_Drawer_Open, R.string.Show_Drawer_Close);
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
        if (!favsList.isEmpty()) {
            myListAdapter.notifyDataSetChanged();
        }

        // should start alertdailog on looooooong press
        listView.setOnItemLongClickListener((p, b, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.Prompt_Delete))
                    .setMessage(getString(R.string.Prompt_Delete_Message_Position) + " " + position + "\n" + getString(R.string.Prompt_Delete_Message_ID) + " " + id)
                    .setPositiveButton(R.string.Prompt_Positive, (click, arg) -> {
                        favsList.remove(position).deleteImage(sqLiteDatabase);
                        myListAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.Prompt_Negative,(click, arg)->{ })
                    .create().show();
            return true;
        });

        // should start fragment on tap press
        listView.setOnItemClickListener((list,view,position,id)->{
            FavouritesList favouritesList = favsList.get(position);
            Bundle dataToPass = new Bundle();
            dataToPass.putLong(ITEM_ID,id);
            dataToPass.putString(TEXT_TITLE, favouritesList.title);
            dataToPass.putString(TEXT_DATE, favouritesList.date);
//            dataToPass.putParcelable(BITMAP_IMAGE, favouritesList.bitmapImageConv);
//            byte[] imageConvToByte = Converter.getBytes(favouritesList.bitmapImageConv);
//            dataToPass.putByteArray(BYTE_IMAGE,imageConvToByte);
            Intent nextActivity = new Intent(ActivityFavourites.this, EmptyActivity.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity);
        });



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

    /**
     * inner class FavouritesList
     */
    //TODO favourites list favourites
    private static class FavouritesList {
        private long id = -1L;
        private final String date;
        private final String title;
        private final Bitmap bitmapImageConv;

        private FavouritesList(long id, String title, String date, Bitmap bitmapImageConv){
            this.id = id;
            this.title = title;
            this.date = date;
            this.bitmapImageConv = bitmapImageConv;
        }

        private FavouritesList(String title, String date, Bitmap bitmapImageConv) {
            this.title = title;
            this.date = date;
            this.bitmapImageConv = bitmapImageConv;
        }

        //delete from database
        protected void deleteImage(SQLiteDatabase sqLiteDatabase){
            if (this.id != -1L){
                sqLiteDatabase.delete(MyOpener.TABLE_NAME,String.format("%s = ?",MyOpener.COL_ID), new String[]{Long.toString(this.id)});
            }
            this.id = -1L;
        }

    }

    /**
     *
     * @param db
     * @return
     */

    private List<FavouritesList> loadDataFromDatabase(SQLiteDatabase db) {
        Cursor c = db.query(MyOpener.TABLE_NAME, null, null, null, null, null, null);
        List<FavouritesList> results = new ArrayList<>();
        while (c.moveToNext()){
            //get blob from database then convert it very gurd
            byte[] bytedImage = c.getBlob(c.getColumnIndex(MyOpener.COL_IMAGE));
            Bitmap initConv = Converter.getImage(bytedImage);
            results.add(new FavouritesList(c.getLong(c.getColumnIndex(MyOpener.COL_ID)),c.getString(c.getColumnIndex(MyOpener.COL_NAME)),c.getString(c.getColumnIndex(MyOpener.COL_DATE)),initConv));
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
            FavouritesList favouritesList = favsList.get(position);
            View view = inflater.inflate(R.layout.list_row,parent,false);
            ((TextView) view.findViewById(R.id.Text_List_Row_Date_Editable)).setText(favouritesList.date);
            ((TextView) view.findViewById(R.id.Text_List_Row_Title_Editable)).setText(favouritesList.title);
            ((ImageView) view.findViewById(R.id.Image_List_Row_Image)).setImageBitmap(favouritesList.bitmapImageConv);
            return view;
        }
    }

}
