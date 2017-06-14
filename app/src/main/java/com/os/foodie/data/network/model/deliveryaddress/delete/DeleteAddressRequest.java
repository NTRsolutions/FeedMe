
package com.os.foodie.data.network.model.deliveryaddress.delete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteAddressRequest {

    @SerializedName("address_id")
    @Expose
    private String addressId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeleteAddressRequest() {
    }

    /**
     * 
     * @param addressId
     */
    public DeleteAddressRequest(String addressId) {
        super();
        this.addressId = addressId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

}
