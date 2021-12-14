package com.example.cst2335_final_project;

import static com.example.cst2335_final_project.MyOpener.COL_DATE;
import static com.example.cst2335_final_project.MyOpener.COL_IMAGE;
import static com.example.cst2335_final_project.MyOpener.TABLE_NAME;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class ActivityImageGen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String TAG = this.getClass().getSimpleName();
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView yyyymmdd, whatActivityText, imageTitle,dummyHD,dummyNonHD;
    public static ProgressBar progressBar;
    ImageView imageOfDay;
    //save to fav variables
    Bitmap bitmapImage;
    String dbNameDate;
    SQLiteDatabase sqLiteDatabase;

    Button doneButton,favBtn;
    public final static String nasaURL1 = "https://api.nasa.gov/planetary/apod?api_key=";
    public final static String nasaURL2 = "&date=";
    public final static String urlKey ="GtIt36FWCP5fvOaaJVjUAiEqTmlNSmZqoH6jAT7L";
    public static String userDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_generated);

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
        //Progress bar
        doneButton = findViewById(R.id.doneBtn);
        progressBar = findViewById(R.id.prgBar);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            userDate = extras.getString("the-date");
//            Log.e(TAG,"the date is: "+userDate);
        }
        // Date textview
        //TODOFINISHED edit TextView yyyy_mm_dd to take user chosen date using onActivityResult
        yyyymmdd = findViewById(R.id.yyyy_mm_dd);
        imageTitle = findViewById(R.id.imageOfTheDay);
        dummyHD = findViewById(R.id.TextView_Img_Gen_HD_Dummy);
        dummyNonHD = findViewById(R.id.TextView_Img_Gen_nonHD_Dummy);

        //image of the day
        imageOfDay = findViewById(R.id.image1);

        //TODO save button to send image to database with date as name
        //favourites Snackbar Button -> Saved/Unsaved to Favourites
        favBtn = findViewById(R.id.Button_Generate_Save_Image);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] convertedImage = Converter.getBytes(bitmapImage);
                addToDatabase(dbNameDate,convertedImage);
                Snackbar.make(favBtn, getString(R.string.Show_Message_Saved_To_Favourites), Snackbar.LENGTH_LONG).show();
            }
        });
//        Snackbar.make(favBtn, getString(R.string.Show_Message_Not_Saved_To_Favourites), Snackbar.LENGTH_LONG)
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackToSelection = new Intent(ActivityImageGen.this,ActivitySelection.class);
                ActivityImageGen.this.startActivity(goBackToSelection);
            }
        });

        //execute the AsyncTask

        if (userDate != null){
            ImageQuery imageQueryFromSelectionActivity = new ImageQuery();
            imageQueryFromSelectionActivity.execute(nasaURL1+urlKey+nasaURL2+userDate);
//        Log.e(TAG,"the full URL is: "+nasaURL1+urlKey+nasaURL2+userDate);
        }


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
                Intent goToMainPage = new Intent(ActivityImageGen.this,ActivitySelection.class);
                ActivityImageGen.this.startActivity(goToMainPage);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Help:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.Alert_Title_Help_AIG))
                        .setMessage(getString(R.string.Alert_Message_Help_AIG))
                        .setNeutralButton(getString(R.string.Alert_Neutral_Button), null)
                        .create();
                dialog.show();
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_Help_alert,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Favourites:
                Intent goToFavPage = new Intent(ActivityImageGen.this,ActivityFavourites.class);
                ActivityImageGen.this.startActivity(goToFavPage);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Random_Generator:
                Intent goToRandomPage = new Intent(ActivityImageGen.this,ActivityRandomGen.class);
                ActivityImageGen.this.startActivity(goToRandomPage);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Search_By_Date:
//                Intent goToImgGen = new Intent(ActivityImageGen.this,ActivityImageGen.class);
//                ActivityImageGen.this.startActivity(goToImgGen);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_is_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Toolbar_Logout:
                Intent goToLogout = new Intent(ActivityImageGen.this,MainActivity.class);
                ActivityImageGen.this.startActivity(goToLogout);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Nav_Main:
                Intent goToMainPage = new Intent(ActivityImageGen.this,ActivitySelection.class);
                ActivityImageGen.this.startActivity(goToMainPage);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_Main_Page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Fav_List:
                Intent goToFavPage = new Intent(ActivityImageGen.this,ActivityFavourites.class);
                ActivityImageGen.this.startActivity(goToFavPage);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_Favourites, Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Random_Image:
                Intent goToRandomPage = new Intent(ActivityImageGen.this,ActivityRandomGen.class);
                ActivityImageGen.this.startActivity(goToRandomPage);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_Random_Image,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Image_Date:
//                Intent goToImgGen = new Intent(ActivityImageGen.this,ActivityImageGen.class);
//                ActivityImageGen.this.startActivity(goToImgGen);
                Toast.makeText(ActivityImageGen.this,R.string.Show_Message_is_Image_Date,Toast.LENGTH_SHORT).show();
                break;
            case R.id.Menu_Nav_Logout:
                Intent goToLogout = new Intent(ActivityImageGen.this,MainActivity.class);
                ActivityImageGen.this.startActivity(goToLogout);
                Toast.makeText(getApplicationContext(),R.string.Show_Message_Logged_Out,Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

    private class ImageQuery extends AsyncTask<String, Integer, String>{
        //TODO FINISHED should this stay a String or to a Date variable to order by date later
        private String Full_URL = nasaURL1+urlKey+nasaURL2+userDate;
        private String title;
        private String HDURL;
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
                Thread.sleep(1000);
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                publishProgress(50);
                Thread.sleep(1000);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null){
                    sb.append(line +"\n");
                }
                String result = sb.toString();
                JSONObject jsonObject = new JSONObject(result);
                HDURL = (String) jsonObject.getString("hdurl");
                publishProgress(50);
                Thread.sleep(1000);
                JSON_Date = (String) jsonObject.getString("date");
                publishProgress(75);
                Thread.sleep(1000);
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
                Thread.sleep(1000);

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
            ActivityImageGen.progressBar.setProgress(values[0]);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            yyyymmdd.setText(JSON_Date);
            dbNameDate = JSON_Date;
            imageTitle.setText(title);
            dummyNonHD.setText(nonHDURL);
            dummyHD.setText(HDURL);
            imageOfDay.setImageBitmap(image);
            bitmapImage = image;
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    // save the image to database method
    public void addToDatabase(String date, byte[] byteImage) throws SQLiteException{
        sqLiteDatabase = new MyOpener(this).getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(COL_DATE, date);
        cv.put(COL_IMAGE, byteImage);
        sqLiteDatabase.insert(TABLE_NAME, null, cv );
    }

}
