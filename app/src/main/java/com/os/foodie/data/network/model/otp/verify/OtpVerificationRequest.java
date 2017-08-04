package com.os.foodie.data.network.model.otp.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerificationRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("otp")
    @Expose
    private String otp;

    /**
     * No args constructor for use in serialization
     */
    public OtpVerificationRequest() {
    }

    /**
     * @param userId
     * @param otp
     */
    public OtpVerificationRequest(String userId, String otp) {
        super();
        this.userId = userId;
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}