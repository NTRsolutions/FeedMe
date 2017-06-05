
package com.os.foodie.data.network.model.cuisinetype.list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CuisineTypeResponse implements Parcelable {

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<CuisineTypeResponse> CREATOR = new Creator<CuisineTypeResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CuisineTypeResponse createFromParcel(Parcel in) {
            CuisineTypeResponse instance = new CuisineTypeResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public CuisineTypeResponse[] newArray(int size) {
            return (new CuisineTypeResponse[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public CuisineTypeResponse() {
    }

    /**
     * @param response
     */
    public CuisineTypeResponse(Response response) {
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
        return 0;
    }

}
