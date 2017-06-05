
package com.os.foodie.data.network.model.fblogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookLoginRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;

    /**
     * No args constructor for use in serialization
     */
    public FacebookLoginRequest() {
    }

    /**
     * @param fbId
     * @param email
     * @param deviceType
     * @param deviceId
     */
    public FacebookLoginRequest(String email, String fbId, String deviceId, String deviceType) {
        super();
        this.email = email;
        this.fbId = fbId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

}
