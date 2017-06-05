
package com.os.foodie.data.network.model.details;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerRestaurantDetailsRequest implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    public final static Creator<CustomerRestaurantDetailsRequest> CREATOR = new Creator<CustomerRestaurantDetailsRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CustomerRestaurantDetailsRequest createFromParcel(Parcel in) {
            CustomerRestaurantDetailsRequest instance = new CustomerRestaurantDetailsRequest();
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CustomerRestaurantDetailsRequest[] newArray(int size) {
            return (new CustomerRestaurantDetailsRequest[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public CustomerRestaurantDetailsRequest() {
    }

    /**
     * @param userId
     * @param restaurantId
     */
    public CustomerRestaurantDetailsRequest(String userId, String restaurantId) {
        super();
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(restaurantId);
        dest.writeValue(userId);
    }

    public int describeContents() {
        return 0;
    }

}
