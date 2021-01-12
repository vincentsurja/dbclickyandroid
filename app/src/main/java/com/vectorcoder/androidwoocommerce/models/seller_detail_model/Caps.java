
package com.vectorcoder.androidwoocommerce.models.seller_detail_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Caps {

    @SerializedName("seller")
    @Expose
    private Boolean seller;
    @SerializedName("vendor")
    @Expose
    private Boolean vendor;

    public Boolean getSeller() {
        return seller;
    }

    public void setSeller(Boolean seller) {
        this.seller = seller;
    }

    public Boolean getVendor() {
        return vendor;
    }

    public void setVendor(Boolean vendor) {
        this.vendor = vendor;
    }

}
