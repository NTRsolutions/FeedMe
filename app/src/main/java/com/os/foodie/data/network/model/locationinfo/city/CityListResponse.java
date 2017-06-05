
package com.os.foodie.data.network.model.locationinfo.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CityListResponse() {
    }

    /**
     * 
     * @param response
     */
    public CityListResponse(Response response) {
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
