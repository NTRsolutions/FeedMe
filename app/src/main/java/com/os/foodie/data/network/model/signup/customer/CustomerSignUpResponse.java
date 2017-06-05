
package com.os.foodie.data.network.model.signup.customer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerSignUpResponse implements Parcelable
{

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<CustomerSignUpResponse> CREATOR = new Creator<CustomerSignUpResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CustomerSignUpResponse createFromParcel(Parcel in) {
            CustomerSignUpResponse instance = new CustomerSignUpResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public CustomerSignUpResponse[] newArray(int size) {
            return (new CustomerSignUpResponse[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomerSignUpResponse() {
    }

    /**
     * 
     * @param response
     */
    public CustomerSignUpResponse(Response response) {
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
