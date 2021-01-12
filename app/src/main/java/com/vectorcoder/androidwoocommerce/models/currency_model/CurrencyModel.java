
package com.vectorcoder.androidwoocommerce.models.currency_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CurrencyList> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CurrencyList> getData() {
        return data;
    }

    public void setData(List<CurrencyList> data) {
        this.data = data;
    }

}
