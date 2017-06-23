
package com.os.foodie.data.network.model.payment.addcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPaymentCardRequest {

    @SerializedName("credit_card_number")
    @Expose
    private String creditCardNumber;
    @SerializedName("expiry_month")
    @Expose
    private String expiryMonth;
    @SerializedName("expiry_year")
    @Expose
    private String expiryYear;
    @SerializedName("cvv2")
    @Expose
    private String cvv2;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddPaymentCardRequest() {
    }

    /**
     * 
     * @param expiryYear
     * @param lastName
     * @param userId
     * @param expiryMonth
     * @param creditCardNumber
     * @param firstName
     * @param cvv2
     */
    public AddPaymentCardRequest(String creditCardNumber, String expiryMonth, String expiryYear, String cvv2, String firstName, String lastName, String userId) {
        super();
        this.creditCardNumber = creditCardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv2 = cvv2;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
