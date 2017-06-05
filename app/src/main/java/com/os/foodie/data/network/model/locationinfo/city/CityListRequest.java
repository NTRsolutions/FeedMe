
package com.os.foodie.data.network.model.locationinfo.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListRequest {

    @SerializedName("country_id")
    @Expose
    private String countryId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CityListRequest() {
    }

    /**
     * 
     * @param countryId
     */
    public CityListRequest(String countryId) {
        super();
        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

}
