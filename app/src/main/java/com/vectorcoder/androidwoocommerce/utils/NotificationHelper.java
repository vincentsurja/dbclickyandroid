package com.vectorcoder.androidwoocommerce.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.media.RingtoneManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;

import java.util.Random;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;


/**
 * NotificationHelper is used to create new Notifications
 **/

public class NotificationHelper {
    
    
    public static final int NOTIFICATION_REQUEST_CODE = 100;
    private static String mChannelID = "woo_commerce";
    
    //*********** Used to create Notifications ********//
    
    public static void showNewNotification(Context context, String title, String msg) {
    
        
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        Intent notificationIntent;
    
        notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        
        String ADMIN_CHANNEL_ID = "vector_001";
    
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels(ADMIN_CHANNEL_ID,notificationManager);
        }
        int notificationId = new Random().nextInt(60000);
    
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)  //a resource for your custom small icon
                .setContentTitle(title) //the "title" value you sent in your notification
                .setContentText(msg) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(notificationSound)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultSoundUri);
    
      
    
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
        
    }
    
    
    

    private static void setupChannels(String ADMIN_CHANNEL_ID,NotificationManager notificationManager){
        CharSequence adminChannelName = "VectorCoder";
        String adminChannelDescription = "This is Notification Description";
      
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel adminChannel = notificationManager.getNotificationChannel(ADMIN_CHANNEL_ID);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (adminChannel == null) {
                adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
                adminChannel.setDescription(adminChannelDescription);
                adminChannel.enableLights(true);
                adminChannel.setLightColor(Color.RED);
                adminChannel.enableVibration(true);
                adminChannel.setSound(uri,attributes);
                notificationManager.createNotificationChannel(adminChannel);
    
            }
        }
       
    }
    
    public static void showNewNotification(Context context, Intent intent, String title, String msg) {
        
        
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        Intent notificationIntent;
    
    
        if (intent != null) {
            notificationIntent = intent;
        }
        else {
            notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        
        PendingIntent pendingIntent = PendingIntent.getActivity((context), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_ONE_SHOT);
        
        
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        
        Notification.Builder builder = new Notification.Builder(context);
        
        
        Log.i("VC_Shop", "showNewNotification_Title="+title);
        Log.i("VC_Shop", "showNewNotification_Body="+msg);
        
        // Create Notification
        Notification notification = builder
                .setContentTitle(title)
                .setContentText(msg)
                .setTicker(context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_logo)
                .setSound(notificationSound)
                .setLights(Color.RED, 3000, 3000)
                .setVibrate(new long[] { 1000, 1000 })
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setChannelId(mChannelID)
                .build();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("my_channel_01",
                    "Woocommerce",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
        
        mNotificationManager.notify(NOTIFICATION_REQUEST_CODE, notification);
        
    }
    
}

