package com.example.nisarga.testapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
    Button Updaterangebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context=getApplicationContext();
        Minimum=findViewById(R.id.Min);
        Optimal=findViewById(R.id.Optimal);
        Maximum=findViewById(R.id.Max);
        Updaterangebutton=findViewById(R.id.Updaterange);

        myDate= Calendar.getInstance().getTime();
        mDateTestView=findViewById(R.id.Date);
        mDateTestView.setText(" "+myDate.getDate()+"/"+myDate.getMonth());

        myDate= Calendar.getInstance().getTime();
        mTimeTestView=findViewById(R.id.Time);
        mTimeTestView.setText(+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds());

        final Context context=this;


        updateUI();
        Updaterangebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SharedPreferences sharedPreferences=context.getSharedPreferences(getString(R.string.preference_file_key),context.MODE_PRIVATE);
                final SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putInt(getString(R.string.preference_min_value),Integer.parseInt(Minimum.getText().toString()));
                editor.putInt(getString(R.string.preference_optimal_value),Integer.parseInt(Optimal.getText().toString()));
                editor.putInt(getString(R.string.preference_max_value),Integer.parseInt(Maximum.getText().toString()));
                editor.commit();
                Toast.makeText(context,"The values are updated",Toast.LENGTH_SHORT).show();
                updateUI();

            }
        });
    }

    void updateUI()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(getString(R.string.preference_file_key),context.MODE_PRIVATE);

        int minimum=sharedPreferences.getInt(getString(R.string.preference_min_value),60);
        int optimal=sharedPreferences.getInt(getString(R.string.preference_optimal_value),72);
        int maximum=sharedPreferences.getInt(getString(R.string.preference_max_value),80);
        Minimum.setText(""+minimum);
        Optimal.setText(""+optimal);
        Maximum.setText(""+maximum);
    }


}
