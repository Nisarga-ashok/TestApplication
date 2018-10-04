package com.example.nisarga.testapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Settings extends AppCompatActivity {
    public static Context context;
    private Date myDate;
    private TextView mDateTestView;
    private TextView mTimeTestView;
    EditText Maximum;
    EditText Minimum;
    EditText Optimal;
    Button Saverangebutton;
    Button Updaterangebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context=getApplicationContext();
        Minimum=findViewById(R.id.Min);
        Optimal=findViewById(R.id.Optimal);
        Maximum=findViewById(R.id.Max);
        Saverangebutton=findViewById(R.id.Saverange);
        Updaterangebutton=findViewById(R.id.Updaterange);

        myDate= Calendar.getInstance().getTime();
        mDateTestView=findViewById(R.id.Date);
        mDateTestView.setText(" "+myDate.getDate()+"/"+myDate.getMonth());

        myDate= Calendar.getInstance().getTime();
        mTimeTestView=findViewById(R.id.Time);
        mTimeTestView.setText(+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds());
    }


}
