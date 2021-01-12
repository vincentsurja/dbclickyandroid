package com.vectorcoder.androidwoocommerce.utils;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;

import com.google.gson.Gson;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;


import com.vectorcoder.androidwoocommerce.constant.ConstantValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Muhammad Nabeel on 09/04/2019.
 */
public class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
    
    Context context2;
    
    public static String Notification_FLAG = "0";
    
    
    public ExampleNotificationOpenedHandler(Context context) {
        context2 = context;
    }
    
    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        
        OSNotificationAction.ActionType actionType = result.action.type;
        String data;
        String type = null;
        
        data = result.notification.payload.title;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new Gson().toJson(result.notification));
            type = jsonObject.optString("displayType");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
    
        if (type.equalsIgnoreCase("InAppAlert")) {
            Intent intent = new Intent(ConstantValues.NOTIFICATION_MAIN);
            intent.putExtra(ConstantValues.NOTIFICATION_FLAG, "1");
            // put your all data using put extra
        
            LocalBroadcastManager.getInstance(context2).sendBroadcast(intent);
        } else {
        
            Notification_FLAG = "1";
               /* Intent intent = new Intent(ConstantValues.NOTIFICATION_MAIN);
                intent.putExtra(ConstantValues.NOTIFICATION_FLAG, "1");
                // put your all data using put extra
    
                LocalBroadcastManager.getInstance(context2).sendBroadcast(intent);*/
        
            }
        
            if (data != null) {
                if (type != null)
                    Log.e("OneSignalExample", "customkey set with value: " + type);
            }
        
            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
            
            
            }
        }
    
    }

