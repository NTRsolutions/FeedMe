
package com.os.foodie.data.network.model.menu.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMenuItemResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddMenuItemResponse() {
    }

    /**
     * 
     * @param response
     */
    public AddMenuItemResponse(Response response) {
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
