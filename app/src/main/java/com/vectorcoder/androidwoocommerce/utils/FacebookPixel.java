package com.vectorcoder.androidwoocommerce.utils;

import android.os.Bundle;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Muhammad Nabeel on 18/03/2019.
 */
public class FacebookPixel {
    
    
    
    /**
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logSentFriendRequestEvent (AppEventsLogger logger) {
        logger.logEvent("sentFriendRequest");
    }
    
    public static void logAddedToCartEvent (AppEventsLogger logger,String contentData, String contentId, String contentType, String currency, double price) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, price, params);
    }
    
    public static void logCheckout(AppEventsLogger logger,String content_category,String content_ids, String contents, String currency, String num_items, double value){
    
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_PRODUCT_CATEGORY, content_category);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, content_ids);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contents);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        params.putString(AppEventsConstants.EVENT_PARAM_NUM_ITEMS,num_items);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, value, params);
    }
    
    public static void logPurchase(AppEventsLogger logger,String content_ids,String content, String currency,String num_items, double value){
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, content_ids);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, content);
        params.putString(AppEventsConstants.EVENT_PARAM_NUM_ITEMS,num_items);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, value, params);
    }
    
}
