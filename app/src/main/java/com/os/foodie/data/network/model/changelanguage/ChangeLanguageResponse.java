
package com.os.foodie.data.network.model.changelanguage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeLanguageResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ChangeLanguageResponse() {
    }

    /**
     * 
     * @param response
     */
    public ChangeLanguageResponse(Response response) {
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
