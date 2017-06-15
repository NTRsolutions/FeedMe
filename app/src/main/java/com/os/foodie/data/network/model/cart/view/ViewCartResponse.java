
package com.os.foodie.data.network.model.cart.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewCartResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<ViewCartResponse> CREATOR = new Creator<ViewCartResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ViewCartResponse createFromParcel(Parcel in) {
            ViewCartResponse instance = new ViewCartResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public ViewCartResponse[] newArray(int size) {
            return (new ViewCartResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ViewCartResponse() {
    }

    /**
     * 
     * @param response
     */
    public ViewCartResponse(Response response) {
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
