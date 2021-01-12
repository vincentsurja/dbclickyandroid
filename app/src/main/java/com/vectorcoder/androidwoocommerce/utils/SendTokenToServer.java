package com.vectorcoder.androidwoocommerce.utils;

import android.util.Log;

import com.vectorcoder.androidwoocommerce.network.APIClient;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muhammad Nabeel on 04/04/2019.
 */
public class SendTokenToServer {
    
    
    public SendTokenToServer(String token,String user_id){
        
        RequestSendTokenToServer(token,user_id);
    }
    
    private void RequestSendTokenToServer(String token,String user_id){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("device_id", String.valueOf(token));
        if(user_id!=null && !user_id.isEmpty()) {
            params.put("user_id", String.valueOf(user_id));
        }
    
        Call<String> call = APIClient.getInstance().sendDeviceToken(params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String url = response.raw().request().url().toString();
                if (response.isSuccessful()){
                    Log.e("Notification","Device Registered");
                }
                else {
                    Log.e("Notification","Device Not Registered");
                }
        
            }
    
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Notification",t.toString());
            }
        });
    }
}
