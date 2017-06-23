
package com.os.foodie.data.network.model.payment.delete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeletePaymentCardResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeletePaymentCardResponse() {
    }

    /**
     * 
     * @param response
     */
    public DeletePaymentCardResponse(Response response) {
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
