
package com.os.foodie.data.network.model.menu.status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusMenuItemRequest {

    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StatusMenuItemRequest() {
    }

    /**
     * 
     * @param status
     * @param dishId
     */
    public StatusMenuItemRequest(String dishId, String status) {
        super();
        this.dishId = dishId;
        this.status = status;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
