
package com.os.foodie.data.network.model.cart.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCartListRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetCartListRequest() {
    }

    /**
     * 
     * @param userId
     */
    public GetCartListRequest(String userId) {
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
