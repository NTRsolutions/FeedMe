
package com.os.foodie.data.network.model.otp.verify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("first_name")
    @Expose
    private Object firstName;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("is_facebook")
    @Expose
    private String isFacebook;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("is_notification")
    @Expose
    private String isNotification;
    @SerializedName("is_profile_set")
    @Expose
    private String isProfileSet;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            Response instance = new Response();
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
            instance.firstName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.lastName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.latitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.longitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceType = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceId = ((String) in.readValue((String.class.getClassLoader())));
            instance.mobileNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantName = ((String) in.readValue((String.class.getClassLoader())));
            instance.userType = ((String) in.readValue((String.class.getClassLoader())));
            instance.isFacebook = ((String) in.readValue((String.class.getClassLoader())));
            instance.fbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.isNotification = ((String) in.readValue((String.class.getClassLoader())));
            instance.isProfileSet = ((String) in.readValue((String.class.getClassLoader())));
            instance.contactPersonName = ((String) in.readValue((String.class.getClassLoader())));
            instance.profileImage = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param isProfileSet
     * @param fbId
     * @param lastName
     * @param profileImage
     * @param status
     * @param isFacebook
     * @param userType
     * @param message
     * @param isNotification
     * @param email
     * @param deviceType
     * @param userId
     * @param longitude
     * @param contactPersonName
     * @param latitude
     * @param firstName
     * @param mobileNumber
     * @param deviceId
     * @param restaurantName
     */
    public Response(String userId, Object firstName, Object lastName, String email, String latitude, String longitude, String deviceType, String deviceId, String mobileNumber, String restaurantName, String userType, String isFacebook, String fbId, String isNotification, String isProfileSet, String contactPersonName, String profileImage, Integer status, String message) {
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.mobileNumber = mobileNumber;
        this.restaurantName = restaurantName;
        this.userType = userType;
        this.isFacebook = isFacebook;
        this.fbId = fbId;
        this.isNotification = isNotification;
        this.isProfileSet = isProfileSet;
        this.contactPersonName = contactPersonName;
        this.profileImage = profileImage;
        this.status = status;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsFacebook() {
        return isFacebook;
    }

    public void setIsFacebook(String isFacebook) {
        this.isFacebook = isFacebook;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getIsNotification() {
        return isNotification;
    }

    public void setIsNotification(String isNotification) {
        this.isNotification = isNotification;
    }

    public String getIsProfileSet() {
        return isProfileSet;
    }

    public void setIsProfileSet(String isProfileSet) {
        this.isProfileSet = isProfileSet;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(email);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(deviceType);
        dest.writeValue(deviceId);
        dest.writeValue(mobileNumber);
        dest.writeValue(restaurantName);
        dest.writeValue(userType);
        dest.writeValue(isFacebook);
        dest.writeValue(fbId);
        dest.writeValue(isNotification);
        dest.writeValue(isProfileSet);
        dest.writeValue(contactPersonName);
        dest.writeValue(profileImage);
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
