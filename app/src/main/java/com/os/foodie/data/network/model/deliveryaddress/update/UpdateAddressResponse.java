
package com.os.foodie.data.network.model.deliveryaddress.update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAddressResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateAddressResponse() {
    }

    /**
     * 
     * @param response
     */
    public UpdateAddressResponse(Response response) {
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
