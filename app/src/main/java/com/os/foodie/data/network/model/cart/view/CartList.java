
package com.os.foodie.data.network.model.cart.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartList {

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("available_max_discount")
    @Expose
    private String availableMaxDiscount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_arabic")
    @Expose
    private String nameArabic;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("dish_image")
    @Expose
    private String dishImage;

    /**
     * No args constructor for use in serialization
     */
    public CartList() {
    }

    /**
     * @param availableMaxDiscount
     * @param price
     * @param description
     * @param name
     * @param qty
     * @param discount
     * @param cartId
     * @param dishId
     * @param discountId
     */
    public CartList(String cartId, String qty, String dishId, String discountId, String availableMaxDiscount, String name, String discount, String description, String price, String dishImage) {
        super();
        this.cartId = cartId;
        this.qty = qty;
        this.dishId = dishId;
        this.discountId = discountId;
        this.availableMaxDiscount = availableMaxDiscount;
        this.name = name;
        this.discount = discount;
        this.description = description;
        this.price = price;
        this.dishImage = dishImage;
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

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getAvailableMaxDiscount() {
        return availableMaxDiscount;
    }

    public void setAvailableMaxDiscount(String availableMaxDiscount) {
        this.availableMaxDiscount = availableMaxDiscount;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }
}
