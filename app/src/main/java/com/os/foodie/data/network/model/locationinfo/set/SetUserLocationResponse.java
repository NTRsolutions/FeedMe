
package com.os.foodie.data.network.model.locationinfo.set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetUserLocationResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SetUserLocationResponse() {
    }

    /**
     * 
     * @param response
     */
    public SetUserLocationResponse(Response response) {
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
