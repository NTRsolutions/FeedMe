
package com.os.foodie.data.network.model.setupprofile.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetupRestaurantProfileResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SetupRestaurantProfileResponse() {
    }

    /**
     * 
     * @param response
     */
    public SetupRestaurantProfileResponse(Response response) {
        super();
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
