package com.os.foodie.data.network.model.staticpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abhinava on 6/30/2017.
 */

public class StaticPageResponse {

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
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_delete")
        @Expose
        private String isDeleted;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
    }
}

