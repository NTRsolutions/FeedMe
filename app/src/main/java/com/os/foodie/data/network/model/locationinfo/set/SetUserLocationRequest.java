
package com.os.foodie.data.network.model.locationinfo.set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetUserLocationRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SetUserLocationRequest() {
    }

    /**
     * 
     * @param address
     * @param userId
     * @param longitude
     * @param latitude
     * @param city
     * @param country
     */
    public SetUserLocationRequest(String userId, String latitude, String longitude, String address, String country, String city) {
        super();
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.country = country;
        this.city = city;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
