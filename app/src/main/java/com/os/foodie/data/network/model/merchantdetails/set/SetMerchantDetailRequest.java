
package com.os.foodie.data.network.model.merchantdetails.set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetMerchantDetailRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SetMerchantDetailRequest() {
    }

    /**
     * 
     * @param accountNumber
     * @param bankName
     * @param ifscCode
     * @param restaurantId
     * @param accountHolderName
     */
    public SetMerchantDetailRequest(String restaurantId, String bankName, String accountNumber, String accountHolderName, String ifscCode) {
        super();
        this.restaurantId = restaurantId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.ifscCode = ifscCode;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

}
