
package com.os.foodie.data.network.model.payment.addcard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("credit_card_number")
    @Expose
    private String creditCardNumber;
    @SerializedName("credit_card_id")
    @Expose
    private String creditCardId;
    @SerializedName("card_type")
    @Expose
    private String cardType;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Object> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param message
     * @param cardId
     * @param creditCardId
     * @param status
     * @param data
     * @param creditCardNumber
     * @param cardType
     */
    public Response(Integer status, String cardId, String creditCardNumber, String creditCardId, String cardType, String message, List<Object> data) {
        super();
        this.status = status;
        this.cardId = cardId;
        this.creditCardNumber = creditCardNumber;
        this.creditCardId = creditCardId;
        this.cardType = cardType;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

}
