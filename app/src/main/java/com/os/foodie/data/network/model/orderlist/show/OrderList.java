
package com.os.foodie.data.network.model.orderlist.show;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderList {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    //    TODO Amount Changed
//    @SerializedName("total_amount")
    @SerializedName("order_amount")
    @Expose
    private String totalAmount;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("discount")
    @Expose
    private Float discount;
    //    TODO Extra Field Added
    @SerializedName("total_discount")
    @Expose
    private Float totalDiscount;
    @SerializedName("order_delievery_date")
    @Expose
    private String orderDelieveryDate;
    @SerializedName("order_delievery_time")
    @Expose
    private String orderDelieveryTime;
    @SerializedName("dish_name")
    @Expose
    private String dishName;

    /**
     * No args constructor for use in serialization
     */
    public OrderList() {
    }

    /**
     * @param dishName
     * @param orderType
     * @param totalAmount
     * @param paymentStatus
     * @param orderDelieveryTime
     * @param orderStatus
     * @param orderDelieveryDate
     * @param orderId
     * @param currency
     * @param discount
     * @param totalDiscount
     */
    public OrderList(String orderId, String totalAmount, String orderStatus, Float totalDiscount, String currency, String paymentStatus, String orderType, Float discount, String orderDelieveryDate, String orderDelieveryTime, String dishName) {
        super();
        this.orderId = orderId;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.orderType = orderType;
        this.discount = discount;
        this.totalDiscount = totalDiscount;
        this.orderDelieveryDate = orderDelieveryDate;
        this.orderDelieveryTime = orderDelieveryTime;
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

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getOrderDelieveryDate() {
        return orderDelieveryDate;
    }

    public void setOrderDelieveryDate(String orderDelieveryDate) {
        this.orderDelieveryDate = orderDelieveryDate;
    }

    public String getOrderDelieveryTime() {
        return orderDelieveryTime;
    }

    public void setOrderDelieveryTime(String orderDelieveryTime) {
        this.orderDelieveryTime = orderDelieveryTime;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Float totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
}
