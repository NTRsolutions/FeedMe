
package com.os.foodie.data.network.model.cart.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartList implements Parcelable
{

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    public final static Creator<CartList> CREATOR = new Creator<CartList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CartList createFromParcel(Parcel in) {
            CartList instance = new CartList();
            instance.cartId = ((String) in.readValue((String.class.getClassLoader())));
            instance.qty = ((String) in.readValue((String.class.getClassLoader())));
            instance.dishId = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.price = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CartList[] newArray(int size) {
            return (new CartList[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CartList() {
    }

    /**
     * 
     * @param price
     * @param description
     * @param name
     * @param qty
     * @param cartId
     * @param dishId
     */
    public CartList(String cartId, String qty, String dishId, String name, String description, String price) {
        super();
        this.cartId = cartId;
        this.qty = qty;
        this.dishId = dishId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cartId);
        dest.writeValue(qty);
        dest.writeValue(dishId);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(price);
    }

    public int describeContents() {
        return  0;
    }

}
