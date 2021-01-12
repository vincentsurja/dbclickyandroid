
package com.vectorcoder.androidwoocommerce.models.currency_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyList {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("is_etalon")
    @Expose
    private Integer isEtalon;
    @SerializedName("hide_cents")
    @Expose
    private Integer hideCents;
    @SerializedName("decimals")
    @Expose
    private Integer decimals;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("flag")
    @Expose
    private String flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getIsEtalon() {
        return isEtalon;
    }

    public void setIsEtalon(Integer isEtalon) {
        this.isEtalon = isEtalon;
    }

    public Integer getHideCents() {
        return hideCents;
    }

    public void setHideCents(Integer hideCents) {
        this.hideCents = hideCents;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
