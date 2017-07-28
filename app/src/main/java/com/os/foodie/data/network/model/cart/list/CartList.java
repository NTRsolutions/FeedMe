
package com.os.foodie.data.network.model.cart.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartList {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("logo")
    @Expose
    private String logo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CartList() {
    }

    /**
     * 
     * @param logo
     * @param restaurantName
     * @param restaurantId
     */
    public CartList(String restaurantId, String restaurantName, String logo) {
        super();
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.logo = logo;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
