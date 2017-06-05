
package com.os.foodie.data.network.model.locationinfo.country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryListResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CountryListResponse() {
    }

    /**
     * 
     * @param response
     */
    public CountryListResponse(Response response) {
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
