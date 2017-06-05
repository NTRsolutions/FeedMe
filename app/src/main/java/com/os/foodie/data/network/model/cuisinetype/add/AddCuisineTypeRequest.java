
package com.os.foodie.data.network.model.cuisinetype.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCuisineTypeRequest {

    @SerializedName("cuisine_type")
    @Expose
    private String cuisineType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddCuisineTypeRequest() {
    }

    /**
     * 
     * @param cuisineType
     */
    public AddCuisineTypeRequest(String cuisineType) {
        super();
        this.cuisineType = cuisineType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

}
