
package com.os.foodie.data.network.model.deliveryaddress.getall;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable
{

    @SerializedName("address")
    @Expose
    private List<Address> address = null;
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
            in.readList(instance.address, (com.os.foodie.data.network.model.deliveryaddress.getall.Address.class.getClassLoader()));
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

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param message
     * @param status
     * @param address
     */
    public Response(List<Address> address, Integer status, String message, String isDeleted) {
        super();
        this.address = address;
        this.status = status;
        this.message = message;
        this.isDeleted = isDeleted;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
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
        dest.writeList(address);
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
