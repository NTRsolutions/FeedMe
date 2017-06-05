
package com.os.foodie.data.network.model.signup.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantSignUpRequest {

    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("language")
    @Expose
    private String language;

    /**
     * No args constructor for use in serialization
     */
    public RestaurantSignUpRequest() {
    }

    /**
     * @param fbId
     * @param email
     * @param deviceType
     * @param longitude
     * @param language
     * @param latitude
     * @param contactPersonName
     * @param mobileNumber
     * @param password
     * @param deviceId
     * @param restaurantName
     */
    public RestaurantSignUpRequest(String fbId, String contactPersonName, String restaurantName, String email, String password, String mobileNumber, String deviceId, String deviceType, String latitude, String longitude, String language) {
        super();
        this.fbId = fbId;
        this.contactPersonName = contactPersonName;
        this.restaurantName = restaurantName;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.language = language;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
