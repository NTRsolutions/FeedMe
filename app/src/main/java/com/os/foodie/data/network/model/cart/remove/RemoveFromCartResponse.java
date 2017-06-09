
package com.os.foodie.data.network.model.cart.remove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveFromCartResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RemoveFromCartResponse() {
    }

    /**
     * 
     * @param response
     */
    public RemoveFromCartResponse(Response response) {
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
