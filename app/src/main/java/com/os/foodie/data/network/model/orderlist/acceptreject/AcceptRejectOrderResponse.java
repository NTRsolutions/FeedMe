
package com.os.foodie.data.network.model.orderlist.acceptreject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptRejectOrderResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AcceptRejectOrderResponse() {
    }

    /**
     * 
     * @param response
     */
    public AcceptRejectOrderResponse(Response response) {
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
