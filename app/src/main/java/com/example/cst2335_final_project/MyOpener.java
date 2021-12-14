package com.example.cst2335_final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "NASADB";
    protected final static int VERSION_NUMBER = 1;
    final static String TABLE_NAME = "FAVOURITE";
    final static String COL_ID = "FavouriteID";
    final static String COL_DATE = "Date";
    final static String COL_IMAGE = "ImageBlob";



    public MyOpener(Context ctx){
        super(ctx,DATABASE_NAME,null,VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s blob);", TABLE_NAME, COL_ID, COL_DATE, COL_IMAGE));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(sqLiteDatabase);
    }
}
