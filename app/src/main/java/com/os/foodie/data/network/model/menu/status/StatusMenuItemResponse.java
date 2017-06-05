
package com.os.foodie.data.network.model.menu.status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusMenuItemResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StatusMenuItemResponse() {
    }

    /**
     * 
     * @param response
     */
    public StatusMenuItemResponse(Response response) {
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
