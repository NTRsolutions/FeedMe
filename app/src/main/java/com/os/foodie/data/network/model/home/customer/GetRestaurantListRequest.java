
package com.os.foodie.data.network.model.home.customer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRestaurantListRequest implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("search_text")
    @Expose
    private String searchText;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("filters")
    @Expose
    private Filters filters;
    public final static Creator<GetRestaurantListRequest> CREATOR = new Creator<GetRestaurantListRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GetRestaurantListRequest createFromParcel(Parcel in) {
            GetRestaurantListRequest instance = new GetRestaurantListRequest();
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
            instance.searchText = ((String) in.readValue((String.class.getClassLoader())));
            instance.latitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.longitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.cityName = ((String) in.readValue((String.class.getClassLoader())));
            instance.filters = ((Filters) in.readValue((Filters.class.getClassLoader())));
            return instance;
        }

        public GetRestaurantListRequest[] newArray(int size) {
            return (new GetRestaurantListRequest[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public GetRestaurantListRequest() {
    }

    /**
     * @param userId
     * @param searchText
     * @param filters
     */
    public GetRestaurantListRequest(String userId, String searchText, String latitude, String longitude, String cityName, Filters filters) {
        super();
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
        this.searchText = searchText;
        this.filters = filters;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(searchText);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(cityName);
        dest.writeValue(filters);
    }

    public int describeContents() {
        return 0;
    }

}
