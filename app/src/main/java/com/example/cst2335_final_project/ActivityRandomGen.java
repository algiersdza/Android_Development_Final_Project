package com.example.cst2335_final_project;

import static com.example.cst2335_final_project.MyOpener.COL_DATE;
import static com.example.cst2335_final_project.MyOpener.COL_IMAGE;
import static com.example.cst2335_final_project.MyOpener.COL_NAME;
import static com.example.cst2335_final_project.MyOpener.TABLE_NAME;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class ActivityRandomGen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String TAG = this.getClass().getSimpleName();
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView whatActivityText;

    //variables for random gen
    TextView yyyymmdd, imageDay;
    ImageView imageView;
    private static ProgressBar progressBar;
    Button BtnFav, BtnGen;
    public final static String nasaURL1 = "https://api.nasa.gov/planetary/apod?api_key=";
    public final static String nasaURL2 = "&date=";
    public final static String urlKey ="GtIt36FWCP5fvOaaJVjUAiEqTmlNSmZqoH6jAT7L";
    public static String randomDate = null;

    //save to fav variables
    Bitmap bitmapImage;
    String dbNameDate, favTitle;
    SQLiteDatabase sqLiteDatabase;

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

        yyyymmdd = findViewById(R.id.TextView_Random_Date_Editable);
        imageDay = findViewById(R.id.TextView_Random_Title_Editable);
        imageView = findViewById(R.id.Image_Random_Image);
        progressBar = findViewById(R.id.Progress_Random_Progress);

        //generate date and go!
        BtnGen = findViewById(R.id.Button_Random_Lucky_Button);
        BtnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //random lower bound 2017-1-1, upper bound !> today
                int year;
                int month;
                int day;
                boolean condition = false;
                while (!condition){
                    Calendar cal = Calendar.getInstance();
                    int DAY_OF_MONTH = cal.get(Calendar.DAY_OF_MONTH);
                    int upperMonth = 12;
                    int lowerMonth = 1;
                    int upperDay = 28;
                    int lowerDay = 1;
                    int upperYear = 2021;
                    int lowerYear = 2017;
                    year = (int) Math.floor(Math.random()*(upperYear-lowerYear+1)+lowerYear);
                    month = (int) Math.floor(Math.random()*(upperMonth-lowerMonth+1)+lowerMonth);
                    day = (int) Math.floor(Math.random()*(upperDay-lowerDay+1)+lowerDay);
                    if ((year == 2021) && (month == 12)){
                        if (day > DAY_OF_MONTH){
                            System.out.println("NOPE! ");
                        }else{
                            condition = true;
                            randomDate = year+"-"+month+"-"+day;
                        }
                    }else{
                        condition = true;
                        randomDate = year+"-"+month+"-"+day;

                    }
                }
                if (randomDate != null){
                    Log.e(TAG,"the full URL is: "+nasaURL1+urlKey+nasaURL2+randomDate);
                    Toast.makeText(ActivityRandomGen.this,getString(R.string.Show_Random_Date_Message)+randomDate, Toast.LENGTH_SHORT).show();
                    ActivityRandomGen.ImageQuery imageQueryFromActivityRandom = new ImageQuery();
                    imageQueryFromActivityRandom.execute(nasaURL1+urlKey+nasaURL2+randomDate);
                }
            }
        });






        //save picture
        BtnFav = findViewById(R.id.Button_Random_Save_Button);
        BtnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getDrawable() != null){
                    byte[] convertedImage = Converter.getBytes(bitmapImage);
                    addToDatabase(favTitle,dbNameDate,convertedImage);
                    Snackbar.make(BtnFav, getString(R.string.Show_Message_Saved_To_Favourites), Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(BtnFav, getString(R.string.Show_Message_No_Image_Selected), Snackbar.LENGTH_LONG).show();
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
                Intent goToMainPage = new Intent(ActivityRandomGen.this,ActivitySelection.class);
                ActivityRandomGen.this.startActivity(goToMainPage);
                Toast.makeText(ActivityRandomGen.this,R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Help:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.Alert_Title_Help_ARG))
                        .setMessage(getString(R.string.Alert_Message_Help_ARG))
                        .setNeutralButton(getString(R.string.Alert_Neutral_Button), null)
                        .create();
                dialog.show();
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

    private class ImageQuery extends AsyncTask<String, Integer, String> {
        //full URL is amended by a randomDate done in BtnGen, no date will be selected greater than current date.
        private String Full_URL = nasaURL1+urlKey+nasaURL2+randomDate;
        private String title;
        private String media_type; // check if its a video or picture
        private String nonHDURL;
        private String JSON_Date;
        private Bitmap image = null; //imageOfTheDay

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(Full_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int code2 = urlConnection.getResponseCode();
//                Log.e(TAG,"Establishing connection "+code2);
                publishProgress(25);
                Thread.sleep(500);
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                publishProgress(50);
                Thread.sleep(500);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null){
                    sb.append(line +"\n");
                }
                String result = sb.toString();
                JSONObject jsonObject = new JSONObject(result);
                media_type = (String) jsonObject.getString("media_type");
                if (media_type.equals("image")){
                    publishProgress(50);
                    Thread.sleep(500);
                    JSON_Date = (String) jsonObject.getString("date");
                    publishProgress(75);
                    Thread.sleep(500);
                    title = (String) jsonObject.getString("title");
                    nonHDURL = (String) jsonObject.getString("url");

                    URL urlImage = new URL(nonHDURL);
                    urlConnection = (HttpURLConnection) urlImage.openConnection();
                    urlConnection.connect();
                    image = BitmapFactory.decodeStream(urlConnection.getInputStream());
                    FileOutputStream outputStream = openFileOutput(title+".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG,80,outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);
                    Thread.sleep(500);
                }else{
                    publishProgress(100);
                    Thread.sleep(500);
                    cancel(true);
                }


//                Log.e(TAG, "doInBackground: is the link HDURL: "+HDURL);
//                Log.e(TAG, "doInBackground: is the link nonHD: "+nonHDURL);
//                Log.e(TAG, "doInBackground: is the link Date: "+JSON_Date);
//                Log.e(TAG, "doInBackground: is the link Title: "+title);
            } catch (IOException | JSONException | InterruptedException e) {
                e.printStackTrace();
            }
            return "Finished";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setVisibility(View.VISIBLE);
            ActivityRandomGen.progressBar.setProgress(values[0]);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            yyyymmdd.setText(JSON_Date);
            dbNameDate = JSON_Date;
            imageDay.setText(title);
            favTitle = title;
            imageView.setImageBitmap(image);
            bitmapImage = image;
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(ActivityRandomGen.this,getString(R.string.Video_File_Error),Toast.LENGTH_LONG).show();
        }
    }

    // save the image to database method
    public void addToDatabase(String name, String date, byte[] byteImage) throws SQLiteException {
        sqLiteDatabase = new MyOpener(this).getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_DATE, date);
        cv.put(COL_IMAGE, byteImage);
        sqLiteDatabase.insert(TABLE_NAME, null, cv );
    }


}