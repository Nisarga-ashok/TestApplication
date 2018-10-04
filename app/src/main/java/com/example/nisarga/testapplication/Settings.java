package com.example.nisarga.testapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Settings extends AppCompatActivity {
    public static Context context;
    private Date myDate;
    private TextView mDateTestView;
    private TextView mTimeTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context=getApplicationContext();

        myDate= Calendar.getInstance().getTime();
        mDateTestView=findViewById(R.id.Date);
        mDateTestView.setText(" "+myDate.getDate()+"/"+myDate.getMonth() +" | "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds());

        myDate= Calendar.getInstance().getTime();
        mTimeTestView=findViewById(R.id.Time);
        mTimeTestView.setText(" "+myDate.getDate()+"/"+myDate.getMonth() +" | "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds());
    }
}
