
package com.os.foodie.data.network.model.locationinfo.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserLocationDetailResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
