package com.os.foodie.data.network.model.discount.add;

/**
 * Created by abhinava on 6/27/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDiscountRequest {

    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("discount_percentage")
    @Expose
    private String discountPercentage;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

}
