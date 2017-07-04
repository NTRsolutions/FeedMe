
package com.os.foodie.data.network.model.merchantdetails.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMerchantDetailResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetMerchantDetailResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetMerchantDetailResponse(Response response) {
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
