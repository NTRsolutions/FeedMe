
package com.os.foodie.data.network.model.payment.getall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllPaymentCardRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAllPaymentCardRequest() {
    }

    /**
     * 
     * @param userId
     */
    public GetAllPaymentCardRequest(String userId) {
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
