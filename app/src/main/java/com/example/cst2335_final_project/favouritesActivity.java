package com.example.cst2335_final_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class favouritesActivity extends AppCompatActivity {

    private static List<FavourtiesList> favsList = new ArrayList<>();
    public static final String ACTIVITY_NAME = "FAV_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        //Favourites ListView
        ListView favListView;
        favListView = (ListView)findViewById(R.id.listView1);

        //set the adapter to populate listview
        MyListAdapter myListAdapter = new MyListAdapter();


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
