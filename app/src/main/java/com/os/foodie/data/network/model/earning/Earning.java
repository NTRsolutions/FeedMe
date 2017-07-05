
package com.os.foodie.data.network.model.earning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Earning {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("amount")
    @Expose
    private String amount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Earning() {
    }

    /**
     * 
     * @param amount
     * @param paymentDate
     * @param paymentType
     * @param totalAmount
     * @param orderId
     */
    public Earning(String orderId, String totalAmount, String paymentType, String paymentDate, String amount) {
        super();
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
