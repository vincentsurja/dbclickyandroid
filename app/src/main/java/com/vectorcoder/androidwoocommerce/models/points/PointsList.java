
package com.vectorcoder.androidwoocommerce.models.points;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("type")
    @Expose
    private String type;
 /*   @SerializedName("user_points_id")
    @Expose
    private String userPointsId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("admin_user_id")
    @Expose
    private Object adminUserId;*/
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   /* public String getUserPointsId() {
        return userPointsId;
    }

    public void setUserPointsId(String userPointsId) {
        this.userPointsId = userPointsId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Object getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Object adminUserId) {
        this.adminUserId = adminUserId;
    }
*/
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
