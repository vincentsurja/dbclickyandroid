
package com.vectorcoder.androidwoocommerce.models.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WcPointsRewardsEarnPointsRatio {

    @SerializedName("earnPoint")
    @Expose
    private String earnPoint;
    @SerializedName("equalTo")
    @Expose
    private String equalTo;

    public String getEarnPoint() {
        return earnPoint;
    }

    public void setEarnPoint(String earnPoint) {
        this.earnPoint = earnPoint;
    }

    public String getEqualTo() {
        return equalTo;
    }

    public void setEqualTo(String equalTo) {
        this.equalTo = equalTo;
    }

}
