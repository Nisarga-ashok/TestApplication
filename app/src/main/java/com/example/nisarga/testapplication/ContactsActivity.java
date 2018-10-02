package com.example.nisarga.testapplication;

import android.content.ContentValues;
import android.content.Context;
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
    Button firstUpdateButton;
    Button secondUpdateButton;
    Button thirdUpdateButton;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        context=getApplicationContext();

        firstName=findViewById(R.id.namep1);
        firstPhone=findViewById(R.id.php1);
        firstButton=findViewById(R.id.addp1);
        firstUpdateButton=findViewById(R.id.updatep1);

        secondName=findViewById(R.id.namep2);
        secondPhone=findViewById(R.id.php2);
        secondButton=findViewById(R.id.addp2);
        secondUpdateButton=findViewById(R.id.updatep2);

        thirdName=findViewById(R.id.namep3);
        thirdPhone=findViewById(R.id.php3);
        thridButton=findViewById(R.id.addp3);
        thirdUpdateButton=findViewById(R.id.updatep3);


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
                    Toast.makeText(getApplicationContext(),"Inserted into databae",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("MyTag",e.toString());
                }

                updateUI();
            }
        });

        firstUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put(DataBaseSchema.ATTR1_NAME,firstName.getText().toString());
                values.put(DataBaseSchema.ATTR2_PHONE_NUMBER,firstPhone.getText().toString());

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
                    Toast.makeText(getApplicationContext(),"Inserted into databae",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("MyTag",e.toString());
                }

                updateUI();
            }
        });

        secondUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put(DataBaseSchema.ATTR1_NAME,secondName.getText().toString());
                values.put(DataBaseSchema.ATTR2_PHONE_NUMBER,secondPhone.getText().toString());

                String selection=DataBaseSchema.ATTR3_PRIORITY+" LIKE ?";
                String[] selectionArgs={"2"};

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
                    Toast.makeText(getApplicationContext(),"Inserted into databae",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("MyTag",e.toString());
                }

                updateUI();
            }
        });

        thirdUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
                SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put(DataBaseSchema.ATTR1_NAME,thirdName.getText().toString());
                values.put(DataBaseSchema.ATTR2_PHONE_NUMBER,thirdPhone.getText().toString());

                String selection=DataBaseSchema.ATTR3_PRIORITY+" LIKE ?";
                String[] selectionArgs={"3"};

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




    }
}
