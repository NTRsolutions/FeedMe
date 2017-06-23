
package com.os.foodie.data.network.model.payment.delete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeletePaymentCardRequest {

    @SerializedName("card_id")
    @Expose
    private String cardId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeletePaymentCardRequest() {
    }

    /**
     * 
     * @param cardId
     */
    public DeletePaymentCardRequest(String cardId) {
        super();
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

}
