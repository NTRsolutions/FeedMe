
package com.os.foodie.data.network.model.cart.update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCartResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateCartResponse() {
    }

    /**
     * 
     * @param response
     */
    public UpdateCartResponse(Response response) {
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
