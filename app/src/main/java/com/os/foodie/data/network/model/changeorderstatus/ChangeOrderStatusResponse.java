package com.os.foodie.data.network.model.changeorderstatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by monikab on 7/4/2017.
 */

public class ChangeOrderStatusResponse {

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

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("current_status")
        @Expose
        private String currentStatus;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("is_delete")
        @Expose
        private String isDeleted;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
    }
}