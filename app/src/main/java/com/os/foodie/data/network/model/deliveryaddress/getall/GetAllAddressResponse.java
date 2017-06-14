
package com.os.foodie.data.network.model.deliveryaddress.getall;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllAddressResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<GetAllAddressResponse> CREATOR = new Creator<GetAllAddressResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetAllAddressResponse createFromParcel(Parcel in) {
            GetAllAddressResponse instance = new GetAllAddressResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public GetAllAddressResponse[] newArray(int size) {
            return (new GetAllAddressResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAllAddressResponse() {
    }

    /**
     * 
     * @param response
     */
    public GetAllAddressResponse(Response response) {
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
