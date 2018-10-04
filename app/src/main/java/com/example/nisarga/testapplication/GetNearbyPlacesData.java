package com.example.nisarga.testapplication;

import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    public static int minDurIndex;
    public static String shortestDurationJson;

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList =  dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
        DurationParser durationParser=new DurationParser();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+919482909698",
                null,
                "Emergency for Nisarga. The nearest hospital is " +durationParser.parse(GetNearbyPlacesData.shortestDurationJson)+" seconds away",
                null,
                null);
        Toast.makeText(MainActivity.context, "SMS sent.",
                Toast.LENGTH_LONG).show();


    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        List<String> DistanceListJSon=new ArrayList<>();
        String distanceJson=null;
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            DownloadUrl myDownloader = new DownloadUrl();
            try {
                distanceJson = myDownloader.readUrl("https://maps.googleapis.com/maps/api/directions/json?origin=" + MainActivity.latitude + "," + MainActivity.longitude + "&destination=" + lat + "," + lng + "&mode=driving&key=AIzaSyCLINnXRrKn289kY9mZcNInHEWXF8V4pVE");
            }catch (Exception e)
            {
                Log.d("GooglePlacesReadTask", e.toString());
            }
            Log.d("MyTag",distanceJson+"");
            DistanceListJSon.add(distanceJson);
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }

        DurationParser durationParser =new DurationParser();
        List<Integer> durationList=new ArrayList<>();
        Log.i("MyTag","The size of distanceListJson is "+DistanceListJSon.size());
        for(int i=0;i<DistanceListJSon.size();i++)
        {
            int duration= durationParser.parse(DistanceListJSon.get(i));
            durationList.add(duration);
            Log.i("MyTag","the duration for  "+i+" is "+duration);
        }

        int minimum=999999;
        int minIndex=-1;
        for(int i=0;i<durationList.size();i++)
        {
            if(durationList.get(i)<minimum)
            {
                minimum=durationList.get(i);
                minIndex=i;
            }


        }

        shortestDurationJson=DistanceListJSon.get(minIndex);


    }
}