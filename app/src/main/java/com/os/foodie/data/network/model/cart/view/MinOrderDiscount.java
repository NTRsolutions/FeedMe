
package com.os.foodie.data.network.model.cart.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MinOrderDiscount {

    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("discount_percentage")
    @Expose
    private String discountPercentage;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MinOrderDiscount() {
    }

    /**
     * 
     * @param discountPercentage
     * @param minOrderAmount
     * @param discountId
     */
    public MinOrderDiscount(String discountId, String discountPercentage, String minOrderAmount) {
        super();
        this.discountId = discountId;
        this.discountPercentage = discountPercentage;
        this.minOrderAmount = minOrderAmount;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

}
