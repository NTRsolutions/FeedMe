
package com.os.foodie.data.network.model.deliveryaddress.getall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllAddressResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAllAddressResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetAllAddressResponse(Response response) {
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
