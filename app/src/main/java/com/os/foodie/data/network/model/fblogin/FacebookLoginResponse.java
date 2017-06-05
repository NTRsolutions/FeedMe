
package com.os.foodie.data.network.model.fblogin;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookLoginResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<FacebookLoginResponse> CREATOR = new Creator<FacebookLoginResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FacebookLoginResponse createFromParcel(Parcel in) {
            FacebookLoginResponse instance = new FacebookLoginResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public FacebookLoginResponse[] newArray(int size) {
            return (new FacebookLoginResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FacebookLoginResponse() {
    }

    /**
     * 
     * @param response
     */
    public FacebookLoginResponse(Response response) {
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
