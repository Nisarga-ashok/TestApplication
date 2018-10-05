package com.example.nisarga.testapplication;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.BluetoothCallback;
import me.aflak.bluetooth.DeviceCallback;
import me.aflak.bluetooth.DiscoveryCallback;


public class BlueToothService extends IntentService
{

    private final String TAG="TestTag";
    public BlueToothService()
    {
        super("BlueToothService");
    }

    @Override
    protected void onHandleIntent( Intent intent)
    {

    }

    @Override
    public void onCreate()
    {
        super.onCreate();

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
//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(getApplicationContext(),"Your device does not support bluetooth",Toast.LENGTH_SHORT).show();
//            Log.d(TAG,"Device does not support bluetooth");
//            stopSelf();
//        }
//        else
//        {
//            if (!mBluetoothAdapter.isEnabled())
//            {
//                Toast.makeText(getApplicationContext(), "Please turn on the bluetooth", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Bluetooth is not turned on");
//                stopSelf();
//            }
//        }
//
//        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//        if (pairedDevices.size() > 0) {
//            // There are paired devices. Get the name and address of each paired device.
//            for (BluetoothDevice device : pairedDevices) {
//                String deviceName = device.getName();
//                Log.d(TAG,deviceName);
//                String deviceHardwareAddress = device.getAddress(); // MAC address
//                Log.d(TAG,deviceHardwareAddress);
//                if(deviceHardwareAddress.equals("C9:50:76:2F:E0:4F"))
//                {
//                    ConnectThread connectedThread=new ConnectThread(device);
//                    connectedThread.start();
//                }
//            }
//        }
//
//        AcceptThread acceptThread=new AcceptThread();
//        acceptThread.start();


    }

//    private class AcceptThread extends Thread {
//        private final BluetoothServerSocket mmServerSocket;
//
//
//        public AcceptThread() {
//            // Use a temporary object that is later assigned to mmServerSocket
//            // because mmServerSocket is final.
//            BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//            BluetoothServerSocket tmp = null;
//            try {
//                // MY_UUID is the app's UUID string, also used by the client code.
//                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("Samaya", UUID.fromString("9fb4c0be-2229-43e4-be74-e53860a26420"));
//                Log.d(TAG, "Accepting the requests");
//            } catch (IOException e) {
//                Log.e(TAG, "Socket's listen() method failed", e);
//            }
//            mmServerSocket = tmp;
//
//        }
//
//        public void run() {
//            BluetoothSocket socket = null;
//            // Keep listening until exception occurs or a socket is returned.
//            while (socket==null) {
//                try {
//                    Log.d(TAG,""+socket);
//                    socket = mmServerSocket.accept();
//                    Log.d(TAG,socket+"");
//                    Log.d(TAG, socket+"");
//
//                } catch (IOException e) {
//                    Log.e(TAG, "Socket's accept() method failed", e);
//                    break;
//                }
//
//                if (socket != null) {
//                    // A connection was accepted. Perform work associated with
//                    // the connection in a separate thread.
//                    Log.d(TAG, "Closing the server socket");
//                    ConnectedThread connectedThread=new ConnectedThread(socket);
//                    connectedThread.start();
//                    try
//                    {
//                        mmServerSocket.close();
//                    } catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//
//        // Closes the connect socket and causes the thread to finish.
//        public void cancel() {
//            try {
//                mmServerSocket.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Could not close the connect socket", e);
//            }
//        }
//    }
//
//    private class ConnectThread extends Thread {
//        private final BluetoothSocket mmSocket;
//        private final BluetoothDevice mmDevice;
//
//        public ConnectThread(BluetoothDevice device) {
//            // Use a temporary object that is later assigned to mmSocket
//            // because mmSocket is final.
//            BluetoothSocket tmp = null;
//            mmDevice = device;
//
//            try {
//                // Get a BluetoothSocket to connect with the given BluetoothDevice.
//                // MY_UUID is the app's UUID string, also used in the server code.
//                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("9fb4c0be-2229-43e4-be74-e53860a26420"));
//            } catch (IOException e) {
//                Log.e(TAG, "Socket's create() method failed", e);
//            }
//            mmSocket = tmp;
//        }
//
//        public void run() {
//            // Cancel discovery because it otherwise slows down the connection.
//            BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//            mBluetoothAdapter.cancelDiscovery();
//
//            try {
//                // Connect to the remote device through the socket. This call blocks
//                // until it succeeds or throws an exception.
//                Log.d(TAG,"Sent connection request");
//                mmSocket.connect();
//            } catch (IOException connectException) {
//                // Unable to connect; close the socket and return.
//                try {
//                    mmSocket.close();
//                } catch (IOException closeException) {
//                    Log.e(TAG, "Could not close the client socket", closeException);
//                }
//                return;
//            }
//
//            // The connection attempt succeeded. Perform work associated with
//            // the connection in a separate thread.
//            Log.d(TAG,"Connected ");
//        }
//
//        // Closes the client socket and causes the thread to finish.
//        public void cancel() {
//            try {
//                mmSocket.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Could not close the client socket", e);
//            }
//        }
//    }
//
//
//
//        private class ConnectedThread extends Thread {
//            private final BluetoothSocket mmSocket;
//            private final InputStream mmInStream;
//
//            private byte[] mmBuffer; // mmBuffer store for the stream
//
//            public ConnectedThread(BluetoothSocket socket) {
//                Log.d(TAG, "Connected");
//                mmSocket = socket;
//                InputStream tmpIn = null;
//                OutputStream tmpOut = null;
//
//                // Get the input and output streams; using temp objects because
//                // member streams are final.
//                try {
//                    tmpIn = socket.getInputStream();
//                } catch (IOException e) {
//                    Log.e(TAG, "Error occurred when creating input stream", e);
//                }
//                try {
//                    tmpOut = socket.getOutputStream();
//                } catch (IOException e) {
//                    Log.e(TAG, "Error occurred when creating output stream", e);
//                }
//
//                mmInStream = tmpIn;
//
//            }
//
//            public void run() {
//                Log.d(TAG, "Connected");
//                mmBuffer = new byte[1024];
//                int numBytes; // bytes returned from read()
//
//                // Keep listening to the InputStream until an exception occurs.
//                while (true) {
//                    try {
//                        // Read from the InputStream.
//                        numBytes = mmInStream.read(mmBuffer);
//                        Log.d(TAG,mmBuffer.toString());
//
//
//                    } catch (IOException e) {
//                        Log.d(TAG, "Input stream was disconnected", e);
//                        break;
//                    }
//                }
//            }
//
//            // Call this method from the main activity to shut down the connection.
//            public void cancel() {
//                try {
//                    mmSocket.close();
//                } catch (IOException e) {
//                    Log.e(TAG, "Could not close the connect socket", e);
//                }
//            }
//
//        }
}
