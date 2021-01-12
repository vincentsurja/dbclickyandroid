
package com.vectorcoder.androidwoocommerce.models.points;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<PointsList> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PointsList> getData() {
        return data;
    }

    public void setData(List<PointsList> data) {
        this.data = data;
    }

}
