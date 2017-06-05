
package com.os.foodie.data.network.model.otp;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerificationResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<OtpVerificationResponse> CREATOR = new Creator<OtpVerificationResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OtpVerificationResponse createFromParcel(Parcel in) {
            OtpVerificationResponse instance = new OtpVerificationResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public OtpVerificationResponse[] newArray(int size) {
            return (new OtpVerificationResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OtpVerificationResponse() {
    }

    /**
     * 
     * @param response
     */
    public OtpVerificationResponse(Response response) {
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
