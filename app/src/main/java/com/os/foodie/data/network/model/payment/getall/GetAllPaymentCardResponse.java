
package com.os.foodie.data.network.model.payment.getall;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllPaymentCardResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<GetAllPaymentCardResponse> CREATOR = new Creator<GetAllPaymentCardResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetAllPaymentCardResponse createFromParcel(Parcel in) {
            GetAllPaymentCardResponse instance = new GetAllPaymentCardResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public GetAllPaymentCardResponse[] newArray(int size) {
            return (new GetAllPaymentCardResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAllPaymentCardResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetAllPaymentCardResponse(Response response) {
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
