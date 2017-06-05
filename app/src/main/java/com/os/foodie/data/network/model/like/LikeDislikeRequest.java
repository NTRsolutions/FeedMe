
package com.os.foodie.data.network.model.like;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeDislikeRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LikeDislikeRequest() {
    }

    /**
     * 
     * @param userId
     * @param restaurantId
     */
    public LikeDislikeRequest(String userId, String restaurantId) {
        super();
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

}
