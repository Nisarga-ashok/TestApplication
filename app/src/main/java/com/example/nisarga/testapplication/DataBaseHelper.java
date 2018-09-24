package com.example.nisarga.testapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts.db";
    public static final String SQL_CREATE_ENTRIES ="create table "+DataBaseSchema.TABLE_NAME+"("+DataBaseSchema.ATTR1_NAME+" varchar(20), "+DataBaseSchema.ATTR2_PHONE_NUMBER +" int(10))";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
        Toast.makeText(MainActivity.context,"Database created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
