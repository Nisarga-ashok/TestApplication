package com.example.nisarga.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.DatabaseMetaData;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        DataBaseHelper mDataBaseHelper=new DataBaseHelper(getApplicationContext());
    }
}
