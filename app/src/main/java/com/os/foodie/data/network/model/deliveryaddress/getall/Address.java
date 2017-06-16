
package com.os.foodie.data.network.model.deliveryaddress.getall;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
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
    public final static Creator<Address> CREATOR = new Creator<Address>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Address createFromParcel(Parcel in) {
            Address instance = new Address();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
            instance.fullName = ((String) in.readValue((String.class.getClassLoader())));
            instance.mobileNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.pincode = ((String) in.readValue((String.class.getClassLoader())));
            instance.flatNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.colony = ((String) in.readValue((String.class.getClassLoader())));
            instance.landmark = ((String) in.readValue((String.class.getClassLoader())));
            instance.city = ((String) in.readValue((String.class.getClassLoader())));
            instance.state = ((String) in.readValue((String.class.getClassLoader())));
            instance.country = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Address[] newArray(int size) {
            return (new Address[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Address() {
    }

    /**
     * @param pincode
     * @param id
     * @param landmark
     * @param colony
     * @param userId
     * @param fullName
     * @param mobileNumber
     * @param flatNumber
     * @param city
     * @param state
     * @param country
     */
    public Address(String id, String userId, String fullName, String mobileNumber, String pincode, String flatNumber, String colony, String landmark, String city, String state, String country) {
        super();
        this.id = id;
        this.userId = userId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(fullName);
        dest.writeValue(mobileNumber);
        dest.writeValue(pincode);
        dest.writeValue(flatNumber);
        dest.writeValue(colony);
        dest.writeValue(landmark);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(country);
    }

    public int describeContents() {
        return 0;
    }

}
