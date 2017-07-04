
package com.os.foodie.data.network.model.merchantdetails.set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetMerchantDetailResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SetMerchantDetailResponse() {
    }

    /**
     * 
     * @param response
     */
    public SetMerchantDetailResponse(Response response) {
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
