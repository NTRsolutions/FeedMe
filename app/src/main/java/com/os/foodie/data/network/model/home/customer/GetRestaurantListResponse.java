
package com.os.foodie.data.network.model.home.customer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRestaurantListResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<GetRestaurantListResponse> CREATOR = new Creator<GetRestaurantListResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetRestaurantListResponse createFromParcel(Parcel in) {
            GetRestaurantListResponse instance = new GetRestaurantListResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public GetRestaurantListResponse[] newArray(int size) {
            return (new GetRestaurantListResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetRestaurantListResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetRestaurantListResponse(Response response) {
        super();
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(response);
    }

    public int describeContents() {
        return  0;
    }

}
