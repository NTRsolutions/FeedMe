
package com.os.foodie.data.network.model.home.customer;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable {

    @SerializedName("restaurant_list")
    @Expose
    private List<RestaurantList> restaurantList = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public final static Creator<Response> CREATOR = new Creator<Response>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            Response instance = new Response();
            in.readList(instance.restaurantList, (com.os.foodie.data.network.model.home.customer.RestaurantList.class.getClassLoader()));
            instance.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param restaurantList
     * @param status
     * @param message
     */
    public Response(List<RestaurantList> restaurantList, Integer status, String message) {
        super();
        this.restaurantList = restaurantList;
        this.status = status;
        this.message = message;
    }

    public List<RestaurantList> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<RestaurantList> restaurantList) {
        this.restaurantList = restaurantList;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(restaurantList);
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }
}
