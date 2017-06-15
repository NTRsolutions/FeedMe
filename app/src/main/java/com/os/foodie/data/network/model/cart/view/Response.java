
package com.os.foodie.data.network.model.cart.view;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable
{

    @SerializedName("cart_list")
    @Expose
    private List<CartList> cartList = null;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            Response instance = new Response();
            in.readList(instance.cartList, (com.os.foodie.data.network.model.cart.view.CartList.class.getClassLoader()));
            instance.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantName = ((String) in.readValue((String.class.getClassLoader())));
            instance.openingTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.closingTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryType = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryCharge = ((String) in.readValue((String.class.getClassLoader())));
            instance.minOrderAmount = ((String) in.readValue((String.class.getClassLoader())));
            instance.cartCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param message
     * @param cartCount
     * @param deliveryTime
     * @param status
     * @param deliveryType
     * @param cartList
     * @param closingTime
     * @param minOrderAmount
     * @param openingTime
     * @param deliveryCharge
     * @param restaurantName
     * @param restaurantId
     */
    public Response(List<CartList> cartList, String restaurantId, String restaurantName, String openingTime, String closingTime, String deliveryTime, String deliveryType, String deliveryCharge, String minOrderAmount, Integer cartCount, Integer status, String message) {
        super();
        this.cartList = cartList;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.deliveryTime = deliveryTime;
        this.deliveryType = deliveryType;
        this.deliveryCharge = deliveryCharge;
        this.minOrderAmount = minOrderAmount;
        this.cartCount = cartCount;
        this.status = status;
        this.message = message;
    }

    public List<CartList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartList> cartList) {
        this.cartList = cartList;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cartList);
        dest.writeValue(restaurantId);
        dest.writeValue(restaurantName);
        dest.writeValue(openingTime);
        dest.writeValue(closingTime);
        dest.writeValue(deliveryTime);
        dest.writeValue(deliveryType);
        dest.writeValue(deliveryCharge);
        dest.writeValue(minOrderAmount);
        dest.writeValue(cartCount);
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
