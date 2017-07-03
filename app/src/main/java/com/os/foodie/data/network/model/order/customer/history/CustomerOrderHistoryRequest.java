
package com.os.foodie.data.network.model.order.customer.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerOrderHistoryRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomerOrderHistoryRequest() {
    }

    /**
     * 
     * @param userId
     */
    public CustomerOrderHistoryRequest(String userId) {
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
