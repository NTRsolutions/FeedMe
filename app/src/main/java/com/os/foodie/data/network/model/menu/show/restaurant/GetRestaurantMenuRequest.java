
package com.os.foodie.data.network.model.menu.show.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRestaurantMenuRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    /**
     * No args constructor for use in serialization
     */
    public GetRestaurantMenuRequest() {
    }

    /**
     * @param restaurantId
     */
    public GetRestaurantMenuRequest(String restaurantId) {
        super();
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

}
