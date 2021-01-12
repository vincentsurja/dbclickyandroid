package com.vectorcoder.androidwoocommerce.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.models.GeoFencing.GeofencingList;
import com.vectorcoder.androidwoocommerce.utils.NotificationHelper;

import java.text.DecimalFormat;
import java.util.List;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;


/**
 * Created by Nabeel on 1/17/2018.
 */

public class UpdateLocation extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    //BGTask bgtask = new BGTask(this);

    public static String data;
    Intent intent;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    private static int UPDATE_INTERVAL = 2000;
    private static int FATEST_INTERVAL = 2000;
    private static int DISPLACEMENT = 0;
    SharedPreferences preferences;
   
   
    List<GeofencingList> geofencingLists;
    
  

    @Override
    public void onCreate() {
        super.onCreate();

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        intent = new Intent();
        geofencingLists = ((App) getApplicationContext()).getGeoFencingList();
    
        startServiceOreoCondition(getApplicationContext(),getString(R.string.app_name),
                getString(R.string.gps_enable_dialog));
        // This will add all the notification fired value to false. This is because notification fired every time
        // when location chaged function called
        for (int i=0;i<geofencingLists.size();i++){
            geofencingLists.get(i).setFired(false);
        }
        int size = geofencingLists.size();
       // preferences = getSharedPreferences(ConstantValues.NotificationMain,MODE_PRIVATE);
        
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        sendBroadcast(new Intent("force_not_destroy"));
        super.onDestroy();
    }


    private void displayLocation() {

        if (mLastLocation != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.#######");
            float latitude = Float.valueOf(decimalFormat.format(mLastLocation.getLatitude()));
            float longitude = Float.valueOf(decimalFormat.format(mLastLocation.getLongitude()));
            Log.e("Lat Long", ""+latitude+", "+longitude);
            
            for(int i=0;i<geofencingLists.size();i++){
                /*This distance will in kilometers*/
                float latitude2 = Float.valueOf(decimalFormat.format(geofencingLists.get(i).getLatitude()));
                float longitude2 = Float.valueOf(decimalFormat.format(geofencingLists.get(i).getLongitude()));
                
                Location locationA = new Location("PointA");
                locationA.setLatitude(latitude);
                locationA.setLongitude(longitude);
                Location locationB = new Location("PointB");
                locationB.setLatitude(latitude2);
                locationB.setLongitude(longitude2);
                
               double distsanceTwoPoints = meterDistanceBetweenPoints(latitude,longitude,latitude2,longitude2);
               
              // Toast.makeText(getApplicationContext(), ""+distsance, Toast.LENGTH_SHORT).show();
                Log.e("Distance", ""+distsanceTwoPoints+i);
               if(distsanceTwoPoints < Double.parseDouble(geofencingLists.get(i).getRadius())){
                   
                   if(!geofencingLists.get(i).isFired()) {
                       NotificationHelper.showNewNotification(getApplicationContext(), geofencingLists.get(i).getTitle(),
                               geofencingLists.get(i).getContent());
                       geofencingLists.get(i).setFired(true);
                   }
               /*    SharedPreferences.Editor editor = preferences.edit();
                   editor.putBoolean(ConstantValues.NOTIFICATION_FIRED,true);
                   editor.commit();*/
                   //Toast.makeText(getApplicationContext(), "Send notification", Toast.LENGTH_SHORT).show();
               }
               else if(distsanceTwoPoints > Double.parseDouble(geofencingLists.get(i).getRadius())){
                   geofencingLists.get(i).setFired(false);
               }
               
            }
            
        } else {
            
            Toast.makeText(getApplicationContext(), "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        //Log.i(TAG, "Connected yet? " + mGoogleApiClient.isConnecting() + " " + mGoogleApiClient.isConnected());
        mGoogleApiClient.connect();
        //Log.i(TAG, "Connected yet? " + mGoogleApiClient.isConnecting() + " " + mGoogleApiClient.isConnected());
    
    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }


    protected void startLocationUpdates() {
        createLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        

    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
     //   Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        Log.e(":Locationchange", "On Location change");
        // Displaying the new location on UI
        displayLocation();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    public void startServiceOreoCondition(Context context, String title, String msg){
        if (Build.VERSION.SDK_INT >= 26) {
            
            
            String CHANNEL_ID = "my_service";
            String CHANNEL_NAME = "My Background Service";
            
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            
            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE).setSmallIcon(R.mipmap.ic_launcher).setPriority(PRIORITY_MIN)
                    .setContentTitle(title) //the "title" value you sent in your notification
                    .setContentText(msg) //ditto
                    .build();
            
            startForeground(101, notification);
        }
    }
    
    private void fn_update(double lat,double lng){

        intent.putExtra("latutide",lat+"");
        intent.putExtra("longitude",lng+"");
        sendBroadcast(intent);
    }

    private double getDistanceByLatLong(double lat1, double lon1, double lat2, double lon2){
        double distance;
        LatLng latLngA = new LatLng(lat1,lon1);
        LatLng latLngB = new LatLng(lat2,lon2);
    
        Location locationA = new Location("point A");
        locationA.setLatitude(latLngA.latitude);
        locationA.setLongitude(latLngA.longitude);
        Location locationB = new Location("point B");
        locationB.setLatitude(latLngB.latitude);
        locationB.setLongitude(latLngB.longitude);
    
        distance = locationA.distanceTo(locationB)/1000;
        
        return distance;
    }
    
    double distance_between(Location l1, Location l2)
    {
        //float results[] = new float[1];
    /* Doesn't work. returns inconsistent results
    Location.distanceBetween(
            l1.getLatitude(),
            l1.getLongitude(),
            l2.getLatitude(),
            l2.getLongitude(),
            results);
            */
        double lat1=l1.getLatitude();
        double lon1=l1.getLongitude();
        double lat2=l2.getLatitude();
        double lon2=l2.getLongitude();
        double R = 1000; // km
        double dLat = (lat2-lat1)*Math.PI/180;
        double dLon = (lon2-lon1)*Math.PI/180;
        lat1 = lat1*Math.PI/180;
        lat2 = lat2*Math.PI/180;
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c ;
        
      /*  log_write("dist betn "+
                d + " " +
                l1.getLatitude()+ " " +
                l1.getLongitude() + " " +
                l2.getLatitude() + " " +
                l2.getLongitude()
        );*/
        
        return d;
    }
    
    private double meterDistanceBetweenPoints(float lat_a, float lng_a, float lat_b, float lng_b) {
        float pk = (float) (180.f/Math.PI);
        
        float a1 = lat_a / pk;
        float a2 = lng_a / pk;
        float b1 = lat_b / pk;
        float b2 = lng_b / pk;
        
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        
        return (6366000 * tt)/1000;
    }

}
