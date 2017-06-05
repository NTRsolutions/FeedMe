
package com.os.foodie.data.network.model.cuisinetype.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCuisineTypeResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddCuisineTypeResponse() {
    }

    /**
     * 
     * @param response
     */
    public AddCuisineTypeResponse(Response response) {
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
