
package com.os.foodie.data.network.model.deliveryaddress.update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAddressRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("flat_number")
    @Expose
    private String flatNumber;
    @SerializedName("colony")
    @Expose
    private String colony;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateAddressRequest() {
    }

    /**
     * 
     * @param pincode
     * @param landmark
     * @param colony
     * @param userId
     * @param fullName
     * @param mobileNumber
     * @param flatNumber
     * @param addressId
     * @param city
     * @param state
     * @param country
     */
    public UpdateAddressRequest(String userId, String addressId, String fullName, String mobileNumber, String pincode, String flatNumber, String colony, String landmark, String city, String state, String country) {
        super();
        this.userId = userId;
        this.addressId = addressId;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.pincode = pincode;
        this.flatNumber = flatNumber;
        this.colony = colony;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}