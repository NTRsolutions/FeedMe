
package com.os.foodie.data.network.model.cart.update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCartRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateCartRequest() {
    }

    /**
     * 
     * @param price
     * @param userId
     * @param qty
     * @param dishId
     * @param restaurantId
     */
    public UpdateCartRequest(String userId, String dishId, String restaurantId, String qty, String price) {
        super();
        this.userId = userId;
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.qty = qty;
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
