
package com.os.foodie.data.network.model.discount.list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountListResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<DiscountListResponse> CREATOR = new Creator<DiscountListResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DiscountListResponse createFromParcel(Parcel in) {
            DiscountListResponse instance = new DiscountListResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public DiscountListResponse[] newArray(int size) {
            return (new DiscountListResponse[size]);
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
