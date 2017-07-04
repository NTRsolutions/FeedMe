
package com.os.foodie.data.network.model.merchantdetails.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMerchantDetailRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetMerchantDetailRequest() {
    }

    /**
     * 
     * @param restaurantId
     */
    public GetMerchantDetailRequest(String restaurantId) {
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
