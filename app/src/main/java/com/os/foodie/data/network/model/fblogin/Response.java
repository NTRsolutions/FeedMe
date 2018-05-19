
package com.os.foodie.data.network.model.fblogin;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
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
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_profile_set")
    @Expose
    private String isProfileSet;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_name_arabic")
    @Expose
    private String restaurantNameArabic;
    @SerializedName("contact_person_name")
    @Expose
    private Object contactPersonName;
    @SerializedName("is_notification")
    @Expose
    private String isNotification;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("is_facebook")
    @Expose
    private String isFacebook;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("is_delete")
    @Expose
    private String isDeleted;

    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            Response instance = new Response();
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
            instance.fbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.firstName = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastName = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceType = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceId = ((String) in.readValue((String.class.getClassLoader())));
            instance.latitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.longitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.isProfileSet = ((String) in.readValue((String.class.getClassLoader())));
            instance.userType = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantName = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantNameArabic = ((String) in.readValue((String.class.getClassLoader())));
            instance.contactPersonName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.isNotification = ((String) in.readValue((String.class.getClassLoader())));
            instance.profileImage = ((String) in.readValue((String.class.getClassLoader())));
            instance.isFacebook = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
            instance.language = ((String) in.readValue((String.class.getClassLoader())));
            instance.isDeleted = ((String) in.readValue((String.class.getClassLoader())));
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
     * @param fbId
     * @param isProfileSet
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
     * @param deviceId
     * @param restaurantName
     * @param restaurantId
     * @param language
     */
    public Response(String userId, String fbId, String firstName, String lastName, String email, String deviceType, String deviceId, String latitude, String longitude, String isProfileSet, String userType, String restaurantName, String restaurantNameArabic, Object contactPersonName, String isNotification, String profileImage, String isFacebook, Integer status, String message, String restaurantId, String language, String isDeleted) {
        super();
        this.userId = userId;
        this.fbId = fbId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isProfileSet = isProfileSet;
        this.userType = userType;
        this.restaurantName = restaurantName;
        this.restaurantNameArabic = restaurantNameArabic;
        this.contactPersonName = contactPersonName;
        this.isNotification = isNotification;
        this.profileImage = profileImage;
        this.isFacebook = isFacebook;
        this.status = status;
        this.message = message;
        this.restaurantId = restaurantId;
        this.language = language;
        this.isDeleted = isDeleted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
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

    public String getIsProfileSet() {
        return isProfileSet;
    }

    public void setIsProfileSet(String isProfileSet) {
        this.isProfileSet = isProfileSet;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Object getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(Object contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getIsNotification() {
        return isNotification;
    }

    public void setIsNotification(String isNotification) {
        this.isNotification = isNotification;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getIsFacebook() {
        return isFacebook;
    }

    public void setIsFacebook(String isFacebook) {
        this.isFacebook = isFacebook;
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

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRestaurantNameArabic() {
        return restaurantNameArabic;
    }

    public void setRestaurantNameArabic(String restaurantNameArabic) {
        this.restaurantNameArabic = restaurantNameArabic;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(fbId);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(email);
        dest.writeValue(deviceType);
        dest.writeValue(deviceId);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(isProfileSet);
        dest.writeValue(userType);
        dest.writeValue(restaurantName);
        dest.writeValue(restaurantNameArabic);
        dest.writeValue(contactPersonName);
        dest.writeValue(isNotification);
        dest.writeValue(profileImage);
        dest.writeValue(isFacebook);
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(restaurantId);
        dest.writeValue(language);
        dest.writeValue(isDeleted);
    }

    public int describeContents() {
        return  0;
    }

}
