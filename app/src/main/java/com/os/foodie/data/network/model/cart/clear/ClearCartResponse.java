
package com.os.foodie.data.network.model.cart.clear;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClearCartResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClearCartResponse() {
    }

    /**
     * 
     * @param response
     */
    public ClearCartResponse(Response response) {
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
