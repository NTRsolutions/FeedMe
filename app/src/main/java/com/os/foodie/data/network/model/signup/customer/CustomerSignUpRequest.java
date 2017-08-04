
package com.os.foodie.data.network.model.signup.customer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerSignUpRequest implements Parcelable {
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
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
    public final static Parcelable.Creator<CustomerSignUpRequest> CREATOR = new Creator<CustomerSignUpRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CustomerSignUpRequest createFromParcel(Parcel in) {
            CustomerSignUpRequest instance = new CustomerSignUpRequest();
            instance.firstName = ((String) in.readValue((String.class.getClassLoader())));
            instance.fbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastName = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.password = ((String) in.readValue((String.class.getClassLoader())));
            instance.countryCode = ((String) in.readValue((String.class.getClassLoader())));
            instance.mobileNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceId = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceType = ((String) in.readValue((String.class.getClassLoader())));
            instance.latitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.longitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.language = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CustomerSignUpRequest[] newArray(int size) {
            return (new CustomerSignUpRequest[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public CustomerSignUpRequest() {
    }

    /**
     * @param fbId
     * @param lastName
     * @param email
     * @param deviceType
     * @param longitude
     * @param language
     * @param latitude
     * @param countryCode
     * @param mobileNumber
     * @param firstName
     * @param password
     * @param deviceId
     */
    public CustomerSignUpRequest(String fbId, String firstName, String lastName, String email, String password, String countryCode, String mobileNumber, String deviceId, String deviceType, String latitude, String longitude, String language) {
        super();
        this.fbId = fbId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.countryCode = countryCode;
        this.mobileNumber = mobileNumber;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.language = language;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(firstName);
        dest.writeValue(fbId);
        dest.writeValue(lastName);
        dest.writeValue(email);
        dest.writeValue(password);
        dest.writeValue(countryCode);
        dest.writeValue(mobileNumber);
        dest.writeValue(deviceId);
        dest.writeValue(deviceType);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(language);
    }

    public int describeContents() {
        return 0;
    }
}