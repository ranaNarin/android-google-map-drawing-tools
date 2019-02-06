package com.service.drawingtoolmap;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;


/*
import com.pet_connect.Model.GPSTrackingPacket;
import com.pet_connect.coreUtills.UsersServiceImpl;
import com.pet_connect.http.HttpStatusCodeException;*/


/**
 * Created by narinder.rana on 4/20/2016.
 */
public class GPSTracker extends Service implements LocationListener {



    private final Context mContext;

    private final GPSTrackingPacket gpsTrackingPacket;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;



    public GPSTracker(Context context) {
        this.mContext = context;

         gpsTrackingPacket=new GPSTrackingPacket();


        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get Location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    @SuppressLint("MissingPermission")
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {


       /* if(null!=Location) {
            Log.e("onLocationChanged ::", "latitude:::" + Location.getLatitude() + "  Longitude:: " + Location.getLongitude());
            String userId = null;
           // Toast.makeText(mContext, "onLocationChanged  :"+Location.getLatitude()+" "+Location.getLongitude(), Toast.LENGTH_LONG).show();
        *//*
        *  {"MessageType":"0x10","UserID":1,"IMEI":"1","Longitude":77.25,"Latitude":28.5,"Altitude":"123",
        *  "Speed":0,"Heading":"1bc","GpsStatus":1,"Satellites":1,"TrackingDateTme":"\/Date(1464964874205)\/",
        *  "Temperature":2,"Battery":1,"CreatedOn":"\/Date(1464964873894)\/","IsMobileData":true}
        * *//*
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());

            SessionManager session= new SessionManager(mContext);
            if (session.checkLogin()) {

                HashMap<String, String> user = session.getUserDetails();
                // name
                String name = user.get(SessionManager.KEY_NAME);
                // email
                String email = user.get(SessionManager.KEY_EMAIL);
                 userId = user.get(SessionManager.KEY_USER_ID);
                String auth = user.get(SessionManager.KEY_AUTH);

                Log.e("Tag", "in Session" + name + " :: " + email + " :: " + userId + "  :: " + auth);

             //   users.setUserID(userId);
              //  users.setAccess_token("Bearer " + auth);

                //    new GetPetDetail().execute();

            }
            gpsTrackingPacket.setMessageType("0x10");
            if(null!=userId)
            {
                gpsTrackingPacket.setUserID(Long.valueOf(userId));
            }
            else {
                gpsTrackingPacket.setUserID(1);
            }

            gpsTrackingPacket.setIMEI("0007");
            gpsTrackingPacket.setLatitude(Location.getLatitude());
            gpsTrackingPacket.setLongitude(Location.getLongitude());
            gpsTrackingPacket.setAltitude(String.valueOf(Location.getAltitude()));
            gpsTrackingPacket.setSpeed(Location.getSpeed());
            gpsTrackingPacket.setHeading("Heading Test");
            gpsTrackingPacket.setGpsStatus(1);
            gpsTrackingPacket.setSatellites(1);
            gpsTrackingPacket.setTrackingDateTme(Location.getTime());
            gpsTrackingPacket.setTemperature(25);
            gpsTrackingPacket.setBattery(99);
            gpsTrackingPacket.setCreatedOn(Location.getTime());
            gpsTrackingPacket.setIsMobileData(true);

            new GpsBackgroundTask().execute();

        }*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.RED);
        }*/
       // byte[] cborDtat = encodeCBORData();

      //  AndroidJSWebView androidJSWebView= new AndroidJSWebView();


       // androidJSWebView.sendLlocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }



/*    private class GpsBackgroundTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
               new UsersServiceImpl().gpsBackGroundTask(gpsTrackingPacket);
            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }*/
}