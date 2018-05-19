
package com.os.foodie.data.network.model.citycountrylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityCountryListRequest {

    @SerializedName("language")
    @Expose
    private String language;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CityCountryListRequest() {
    }

    /**
     * 
     * @param language
     */
    public CityCountryListRequest(String language) {
        super();
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
