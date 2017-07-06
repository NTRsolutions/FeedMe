
package com.os.foodie.data.network.model.reorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReorderRequest {

    @SerializedName("order_id")
    @Expose
    private String orderId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReorderRequest() {
    }

    /**
     * 
     * @param orderId
     */
    public ReorderRequest(String orderId) {
        super();
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
