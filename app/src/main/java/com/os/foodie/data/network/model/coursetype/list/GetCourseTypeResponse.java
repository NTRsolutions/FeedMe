
package com.os.foodie.data.network.model.coursetype.list;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCourseTypeResponse implements Parcelable {

    @SerializedName("response")
    @Expose
    private Response response;
    public final static Creator<GetCourseTypeResponse> CREATOR = new Creator<GetCourseTypeResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GetCourseTypeResponse createFromParcel(Parcel in) {
            GetCourseTypeResponse instance = new GetCourseTypeResponse();
            instance.response = ((Response) in.readValue((Response.class.getClassLoader())));
            return instance;
        }

        public GetCourseTypeResponse[] newArray(int size) {
            return (new GetCourseTypeResponse[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public GetCourseTypeResponse() {
    }

    /**
     * @param response
     */
    public GetCourseTypeResponse(Response response) {
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
