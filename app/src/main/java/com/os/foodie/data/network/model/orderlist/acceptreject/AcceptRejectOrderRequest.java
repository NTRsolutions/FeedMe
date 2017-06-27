
package com.os.foodie.data.network.model.orderlist.acceptreject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptRejectOrderRequest {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AcceptRejectOrderRequest() {
    }

    /**
     * 
     * @param orderStatus
     * @param orderId
     */
    public AcceptRejectOrderRequest(String orderId, String orderStatus) {
        super();
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
