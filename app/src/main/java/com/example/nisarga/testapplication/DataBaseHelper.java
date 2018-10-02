package com.example.nisarga.testapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts.db";
    public static final String SQL_CREATE_ENTRIES ="create table "+DataBaseSchema.TABLE_NAME+"("+DataBaseSchema._ID+" INTEGER PRIMARY KEY,"+DataBaseSchema.ATTR1_NAME+" varchar(20), "+DataBaseSchema.ATTR2_PHONE_NUMBER +" varchar(10))";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("MyTag",SQL_CREATE_ENTRIES);


    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Toast.makeText(MainActivity.context,"Database created",Toast.LENGTH_SHORT).show();
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
