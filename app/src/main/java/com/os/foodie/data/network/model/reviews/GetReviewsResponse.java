
package com.os.foodie.data.network.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReviewsResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetReviewsResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetReviewsResponse(Response response) {
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
