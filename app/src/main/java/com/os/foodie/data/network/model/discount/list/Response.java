
package com.os.foodie.data.network.model.discount.list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Response implements Parcelable
{

    @SerializedName("discount_list")
    @Expose
    private ArrayList<DiscountList> discountList = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("is_delete")
    @Expose
    private String isDeleted;
    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            Response instance = new Response();
            in.readList(instance.discountList, (DiscountList.class.getClassLoader()));
            instance.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            instance.isDeleted = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    }
    ;

    public ArrayList<DiscountList> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(ArrayList<DiscountList> discountList) {
        this.discountList = discountList;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(discountList);
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(isDeleted);
    }

    public int describeContents() {
        return  0;
    }

}
