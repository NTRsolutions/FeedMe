
package com.os.foodie.data.network.model.cart.view;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("cart_list")
    @Expose
    private List<CartList> cartList = null;
    @SerializedName("min_order_discounts")
    @Expose
    private List<MinOrderDiscount> minOrderDiscounts = null;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("working_days")
    @Expose
    private String workingDays;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param message
     * @param minOrderDiscounts
     * @param deliveryTime
     * @param status
     * @param deliveryType
     * @param paymentType
     * @param cartList
     * @param closingTime
     * @param workingDays
     * @param minOrderAmount
     * @param openingTime
     * @param deliveryCharge
     * @param restaurantName
     * @param currency
     * @param restaurantId
     */
    public Response(List<CartList> cartList, List<MinOrderDiscount> minOrderDiscounts, String restaurantId, String restaurantName, String currency, String workingDays, String openingTime, String closingTime, String deliveryTime, String deliveryType, String paymentType, String deliveryCharge, String minOrderAmount, Integer status, String message) {
        super();
        this.cartList = cartList;
        this.minOrderDiscounts = minOrderDiscounts;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.currency = currency;
        this.workingDays = workingDays;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
        this.paymentType = paymentType;
        this.deliveryCharge = deliveryCharge;
        this.minOrderAmount = minOrderAmount;
        this.status = status;
        this.message = message;
    }

    public List<CartList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartList> cartList) {
        this.cartList = cartList;
    }

    public List<MinOrderDiscount> getMinOrderDiscounts() {
        return minOrderDiscounts;
    }

    public void setMinOrderDiscounts(List<MinOrderDiscount> minOrderDiscounts) {
        this.minOrderDiscounts = minOrderDiscounts;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
