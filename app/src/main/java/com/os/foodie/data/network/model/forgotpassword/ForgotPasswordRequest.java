package com.os.foodie.data.network.model.forgotpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequest {

    @SerializedName("email")
    @Expose
    private String email;

    /**
     * No args constructor for use in serialization
     */
    public ForgotPasswordRequest() {
    }

    /**
     * @param email
     */
    public ForgotPasswordRequest(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}