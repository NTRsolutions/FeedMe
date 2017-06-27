
package com.os.foodie.data.network.model.orderlist.show;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderListRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetOrderListRequest() {
    }

    /**
     * 
     * @param restaurantId
     */
    public GetOrderListRequest(String restaurantId) {
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
