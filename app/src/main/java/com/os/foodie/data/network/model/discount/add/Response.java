
package com.os.foodie.data.network.model.discount.add;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable
{

    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            Response instance = new Response();
            instance.discountId = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    }
    ;

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(discountId);
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
