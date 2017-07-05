
package com.os.foodie.data.network.model.earning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEarningRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetEarningRequest() {
    }

    /**
     * 
     * @param restaurantId
     */
    public GetEarningRequest(String restaurantId) {
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
