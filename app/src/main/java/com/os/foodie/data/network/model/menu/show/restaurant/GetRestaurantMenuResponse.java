
package com.os.foodie.data.network.model.menu.show.restaurant;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRestaurantMenuResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<GetRestaurantMenuResponse> CREATOR = new Creator<GetRestaurantMenuResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetRestaurantMenuResponse createFromParcel(Parcel in) {
            GetRestaurantMenuResponse instance = new GetRestaurantMenuResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public GetRestaurantMenuResponse[] newArray(int size) {
            return (new GetRestaurantMenuResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetRestaurantMenuResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetRestaurantMenuResponse(Response response) {
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
