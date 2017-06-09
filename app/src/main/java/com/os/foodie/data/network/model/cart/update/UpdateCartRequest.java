
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
    @SerializedName("qty")
    @Expose
    private String qty;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateCartRequest() {
    }

    /**
     * 
     * @param userId
     * @param qty
     * @param dishId
     */
    public UpdateCartRequest(String userId, String dishId, String qty) {
        super();
        this.userId = userId;
        this.dishId = dishId;
        this.qty = qty;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
