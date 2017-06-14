
package com.os.foodie.data.network.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_type")
    @Expose
    private String userType;
//    TODO Set Profile
    @SerializedName("is_profile_set")
    @Expose
    private String isProfileSet;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }


    /**
     * @param message
     * @param lastName
     * @param profileImage
     * @param status
     * @param email
     * @param deviceType
     * @param userId
     * @param firstName
     * @param deviceId
     * @param userType
     * @param isProfileSet
     * @param restaurantName
     * @param contactPersonName
     */
    public Response(String userId, String firstName, String lastName, String email, String deviceType, String deviceId, String profileImage, Integer status, String message, String userType, String isProfileSet, String restaurantName, String contactPersonName, String restaurantId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.profileImage = profileImage;
        this.status = status;
        this.message = message;
        this.userType = userType;
        this.isProfileSet = isProfileSet;
        this.restaurantName = restaurantName;
        this.contactPersonName = contactPersonName;
        this.restaurantId = restaurantId;
    }

//    public Response(String userId, String firstName, String lastName, String email, String deviceType, String deviceId, String profileImage, Integer status, String message) {
//        super();
//        this.userId = userId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.deviceType = deviceType;
//        this.deviceId = deviceId;
//        this.profileImage = profileImage;
//        this.status = status;
//        this.message = message;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsProfileSet() {
        return isProfileSet;
    }

    public void setIsProfileSet(String isProfileSet) {
        this.isProfileSet = isProfileSet;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
