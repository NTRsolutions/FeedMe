package com.os.foodie.data.network.model.discount.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abhinava on 6/28/2017.
 */

public class DeleteDiscountRequest
{
    public DeleteDiscountRequest(String discount_id) {
        super();
        this.discountId = discount_id;
    }

    @SerializedName("discount_id")
    @Expose
    private String discountId;

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discount_id) {
        this.discountId = discount_id;
    }
}
