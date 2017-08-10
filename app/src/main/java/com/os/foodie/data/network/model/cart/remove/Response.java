
package com.os.foodie.data.network.model.cart.remove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("total_cart_quantity")
    @Expose
    private String totalCartQuantity;
    @SerializedName("total_cart_amount")
    @Expose
    private String totalCartAmount;
    @SerializedName("is_delete")
    @Expose
    private String isDeleted;

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
     */
    public Response(String message, Integer status,String totalCartQuantity, String totalCartAmount) {
        super();
        this.message = message;
        this.status = status;
        this.totalCartQuantity = totalCartQuantity;
        this.totalCartAmount = totalCartAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}