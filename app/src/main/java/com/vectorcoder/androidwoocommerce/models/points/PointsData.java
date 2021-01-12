
package com.vectorcoder.androidwoocommerce.models.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsData {

    @SerializedName("wc_points_rewards_earn_points_ratio")
    @Expose
    private WcPointsRewardsEarnPointsRatio wcPointsRewardsEarnPointsRatio;
    @SerializedName("wc_points_rewards_redeem_points_ratio")
    @Expose
    private WcPointsRewardsRedeemPointsRatio wcPointsRewardsRedeemPointsRatio;
    @SerializedName("wc_points_rewards_cart_max_discount")
    @Expose
    private String wcPointsRewardsCartMaxDiscount;
    @SerializedName("wc_points_rewards_max_discount")
    @Expose
    private String wcPointsRewardsMaxDiscount;
    @SerializedName("wc_points_rewards_points_expiry")
    @Expose
    private String wcPointsRewardsPointsExpiry;
    @SerializedName("wc_points_rewards_account_signup_points")
    @Expose
    private String wcPointsRewardsAccountSignupPoints;
    @SerializedName("wc_points_rewards_write_review_points")
    @Expose
    private String wcPointsRewardsWriteReviewPoints;

    public WcPointsRewardsEarnPointsRatio getWcPointsRewardsEarnPointsRatio() {
        return wcPointsRewardsEarnPointsRatio;
    }

    public void setWcPointsRewardsEarnPointsRatio(WcPointsRewardsEarnPointsRatio wcPointsRewardsEarnPointsRatio) {
        this.wcPointsRewardsEarnPointsRatio = wcPointsRewardsEarnPointsRatio;
    }

    public WcPointsRewardsRedeemPointsRatio getWcPointsRewardsRedeemPointsRatio() {
        return wcPointsRewardsRedeemPointsRatio;
    }

    public void setWcPointsRewardsRedeemPointsRatio(WcPointsRewardsRedeemPointsRatio wcPointsRewardsRedeemPointsRatio) {
        this.wcPointsRewardsRedeemPointsRatio = wcPointsRewardsRedeemPointsRatio;
    }

    public String getWcPointsRewardsCartMaxDiscount() {
        return wcPointsRewardsCartMaxDiscount;
    }

    public void setWcPointsRewardsCartMaxDiscount(String wcPointsRewardsCartMaxDiscount) {
        this.wcPointsRewardsCartMaxDiscount = wcPointsRewardsCartMaxDiscount;
    }

    public String getWcPointsRewardsMaxDiscount() {
        return wcPointsRewardsMaxDiscount;
    }

    public void setWcPointsRewardsMaxDiscount(String wcPointsRewardsMaxDiscount) {
        this.wcPointsRewardsMaxDiscount = wcPointsRewardsMaxDiscount;
    }

    public String getWcPointsRewardsPointsExpiry() {
        return wcPointsRewardsPointsExpiry;
    }

    public void setWcPointsRewardsPointsExpiry(String wcPointsRewardsPointsExpiry) {
        this.wcPointsRewardsPointsExpiry = wcPointsRewardsPointsExpiry;
    }

    public String getWcPointsRewardsAccountSignupPoints() {
        return wcPointsRewardsAccountSignupPoints;
    }

    public void setWcPointsRewardsAccountSignupPoints(String wcPointsRewardsAccountSignupPoints) {
        this.wcPointsRewardsAccountSignupPoints = wcPointsRewardsAccountSignupPoints;
    }

    public String getWcPointsRewardsWriteReviewPoints() {
        return wcPointsRewardsWriteReviewPoints;
    }

    public void setWcPointsRewardsWriteReviewPoints(String wcPointsRewardsWriteReviewPoints) {
        this.wcPointsRewardsWriteReviewPoints = wcPointsRewardsWriteReviewPoints;
    }

}
