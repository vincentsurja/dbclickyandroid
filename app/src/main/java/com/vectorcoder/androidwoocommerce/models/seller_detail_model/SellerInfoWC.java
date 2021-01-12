
package com.vectorcoder.androidwoocommerce.models.seller_detail_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellerInfoWC {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("caps")
    @Expose
    private Caps caps;
    @SerializedName("cap_key")
    @Expose
    private String capKey;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("allcaps")
    @Expose
    private Allcaps allcaps;
    @SerializedName("filter")
    @Expose
    private Object filter;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Caps getCaps() {
        return caps;
    }

    public void setCaps(Caps caps) {
        this.caps = caps;
    }

    public String getCapKey() {
        return capKey;
    }

    public void setCapKey(String capKey) {
        this.capKey = capKey;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Allcaps getAllcaps() {
        return allcaps;
    }

    public void setAllcaps(Allcaps allcaps) {
        this.allcaps = allcaps;
    }

    public Object getFilter() {
        return filter;
    }

    public void setFilter(Object filter) {
        this.filter = filter;
    }

}
