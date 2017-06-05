
package com.os.foodie.data.network.model.menu.delete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteMenuItemRequest {

    @SerializedName("dish_id")
    @Expose
    private String dishId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeleteMenuItemRequest() {
    }

    /**
     * 
     * @param dishId
     */
    public DeleteMenuItemRequest(String dishId) {
        super();
        this.dishId = dishId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

}
