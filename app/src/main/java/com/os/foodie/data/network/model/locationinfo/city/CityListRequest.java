
package com.os.foodie.data.network.model.locationinfo.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListRequest {

    @SerializedName("language")
    @Expose
    private String language;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CityListRequest() {
    }

    /**
     * 
     * @param language
     */
    public CityListRequest(String language) {
        super();
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String countryId) {
        this.language = language;
    }

}
