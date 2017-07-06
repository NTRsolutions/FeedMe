
package com.os.foodie.data.network.model.reorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReorderResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReorderResponse() {
    }

    /**
     * 
     * @param response
     */
    public ReorderResponse(Response response) {
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
