package com.example.nisarga.testapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    EditText firstName;
    EditText firstPhone;
    EditText secondName;
    EditText secondPhone;
    EditText thirdName;
    EditText thirdPhone;

    Button firstButton;
    Button secondButton;
    Button thridButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);



        firstName=findViewById(R.id.namep1);
        firstPhone=findViewById(R.id.php1);
        firstButton=findViewById(R.id.addp1);

        secondName=findViewById(R.id.namep2);
        secondPhone=findViewById(R.id.php2);
        secondButton=findViewById(R.id.addp2);

        thirdName=findViewById(R.id.namep3);
        thirdPhone=findViewById(R.id.php3);
        thridButton=findViewById(R.id.addp3);

        updateUI();

        firstButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                    SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DataBaseSchema.ATTR1_NAME, firstName.getText().toString());
                    values.put(DataBaseSchema.ATTR2_PHONE_NUMBER, firstPhone.getText().toString());
                    values.put(DataBaseSchema.ATTR3_PRIORITY,"1");

                    db.insert(DataBaseSchema.TABLE_NAME, null, values);
                    Toast.makeText(MainActivity.context,"Inserted into databae",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("MyTag",e.toString());
                }

                updateUI();
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                    SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DataBaseSchema.ATTR1_NAME, secondName.getText().toString());
                    values.put(DataBaseSchema.ATTR2_PHONE_NUMBER, secondPhone.getText().toString());
                    values.put(DataBaseSchema.ATTR3_PRIORITY,"2");

                    db.insert(DataBaseSchema.TABLE_NAME, null, values);
                    Toast.makeText(MainActivity.context,"Inserted into databae",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("MyTag",e.toString());
                }

                updateUI();
            }
        });

        thridButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                    SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DataBaseSchema.ATTR1_NAME, thirdName.getText().toString());
                    values.put(DataBaseSchema.ATTR2_PHONE_NUMBER, thirdPhone.getText().toString());
                    values.put(DataBaseSchema.ATTR3_PRIORITY,"3");

                    db.insert(DataBaseSchema.TABLE_NAME, null, values);
                    Toast.makeText(MainActivity.context,"Inserted into databae",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("MyTag",e.toString());
                }

                updateUI();
            }
        });

    }

    void updateUI()
    {
        DataBaseHelper mDataBaseHelper= new DataBaseHelper(getApplicationContext());
        SQLiteDatabase db=mDataBaseHelper.getReadableDatabase();

        String[] projections={DataBaseSchema._ID,DataBaseSchema.ATTR1_NAME,DataBaseSchema.ATTR2_PHONE_NUMBER,DataBaseSchema.ATTR3_PRIORITY};

        Cursor cursor=db.query(
                DataBaseSchema.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List names=new ArrayList<>();
        List phone_number=new ArrayList<>();
        while (cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseSchema.ATTR1_NAME));
            String ph_no=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseSchema.ATTR2_PHONE_NUMBER));
            Log.i("MyTag",name+" is retrieved");
            Log.i("MyTag",ph_no+" is retrieved");
            names.add(name);
            phone_number.add(ph_no);
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




    }
}
