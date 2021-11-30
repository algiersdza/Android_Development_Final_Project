package com.example.cst2335_final_project;

import static com.example.cst2335_final_project.favouritesActivity.ACTIVITY_NAME;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityImageGen extends AppCompatActivity {
    public static TextView ddmmyy;
    public static ProgressBar progressBar;
    public static ImageView imageOfDay;
    CheckBox favCheck;
    //public static String urlKey o.o

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_generated);

        //Progress bar
        progressBar = (ProgressBar) findViewById(R.id.prgBar);

        // Date textview
        //TODO edit TextView ddmmyy to take user chosen date using onActivityResult
        ddmmyy = (TextView) findViewById(R.id.ddmmyy);

        //image of the day
        imageOfDay = (ImageView)findViewById(R.id.image1);

        //arrival toast "Image Generated"
        Toast.makeText(this, "Image Generated", Toast.LENGTH_LONG).show();

        //favourites Snackbar Checkbox -> Saved/Unsaved to Favourites
        favCheck = (CheckBox) findViewById(R.id.favBtn);
        favCheck.setOnCheckedChangeListener((CompoundButton cb, boolean b) -> {
            if (b){//save
                Snackbar.make(favCheck, getString(R.string.saved2fav), Snackbar.LENGTH_LONG)
                        .setAction("Undo", click -> favCheck.setChecked(!b)).show();
            }else{
                Snackbar.make(favCheck, getString(R.string.unsaved), Snackbar.LENGTH_LONG)
                        .setAction("Undo", click -> favCheck.setChecked(!b)).show();
            }
        });

        //set up url api key
        //urlKey = "GtIt36FWCP5fvOaaJVjUAiEqTmlNSmZqoH6jAT7L";

        //execute the AsyncTask
        ImageQuery imageQueryObj = new ImageQuery();
        //imageQueryObj.execute(urlKey);

    }
    private class ImageQuery extends AsyncTask<String, Integer, String>{
        //TODO should this stay a String or to a Date variable to order by date later
        private String ddmmyy2;
        private Bitmap image=null; //imageOfTheDay

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //set local date values to the xml textview element
                //TODO set ddmmyy2
            /*
            ddmmyy.setText("- " + ddmmyy2 + "- ");
            imageOfDay.setImageBitmap(image);
             */

            //hide progress bar once fully executed
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e(ACTIVITY_NAME, "Heya friends ^O.O^ : In onProgressUpdate()");
            //set progress bar as visible while loading contents
            progressBar.setVisibility(View.VISIBLE);
            //start bar progress
            ActivityImageGen.progressBar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... strings) {
            //TODO notes below lady. also double check <String int string> ^


            try {
                /*
                //1 create a URL object of which server to contact:
                URL url = new URL(urlKey);
                //2 open connection
                HttpURLConnection con = (HttpURLConnection) urlKey.openConnection();
                //3 wait for data
                InputStream response = con.getInputStream();
                //4 pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                int eventType = xpp.getEventType();//parser is CURRENTLY at START_DOCUMENT
                while(eventType != xmlPullParser.END_DOCUMENT);{
                    if(eventType == xmlPullParser.START_TAG){
                        //if we reach here, we are pointing at the start tag
                        if(xpp.getName().equals("date")){ //TODO purposeful error here to check if that's the name of teh date element

                            //set date2 value to the get api value and update bar
                            ddmmyy2 = xpp.getAttributeValue(null, "ddmmyy");
                 */
                            Log.e("DISPLAY VARIABLE: ", "Retrieving <date> element ddmmyy " + ddmmyy);
                            publishProgress(50);
                 /*
                        }
                        else if (xpp.getName().equals("")){//TODO check how to retrieve an image
                  */
                            //get image
                            Log.e("DISPLAY VARIABLE: ", "Retrieving <image> Bitmap");
                            publishProgress(75);
                   /*     }
                    }
                    //eventType = xpp.next();
                }
                    */

            } catch (Exception e) {
                Log.e("ERROR ", e.getMessage());
            }
            //JSON needed?

            return "Finished";
        }
    }
}
