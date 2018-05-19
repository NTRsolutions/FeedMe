
package com.os.foodie.data.network.model.locationinfo.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserLocationDetailRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    public GetUserLocationDetailRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
