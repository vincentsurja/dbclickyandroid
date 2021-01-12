
package com.vectorcoder.androidwoocommerce.models.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WcPointsRewardsRedeemPointsRatio {

    @SerializedName("redeemPoint")
    @Expose
    private String redeemPoint;
    @SerializedName("equalTo")
    @Expose
    private String equalTo;

    public String getRedeemPoint() {
        return redeemPoint;
    }

    public void setRedeemPoint(String redeemPoint) {
        this.redeemPoint = redeemPoint;
    }

    public String getEqualTo() {
        return equalTo;
    }

    public void setEqualTo(String equalTo) {
        this.equalTo = equalTo;
    }

}
