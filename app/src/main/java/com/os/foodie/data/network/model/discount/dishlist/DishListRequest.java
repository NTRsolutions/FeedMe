package com.os.foodie.data.network.model.discount.dishlist;

/**
 * Created by abhinava on 6/27/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DishListRequest
{

    public DishListRequest(String restaurant_id) {
        super();
        this.restaurantId = restaurant_id;
    }

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

}