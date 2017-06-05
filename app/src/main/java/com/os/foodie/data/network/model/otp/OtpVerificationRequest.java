package com.os.foodie.data.network.model.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerificationRequest {

    @SerializedName("otp")
    @Expose
    private String otp;

    /**
     * No args constructor for use in serialization
     */
    public OtpVerificationRequest() {
    }

    /**
     * @param otp
     */
    public OtpVerificationRequest(String otp) {
        super();
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
