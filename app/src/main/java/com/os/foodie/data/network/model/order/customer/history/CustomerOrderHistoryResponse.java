
package com.os.foodie.data.network.model.order.customer.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerOrderHistoryResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomerOrderHistoryResponse() {
    }

    /**
     * 
     * @param response
     */
    public CustomerOrderHistoryResponse(Response response) {
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
