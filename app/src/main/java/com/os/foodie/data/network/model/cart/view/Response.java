
package com.os.foodie.data.network.model.cart.view;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("cart_list")
    @Expose
    private List<CartList> cartList = null;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
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
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param message
     * @param cartCount
     * @param deliveryTime
     * @param status
     * @param deliveryType
     * @param cartList
     * @param closingTime
     * @param minOrderAmount
     * @param openingTime
     * @param deliveryCharge
     * @param restaurantName
     * @param restaurantId
     */
    public Response(List<CartList> cartList, String restaurantId, String restaurantName, String openingTime, String closingTime, String deliveryTime, String deliveryType, String deliveryCharge, String minOrderAmount, Integer cartCount, Integer status, String message) {
        super();
        this.cartList = cartList;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
        this.deliveryCharge = deliveryCharge;
        this.minOrderAmount = minOrderAmount;
        this.cartCount = cartCount;
        this.status = status;
        this.message = message;
    }

    public List<CartList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartList> cartList) {
        this.cartList = cartList;
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

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
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

}
