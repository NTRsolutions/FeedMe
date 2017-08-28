
package com.os.foodie.data.network.model.citycountrylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityCountryListResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CityCountryListResponse() {
    }

    /**
     * 
     * @param response
     */
    public CityCountryListResponse(Response response) {
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
