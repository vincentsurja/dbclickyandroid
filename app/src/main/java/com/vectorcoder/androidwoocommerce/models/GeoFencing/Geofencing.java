package com.vectorcoder.androidwoocommerce.models.GeoFencing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Nabeel on 12/04/2019.
 */
public class Geofencing {
   
    @SerializedName("status")
    @Expose
    private String title;
    @SerializedName("data")
    @Expose
    private List<GeofencingList> data;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<GeofencingList> getData() {
        return data;
    }
    
    public void setData(List<GeofencingList> data) {
        this.data = data;
    }
    
}
