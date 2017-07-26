package com.os.foodie.data.network.model.account.edit.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by monikab on 6/5/2017.
 */

public class EditRestaurantAccountResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public class Response {

        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile_number")
        @Expose
        private String mobileNumber;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("contact_person_name")
        @Expose
        private String contactPersonName;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
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

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getContactPersonName() {
            return contactPersonName;
        }

        public void setContactPersonName(String contactPersonName) {
            this.contactPersonName = contactPersonName;
        }
    }
}