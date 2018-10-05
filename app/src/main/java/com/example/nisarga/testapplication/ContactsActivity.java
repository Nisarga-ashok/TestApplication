package com.example.nisarga.testapplication;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.util.Set;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.BluetoothCallback;
import me.aflak.bluetooth.DeviceCallback;
import me.aflak.bluetooth.DiscoveryCallback;

public class ContactsActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST=3;

    EditText firstName;
    EditText firstPhone;
    EditText secondName;
    EditText secondPhone;
    EditText thirdName;
    EditText thirdPhone;
    EditText messageText;

    Button firstButton;
    Button secondButton;
    Button thridButton;
    Button firstUpdateButton;
    Button secondUpdateButton;
    Button thirdUpdateButton;
    Button messageUpdateButton;
    private Button mSettings;

    public static String TAG="TestTag";



    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mSettings=findViewById(R.id.Settings);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ContactsActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        checkPermission();

//        if(!isServiceRunning(BlueToothService.class))
//        {
//            Intent intent = new Intent(this, BlueToothService.class);
//            startService(intent);
//        }

        Bluetooth bluetooth = new Bluetooth(getApplicationContext());
        bluetooth.onStart();
        bluetooth.enable();
        bluetooth.setBluetoothCallback(new BluetoothCallback()
        {
            @Override
            public void onBluetoothTurningOn()
            {

            }

            @Override
            public void onBluetoothOn()
            {

            }

            @Override
            public void onBluetoothTurningOff()
            {

            }

            @Override
            public void onBluetoothOff()
            {

            }

            @Override
            public void onUserDeniedActivation()
            {

            }
        });

        bluetooth.setDiscoveryCallback(new DiscoveryCallback()
        {
            @Override
            public void onDiscoveryStarted()
            {

            }

            @Override
            public void onDiscoveryFinished()
            {

            }

            @Override
            public void onDeviceFound(BluetoothDevice device)
            {

            }

            @Override
            public void onDevicePaired(BluetoothDevice device)
            {

            }

            @Override
            public void onDeviceUnpaired(BluetoothDevice device)
            {

            }

            @Override
            public void onError(String message)
            {

            }
        });

        BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                Log.d(TAG,deviceName);
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d(TAG,deviceHardwareAddress);
            }
        }

        bluetooth.connectToAddress("B8:86:87:CE:45:0E");
        Log.d(TAG,"Pair is called");

        bluetooth.setDeviceCallback(new DeviceCallback()
        {
            @Override
            public void onDeviceConnected(BluetoothDevice device)
            {
                Log.d(TAG,"Device connected");
            }

            @Override
            public void onDeviceDisconnected(BluetoothDevice device, String message)
            {
                Log.d(TAG,"Device disconnected");
            }

            @Override
            public void onMessage(String message)
            {

            }

            @Override
            public void onError(String message)
            {
                Log.d(TAG,"Device error");
            }

            @Override
            public void onConnectError(BluetoothDevice device, String message)
            {

            }
        });


        context=getApplicationContext();

        firstName=findViewById(R.id.namep1);
        firstName.setShowSoftInputOnFocus(false);
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

        messageText=findViewById(R.id.default_message);
        messageUpdateButton=findViewById(R.id.msgupdate);



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

        messageUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper mDataBaseHelper=new DataBaseHelper(getApplicationContext());
                SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put(DataBaseSchema.ATTR1_MESSAGE,messageText.getText().toString());
                values.put(DataBaseSchema.ATTR3_PRIORITY,"4");

                String args=DataBaseSchema.ATTR3_PRIORITY+" LIKE ?";
                String argSelection[]={"4"};


                int rowAffected=0;
                try {
                    rowAffected = db.update(DataBaseSchema.TABLE_NAME_MESSAGE, values, args, argSelection);
                }
                catch (Exception e)
                {

                }

                if(rowAffected==0)
                {
                    db.insert(DataBaseSchema.TABLE_NAME_MESSAGE,null,values);
                    Toast.makeText(getApplicationContext(),"Default message set",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Updated default message",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==MY_PERMISSIONS_REQUEST)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"Permission granted",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"Permission is needed for the app",Toast.LENGTH_SHORT).show();
                finish();
            }
        }


    }

    void updateUI()
    {
        DataBaseHelper mDataBaseHelper= new DataBaseHelper(getApplicationContext());
        SQLiteDatabase db=mDataBaseHelper.getReadableDatabase();

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

        Cursor cursor1=db.query(DataBaseSchema.TABLE_NAME_MESSAGE,
                null,
                null,
                null,
                null,
                null,
                null
        );

        try
        {
            cursor1.moveToFirst();
            messageText.setText(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseSchema.ATTR1_MESSAGE)));
        }
        catch (Exception e)
        {

        }
    }

    void checkPermission()
    {
        if ((ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)) {
            // Permission is not granted
            ActivityCompat.requestPermissions(ContactsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST);

        }


    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
