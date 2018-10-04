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


        updateUI();

        Saverangebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                    SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DataBaseSchema.ATTR1_MIN, Minimum.getText().toString());
                    values.put(DataBaseSchema.ATTR2_OPTIMAL, Optimal.getText().toString());
                    values.put(DataBaseSchema.ATTR3_MAX, Maximum.getText().toString());

                    db.insert(DataBaseSchema.TABLE_NAME, null, values);
                    Toast.makeText(getApplicationContext(),"Inserted into databae",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("MyTag",e.toString());
                }

                updateUI();
            }
        });
        Updaterangebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put(DataBaseSchema.ATTR1_MIN,Minimum.getText().toString());
                values.put(DataBaseSchema.ATTR2_OPTIMAL,Optimal.getText().toString());
                values.put(DataBaseSchema.ATTR3_MAX,Maximum.getText().toString());

                String selection=DataBaseSchema.ATTR3_PRIORITY+" LIKE ?";
                String[] selectionArgs={"1"};

                int rowsAffected=db.update(DataBaseSchema.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                if(rowsAffected==0)
                {
                    Toast.makeText(getApplicationContext(),"Please click on Insert Data Button",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Record updated", Toast.LENGTH_SHORT).show();
                    updateUI();
                }

            }
        });



        myDate= Calendar.getInstance().getTime();
        mDateTestView=findViewById(R.id.Date);
        mDateTestView.setText(" "+myDate.getDate()+"/"+myDate.getMonth());

        myDate= Calendar.getInstance().getTime();
        mTimeTestView=findViewById(R.id.Time);
        mTimeTestView.setText(+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds());
    }
    void updateUI()
    {
        DataBaseHelper mDataBaseHelper= new DataBaseHelper(getApplicationContext());
        SQLiteDatabase db=mDataBaseHelper.getReadableDatabase();

        Cursor cursor=db.query(
                DataBaseSchema.TABLE_NAME_PULSE_RANGE,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List Minimum=new ArrayList<>();
        List Optimal=new ArrayList<>();
        List Maximum=new ArrayList<>();
        while (cursor.moveToNext())
        {
            String min=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseSchema.ATTR1_MIN));
            String opt=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseSchema.ATTR2_OPTIMAL));
            String max=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseSchema.ATTR2_OPTIMAL))
            Log.i("MyTag",min+" is retrieved");
            Log.i("MyTag",opt+" is retrieved");
            Log.i("MyTag",max+" is retrieved");
            Minimum.add(min);
            Optimal.add(opt);
            Maximum.add(max);
        }

        if(names.size()<=3 && names.size()>0)
        {
            firstName.setText(names.get(0).toString());
            firstPhone.setText(phone_number.get(0).toString());
        }

        if(names.size()<=3 && names.size()>1)
        {
            secondName.setText(names.get(1).toString());
            secondPhone.setText(phone_number.get(1).toString());
        }

        if(names.size()<=3 && names.size()>2)
        {
            thirdName.setText(names.get(2).toString());
            thirdPhone.setText(phone_number.get(2).toString());
        }

        int count=names.size();
        if(count==1)
            firstButton.setEnabled(false);

        if(count==2)
        {
            firstButton.setEnabled(false);
            secondButton.setEnabled(false);
        }

        if(count==3)
        {
            firstButton.setEnabled(false);
            secondButton.setEnabled(false);
            thridButton.setEnabled(false);
        }

        Cursor cursor1=db.query(DataBaseSchema.TABLE_NAME_MESSAGE,
                null,
                null,
                null,
                null,
                null,
                null
        );

    }

}
