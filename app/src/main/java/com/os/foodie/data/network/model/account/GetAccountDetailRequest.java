package com.os.foodie.data.network.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.R.attr.description;
import static android.R.attr.name;

/**
 * Created by monikab on 6/2/2017.
 */

public class GetAccountDetailRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;


    public GetAccountDetailRequest(String userId) {
        super();
        this.userId = userId;

    }





    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
