package com.os.foodie.data.network.model.account.edit.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by monikab on 6/2/2017.
 */

public class EditRestaurantAccountRequest {

    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;

    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
