
package com.os.foodie.data.network.model.payment.getall;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card implements Parcelable
{

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
    @Expose(serialize = false, deserialize = false)
    private boolean isChecked;
    public final static Creator<Card> CREATOR = new Creator<Card>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Card createFromParcel(Parcel in) {
            Card instance = new Card();
            instance.cardId = ((String) in.readValue((String.class.getClassLoader())));
            instance.creditCardNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.creditCardId = ((String) in.readValue((String.class.getClassLoader())));
            instance.cardType = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Card[] newArray(int size) {
            return (new Card[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Card() {
    }

    /**
     * 
     * @param cardId
     * @param creditCardId
     * @param creditCardNumber
     * @param cardType
     */
    public Card(String cardId, String creditCardNumber, String creditCardId, String cardType) {
        super();
        this.cardId = cardId;
        this.creditCardNumber = creditCardNumber;
        this.creditCardId = creditCardId;
        this.cardType = cardType;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cardId);
        dest.writeValue(creditCardNumber);
        dest.writeValue(creditCardId);
        dest.writeValue(cardType);
    }

    public int describeContents() {
        return  0;
    }

}
