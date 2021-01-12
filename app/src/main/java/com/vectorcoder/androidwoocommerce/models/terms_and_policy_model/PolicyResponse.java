package com.vectorcoder.androidwoocommerce.models.terms_and_policy_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PolicyResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("content")
    @Expose
    private PolicyContent content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PolicyContent getContent() {
        return content;
    }

    public void setContent(PolicyContent content) {
        this.content = content;
    }

}
