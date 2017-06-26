
package com.os.foodie.data.network.model.checkout;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckoutRequest implements Parcelable
{

    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("order_delievery_date")
    @Expose
    private String orderDelieveryDate;
    @SerializedName("order_delievery_time")
    @Expose
    private String orderDelieveryTime;
    @SerializedName("user_address_id")
    @Expose
    private String userAddressId;
    public final static Creator<CheckoutRequest> CREATOR = new Creator<CheckoutRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CheckoutRequest createFromParcel(Parcel in) {
            CheckoutRequest instance = new CheckoutRequest();
            instance.cardId = ((String) in.readValue((String.class.getClassLoader())));
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryType = ((String) in.readValue((String.class.getClassLoader())));
            instance.paymentMethod = ((String) in.readValue((String.class.getClassLoader())));
            instance.orderDelieveryDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.orderDelieveryTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.userAddressId = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CheckoutRequest[] newArray(int size) {
            return (new CheckoutRequest[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CheckoutRequest() {
    }

    /**
     * 
     * @param cardId
     * @param deliveryType
     * @param userId
     * @param userAddressId
     * @param orderDelieveryTime
     * @param orderDelieveryDate
     * @param paymentMethod
     */
    public CheckoutRequest(String cardId, String userId, String deliveryType, String paymentMethod, String orderDelieveryDate, String orderDelieveryTime, String userAddressId) {
        super();
        this.cardId = cardId;
        this.userId = userId;
        this.deliveryType = deliveryType;
        this.paymentMethod = paymentMethod;
        this.orderDelieveryDate = orderDelieveryDate;
        this.orderDelieveryTime = orderDelieveryTime;
        this.userAddressId = userAddressId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderDelieveryDate() {
        return orderDelieveryDate;
    }

    public void setOrderDelieveryDate(String orderDelieveryDate) {
        this.orderDelieveryDate = orderDelieveryDate;
    }

    public String getOrderDelieveryTime() {
        return orderDelieveryTime;
    }

    public void setOrderDelieveryTime(String orderDelieveryTime) {
        this.orderDelieveryTime = orderDelieveryTime;
    }

    public String getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(String userAddressId) {
        this.userAddressId = userAddressId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cardId);
        dest.writeValue(userId);
        dest.writeValue(deliveryType);
        dest.writeValue(paymentMethod);
        dest.writeValue(orderDelieveryDate);
        dest.writeValue(orderDelieveryTime);
        dest.writeValue(userAddressId);
    }

    public int describeContents() {
        return  0;
    }

}
