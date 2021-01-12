
package com.vectorcoder.androidwoocommerce.models.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointConversionModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private PointsData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PointsData getData() {
        return data;
    }

    public void setData(PointsData data) {
        this.data = data;
    }

}
