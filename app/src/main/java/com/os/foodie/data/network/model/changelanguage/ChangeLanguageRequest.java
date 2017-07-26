
package com.os.foodie.data.network.model.changelanguage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeLanguageRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("language")
    @Expose
    private String language;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ChangeLanguageRequest() {
    }

    /**
     * 
     * @param userId
     * @param language
     */
    public ChangeLanguageRequest(String userId, String language) {
        super();
        this.userId = userId;
        this.language = language;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
