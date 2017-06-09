
package com.os.foodie.data.network.model.cart.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewCartRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ViewCartRequest() {
    }

    /**
     * 
     * @param userId
     */
    public ViewCartRequest(String userId) {
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
