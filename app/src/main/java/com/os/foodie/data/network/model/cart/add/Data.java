
package com.os.foodie.data.network.model.cart.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("total_cart_quantity")
    @Expose
    private String totalCartQuantity;
    @SerializedName("total_cart_amount")
    @Expose
    private String totalCartAmount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param totalCartQuantity
     * @param totalCartAmount
     */
    public Data(String totalCartQuantity, String totalCartAmount) {
        super();
        this.totalCartQuantity = totalCartQuantity;
        this.totalCartAmount = totalCartAmount;
    }

    public String getTotalCartQuantity() {
        return totalCartQuantity;
    }

    public void setTotalCartQuantity(String totalCartQuantity) {
        this.totalCartQuantity = totalCartQuantity;
    }

    public String getTotalCartAmount() {
        return totalCartAmount;
    }

    public void setTotalCartAmount(String totalCartAmount) {
        this.totalCartAmount = totalCartAmount;
    }

}
