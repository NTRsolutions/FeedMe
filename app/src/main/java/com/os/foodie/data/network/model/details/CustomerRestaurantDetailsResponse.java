
package com.os.foodie.data.network.model.details;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerRestaurantDetailsResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<CustomerRestaurantDetailsResponse> CREATOR = new Creator<CustomerRestaurantDetailsResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CustomerRestaurantDetailsResponse createFromParcel(Parcel in) {
            CustomerRestaurantDetailsResponse instance = new CustomerRestaurantDetailsResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public CustomerRestaurantDetailsResponse[] newArray(int size) {
            return (new CustomerRestaurantDetailsResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomerRestaurantDetailsResponse() {
    }

    /**
     * 
     * @param response
     */
    public CustomerRestaurantDetailsResponse(Response response) {
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
