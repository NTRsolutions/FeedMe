
package com.os.foodie.data.network.model.discount.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DiscountList implements Serializable
{

    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("dishes")
    @Expose
    private List<Dish> dishes = null;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("discount_percentage")
    @Expose
    private String discountPercentage;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;


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

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
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



}
