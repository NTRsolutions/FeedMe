
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
    public final static Creator<GetRestaurantListRequest> CREATOR = new Creator<GetRestaurantListRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetRestaurantListRequest createFromParcel(Parcel in) {
            GetRestaurantListRequest instance = new GetRestaurantListRequest();
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
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
     */
    public GetRestaurantListRequest(String userId) {
        super();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
    }

    public int describeContents() {
        return  0;
    }

}
