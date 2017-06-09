
package com.os.foodie.data.network.model.home.customer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRestaurantListRequest implements Parcelable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("search_text")
    @Expose
    private String searchText;
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
            instance.filters = ((Filters) in.readValue((Filters.class.getClassLoader())));
            return instance;
        }

        public GetRestaurantListRequest[] newArray(int size) {
            return (new GetRestaurantListRequest[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetRestaurantListRequest() {
    }

    /**
     * 
     * @param userId
     * @param searchText
     * @param filters
     */
    public GetRestaurantListRequest(String userId, String searchText, Filters filters) {
        super();
        this.userId = userId;
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

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(searchText);
        dest.writeValue(filters);
    }

    public int describeContents() {
        return  0;
    }

}
