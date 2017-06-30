
package com.os.foodie.data.network.model.discount.dishlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DishListResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<DishListResponse> CREATOR = new Creator<DishListResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DishListResponse createFromParcel(Parcel in) {
            DishListResponse instance = new DishListResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public DishListResponse[] newArray(int size) {
            return (new DishListResponse[size]);
        }

    }
    ;

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
