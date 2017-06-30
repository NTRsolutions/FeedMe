
package com.os.foodie.data.network.model.discount.add;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDiscountResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<AddDiscountResponse> CREATOR = new Creator<AddDiscountResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AddDiscountResponse createFromParcel(Parcel in) {
            AddDiscountResponse instance = new AddDiscountResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public AddDiscountResponse[] newArray(int size) {
            return (new AddDiscountResponse[size]);
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
