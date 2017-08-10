package com.os.foodie.data.network.model.notification;

/**
 * Created by monikab on 7/17/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NotificationListResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public class NotificationList {

        @SerializedName("notification_time")
        @Expose
        private String notificationTime;
        @SerializedName("notification_id")
        @Expose
        private String notificationId;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("notification_type")
        @Expose
        private String notificationType;
        @SerializedName("is_read")
        @Expose
        private String isRead;
        @SerializedName("notification_message")
        @Expose
        private String notificationMessage;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("fullname")
        @Expose
        private String fullname;

        public String getNotificationTime() {
            return notificationTime;
        }

        public void setNotificationTime(String notificationTime) {
            this.notificationTime = notificationTime;
        }

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getNotificationMessage() {
            return notificationMessage;
        }

        public void setNotificationMessage(String notificationMessage) {
            this.notificationMessage = notificationMessage;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

    }


    public class Response {

        @SerializedName("notification_list")
        @Expose
        private List<NotificationList> notificationList = null;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("is_delete")
        @Expose
        private String isDeleted;

        public List<NotificationList> getNotificationList() {
            return notificationList;
        }

        public void setNotificationList(List<NotificationList> notificationList) {
            this.notificationList = notificationList;
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

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
    }

}

