
package com.os.foodie.data.network.model.deliveryaddress.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDeliveryAddressResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddDeliveryAddressResponse() {
    }

    /**
     * 
     * @param response
     */
    public AddDeliveryAddressResponse(Response response) {
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