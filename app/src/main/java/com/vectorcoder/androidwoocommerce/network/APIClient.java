package com.vectorcoder.androidwoocommerce.network;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.oauth.BasicOAuth;
import com.vectorcoder.androidwoocommerce.oauth.OAuthInterceptor;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import android.content.ContextWrapper;


import okhttp3.Cache;
import okhttp3.Interceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * APIClient handles all the Network API Requests using Retrofit Library
 **/

public class APIClient {
    
    public static Retrofit retrofit;
    private static APIRequests apiRequests;
    private static final String BASE_URL = ConstantValues.WOOCOMMERCE_URL;
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    
    // variable to hold context
    private static Context context;

//save the context recievied via constructor in a local variable
    
    public APIClient(Context context){
        this.context=context;
    }
    
    
    // Singleton Instance of APIRequests
    public static APIRequests getInstance() {
        if (apiRequests == null) {
            
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            OAuthInterceptor oauth1Woocommerce = new OAuthInterceptor.Builder()
                    .consumerKey(ConstantValues.WOOCOMMERCE_CONSUMER_KEY)
                    .consumerSecret(ConstantValues.WOOCOMMERCE_CONSUMER_SECRET)
                    .build();
            
            BasicOAuth basicOAuthWoocommerce = new BasicOAuth.Builder()
                    .consumerKey(ConstantValues.WOOCOMMERCE_CONSUMER_KEY)
                    .consumerSecret(ConstantValues.WOOCOMMERCE_CONSUMER_SECRET)
                    .build();
    
            Cache cache = new Cache(App.getContext().getCacheDir(), cacheSize);
    
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain)
                                throws IOException {
                            Request request = chain.request();
                            if (!Utilities.isNetworkAvailable(App.getContext())) {
                                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
                                request = request
                                        .newBuilder()
                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                        .build();
                            }
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .addNetworkInterceptor(interceptor)
                    .addInterceptor(BASE_URL.startsWith("http://")?  oauth1Woocommerce : basicOAuthWoocommerce)
                    .build();
            
        /*    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(BASE_URL.startsWith("http://")?  oauth1Woocommerce : basicOAuthWoocommerce)
                    .build();*/
            
    
        /*    RestAdapter restAdapter = new RestAdapter.Builder()
                    .setErrorHandler(new ErrorRetrofitHandlerException())
                    .setEndpoint(urlBase)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(new OkHttpClient()))
                    .build();*/
        
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            
            apiRequests = retrofit.create(APIRequests.class);
            
            return apiRequests;
            
        }
        else {
            return apiRequests;
        }
    }
    
    // Singleton Instance of APIRequests
   /* public APIRequests getInstance(final Context context) {
        if (apiRequests == null) {
            
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            OAuthInterceptor oauth1Woocommerce = new OAuthInterceptor.Builder()
                    .consumerKey(ConstantValues.WOOCOMMERCE_CONSUMER_KEY)
                    .consumerSecret(ConstantValues.WOOCOMMERCE_CONSUMER_SECRET)
                    .build();
            
            BasicOAuth basicOAuthWoocommerce = new BasicOAuth.Builder()
                    .consumerKey(ConstantValues.WOOCOMMERCE_CONSUMER_KEY)
                    .consumerSecret(ConstantValues.WOOCOMMERCE_CONSUMER_SECRET)
                    .build();
            
            Cache cache = new Cache(context.getCacheDir(), cacheSize);
            
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain)
                                throws IOException {
                            Request request = chain.request();
                            if (!Utilities.isNetworkAvailable(context)) {
                                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
                                request = request
                                        .newBuilder()
                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                        .build();
                            }
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(BASE_URL.startsWith("http://")?  oauth1Woocommerce : basicOAuthWoocommerce)
                    .build();
            
           *//* OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(BASE_URL.startsWith("http://")?  oauth1Woocommerce : basicOAuthWoocommerce)
                    .build();*//*
            
    
        *//*    RestAdapter restAdapter = new RestAdapter.Builder()
                    .setErrorHandler(new ErrorRetrofitHandlerException())
                    .setEndpoint(urlBase)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(new OkHttpClient()))
                    .build();*//*
            
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            
            apiRequests = retrofit.create(APIRequests.class);
            
            return apiRequests;
            
        }
        else {
            return apiRequests;
        }
    }*/
    
    private static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (Utilities.isNetworkAvailable(context)) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    
}
