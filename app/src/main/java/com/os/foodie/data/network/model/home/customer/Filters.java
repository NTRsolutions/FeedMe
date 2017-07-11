
package com.os.foodie.data.network.model.home.customer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filters implements Parcelable {

    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("max_order_amount")
    @Expose
    private String maxOrderAmount;
    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("min_distance")
    @Expose
    private String minDistance;
    @SerializedName("max_distance")
    @Expose
    private String maxDistance;
    @SerializedName("cuisine_type")
    @Expose
    private String cuisineTypes;
    @SerializedName("is_discount")
    @Expose
    private String isDiscount;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("open_close")
    @Expose
    private String openClose;
    @Expose(serialize = false, deserialize = false)
    private transient String cuisineNames;
    @Expose(serialize = false, deserialize = false)
    private transient boolean isClear = true;

    public final static Creator<Filters> CREATOR = new Creator<Filters>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Filters createFromParcel(Parcel in) {
            Filters instance = new Filters();
            instance.minOrderAmount = ((String) in.readValue((String.class.getClassLoader())));
            instance.maxOrderAmount = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryType = ((String) in.readValue((String.class.getClassLoader())));
            instance.time = ((String) in.readValue((String.class.getClassLoader())));
            instance.minDistance = ((String) in.readValue((String.class.getClassLoader())));
            instance.maxDistance = ((String) in.readValue((String.class.getClassLoader())));
            instance.cuisineTypes = ((String) in.readValue((String.class.getClassLoader())));
            instance.isDiscount = ((String) in.readValue((String.class.getClassLoader())));
            instance.rating = ((String) in.readValue((String.class.getClassLoader())));
            instance.openClose = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Filters[] newArray(int size) {
            return (new Filters[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Filters() {
    }

    /**
     * @param maxOrderAmount
     * @param time
     * @param maxDistance
     * @param minDistance
     * @param deliveryType
     * @param minOrderAmount
     * @param cuisineTypes
     * @param isDiscount
     * @param rating
     * @param openClose
     */
    public Filters(String minOrderAmount, String maxOrderAmount, String deliveryType, String time, String minDistance, String maxDistance, String cuisineTypes, String isDiscount, String rating, String openClose) {
        super();
        this.minOrderAmount = minOrderAmount;
        this.maxOrderAmount = maxOrderAmount;
        this.deliveryType = deliveryType;
        this.time = time;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.cuisineTypes = cuisineTypes;
        this.isDiscount = isDiscount;
        this.rating = rating;
        this.openClose = openClose;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getMaxOrderAmount() {
        return maxOrderAmount;
    }

    public void setMaxOrderAmount(String maxOrderAmount) {
        this.maxOrderAmount = maxOrderAmount;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(String minDistance) {
        this.minDistance = minDistance;
    }

    public String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getCuisineTypes() {
        return cuisineTypes;
    }

    public void setCuisineTypes(String cuisineTypes) {
        this.cuisineTypes = cuisineTypes;
    }

    public String getCuisineNames() {
        return cuisineNames;
    }

    public void setCuisineNames(String cuisineNames) {
        this.cuisineNames = cuisineNames;
    }

    public String getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOpenClose() {
        return openClose;
    }

    public void setOpenClose(String openClose) {
        this.openClose = openClose;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(minOrderAmount);
        dest.writeValue(maxOrderAmount);
        dest.writeValue(deliveryType);
        dest.writeValue(time);
        dest.writeValue(minDistance);
        dest.writeValue(maxDistance);
        dest.writeValue(cuisineTypes);
        dest.writeValue(isDiscount);
        dest.writeValue(rating);
        dest.writeValue(openClose);
    }

    public int describeContents() {
        return 0;
    }

    public boolean checkClear() {

        if (minOrderAmount == null || minOrderAmount.isEmpty()) {
            isClear = false;
            return false;
        } else if (maxOrderAmount == null || maxOrderAmount.isEmpty()) {
            isClear = false;
            return false;
        } else if (deliveryType == null || deliveryType.isEmpty()) {
            isClear = false;
            return false;
        } else if (time == null || time.isEmpty()) {
            isClear = false;
            return false;
        } else if (minDistance == null || minDistance.isEmpty()) {
            isClear = false;
            return false;
        } else if (maxDistance == null || maxDistance.isEmpty()) {
            isClear = false;
            return false;
        } else if (cuisineTypes == null || cuisineTypes.isEmpty()) {
            isClear = false;
            return false;
        } else if (isDiscount == null || cuisineTypes.isEmpty()) {
            isClear = false;
            return false;
        } else if (rating == null || rating.isEmpty()) {
            isClear = false;
            return false;
        } else if (openClose == null || openClose.isEmpty()) {
            isClear = false;
            return false;
        }

        isClear = true;
        return true;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }
}