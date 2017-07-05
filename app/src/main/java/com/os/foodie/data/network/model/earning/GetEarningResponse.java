
package com.os.foodie.data.network.model.earning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEarningResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetEarningResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetEarningResponse(Response response) {
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
