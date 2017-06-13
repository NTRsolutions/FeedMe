
package com.os.foodie.data.network.model.cart.clear;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClearCartRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClearCartRequest() {
    }

    /**
     * 
     * @param userId
     */
    public ClearCartRequest(String userId) {
        super();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
