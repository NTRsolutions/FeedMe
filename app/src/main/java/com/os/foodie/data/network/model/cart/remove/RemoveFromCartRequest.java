
package com.os.foodie.data.network.model.cart.remove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveFromCartRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("dish_id")
    @Expose
    private String dishId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RemoveFromCartRequest() {
    }

    /**
     * 
     * @param userId
     * @param dishId
     */
    public RemoveFromCartRequest(String userId, String dishId) {
        super();
        this.userId = userId;
        this.dishId = dishId;
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

}
