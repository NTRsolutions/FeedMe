
package com.os.foodie.data.network.model.setupprofile.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetupRestaurantProfileRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("cuisine_type")
    @Expose
    private String cuisineType;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("working_days")
    @Expose
    private String workingDays;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("delivery_zipcode")
    @Expose
    private String deliveryZipcode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("delete_image_id")
    @Expose
    private String deleteImageId;

    @SerializedName("currency")
    @Expose
    private String currency;

    /**
     * No args constructor for use in serialization
     */
    public SetupRestaurantProfileRequest() {
    }

    /**
     * @param cuisineType
     * @param deliveryZipcode
     * @param deliveryType
     * @param minOrderAmount
     * @param deliveryCharge
     * @param deliveryTime
     * @param address
     * @param description
     * @param closingTime
     * @param zipCode
     * @param workingDays
     * @param longitude
     * @param latitude
     * @param openingTime
     * @param restaurantId
     * @param paymentMethod
     * @param country
     * @param city
     */
    public SetupRestaurantProfileRequest(String restaurantId, String cuisineType, String minOrderAmount, String paymentMethod, String deliveryCharge, String deliveryTime, String workingDays, String openingTime, String closingTime, String deliveryType, String address, String country, String city, String zipCode, String deliveryZipcode, String description, String latitude, String longitude) {
        super();
        this.restaurantId = restaurantId;
        this.cuisineType = cuisineType;
        this.minOrderAmount = minOrderAmount;
        this.paymentMethod = paymentMethod;
        this.deliveryCharge = deliveryCharge;
        this.deliveryTime = deliveryTime;
        this.workingDays = workingDays;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.deliveryType = deliveryType;
        this.address = address;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.deliveryZipcode = deliveryZipcode;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
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

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDeliveryZipcode() {
        return deliveryZipcode;
    }

    public void setDeliveryZipcode(String deliveryZipcode) {
        this.deliveryZipcode = deliveryZipcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDeleteImageId() {
        return deleteImageId;
    }

    public void setDeleteImageId(String deleteImageId) {
        this.deleteImageId = deleteImageId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
