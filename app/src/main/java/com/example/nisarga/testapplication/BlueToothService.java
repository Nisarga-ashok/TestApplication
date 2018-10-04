package com.example.nisarga.testapplication;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class BlueToothService extends IntentService
{
    private static final String UUID_SERIAL_PORT_PROFILE
            = "00001101-0000-1000-8000-00805F9B34FB";

    private BluetoothSocket mSocket = null;
    private BufferedReader mBufferedReader = null;
    private BluetoothDevice aurdinoBluetooth=null;
    private BluetoothServerSocket mySocket = null;
    private BluetoothSocket incommingSocket=null;

    public static final int REQUEST_ENABLE_BT=0;
    BluetoothAdapter mBluetoothAdapter;
    public BlueToothService()
    {
        super("BlueToothService");
    }

    @Override
    public void onStart( Intent intent, int startId)
    {
        Toast.makeText(getApplicationContext(), "Samaya Service Started", Toast.LENGTH_SHORT).show();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            Toast.makeText(getApplicationContext(), "The device does not support bluetooth", Toast.LENGTH_SHORT).show();
        } else
        {
            if (!mBluetoothAdapter.isEnabled())
            {
                Toast.makeText(getApplicationContext(), "Please enable bluetooth", Toast.LENGTH_SHORT).show();
            }
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0)
        {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices)
            {
                if (device.getAddress().equals("F0:D7:AA:5D:0A:4D"))
                {
                    ConnectThread connectThread=new ConnectThread(device);
                    connectThread.start();

                }
            }

            AcceptThread acceptThread = new AcceptThread();
            acceptThread.start();
        }
    }




    private class AcceptThread extends Thread
    {
        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket
            // because mmServerSocket is final.
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code.
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Moto G (5) Plus", UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                Log.d("Bluetooth","Server socket is requested for moto phone");
            } catch (IOException e) {
                Log.e("Bluetooth", "Socket's listen() method failed", e);
            }
            mySocket = tmp;
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    incommingSocket = mySocket.accept();
                    Log.d("Bluetooth","Trying to accept moto phone");
                } catch (IOException e)
                {
                    Log.e("Bluetooth", "Socket's accept() method failed", e);
                    break;
                }

                if (incommingSocket != null)
                {
                    // A connection was accepted. Perform work associated with
                    // the connection in a separate thread.
                    ConnectedThread connectedThread = new ConnectedThread(incommingSocket);
                    connectedThread.start();
                    try
                    {
                        mySocket.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }

            }

        }

    }

    private class ConnectThread extends Thread
    {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            } catch (IOException e) {
                Log.e("Bluetooth", "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        @Override
        public void run()
        {
            // Cancel discovery because it otherwise slows down the connection.
            Log.d("Bluetooth","Client socket is made for moto phone");

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
                Log.d("Bluetooth","Trying to connect to moto phone");
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e("Bluetooth", "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            ConnectedThread connectedThread = new ConnectedThread(mmSocket);
            connectedThread.start();
        }


    }

    @Override
    protected void onHandleIntent(Intent intent)
    {

    }

    private class ConnectedThread extends Thread
    {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            Log.d("MyTag","Connection Success");

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e("Bluetooth", "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("Bluetooth", "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        @Override
        public void run()
        {
            mmBuffer = new byte[1024];
            int numBytes=0; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                   numBytes= mmInStream.read(mmBuffer);


                } catch (IOException e) {
                    Log.d("MyTag", "Input stream was disconnected", e);
                    String receivedMessage=new String(mmBuffer,0,numBytes);
                    Log.i("Bluetooth",receivedMessage);
                    break;
                }
            }

        }
    }
}
