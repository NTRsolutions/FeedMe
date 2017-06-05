
package com.os.foodie.data.network.model.changepassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("old_password")
    @Expose
    private String oldPassword;
    @SerializedName("new_password")
    @Expose
    private String newPassword;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ChangePasswordRequest() {
    }

    /**
     * 
     * @param newPassword
     * @param userId
     * @param oldPassword
     */
    public ChangePasswordRequest(String userId, String oldPassword, String newPassword) {
        super();
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
