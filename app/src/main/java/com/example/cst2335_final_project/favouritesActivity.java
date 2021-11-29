package com.example.cst2335_final_project;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class favouritesActivity extends AppCompatActivity {
    public static ListView favListView;
    public static final String ACTIVITY_NAME = "FAV_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        //Favourites ListView
        favListView = (ListView)findViewById(R.id.listView1);

    }
}
