
package com.os.foodie.data.network.model.order.customer.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderList {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("dish_name")
    @Expose
    private String dishName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OrderList() {
    }

    /**
     * 
     * @param dishName
     * @param orderType
     * @param totalAmount
     * @param paymentStatus
     * @param orderStatus
     * @param orderId
     * @param discount
     */
    public OrderList(String orderId, String totalAmount, String orderStatus, String paymentStatus, String orderType, Integer discount, String dishName) {
        super();
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.orderType = orderType;
        this.discount = discount;
        this.dishName = dishName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

}
