
package com.os.foodie.data.network.model.cart.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCartListResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetCartListResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetCartListResponse(Response response) {
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
