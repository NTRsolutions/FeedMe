package com.os.foodie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cuisine_type")
    @Expose
    private String cuisineType;
    @SerializedName("working_days")
    @Expose
    private String workingDays;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;
    @SerializedName("delivery_zipcode")
    @Expose
    private String deliveryZipcode;
    @SerializedName("total_cart_quantity")
    @Expose
    private String totalQuantity;
    @SerializedName("total_cart_amount")
    @Expose
    private String totalAmount;
    public final static Parcelable.Creator<RestaurantDetails> CREATOR = new Creator<RestaurantDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RestaurantDetails createFromParcel(Parcel in) {
            RestaurantDetails instance = new RestaurantDetails();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantName = ((String) in.readValue((String.class.getClassLoader())));
            instance.minOrderAmount = ((String) in.readValue((String.class.getClassLoader())));
            instance.contactPersonName = ((String) in.readValue((String.class.getClassLoader())));
            instance.address = ((String) in.readValue((String.class.getClassLoader())));
            instance.latitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.longitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.zipCode = ((String) in.readValue((String.class.getClassLoader())));
            instance.openingTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.closingTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.cuisineType = ((String) in.readValue((String.class.getClassLoader())));
            instance.workingDays = ((String) in.readValue((String.class.getClassLoader())));
            instance.mobileNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryCharge = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryType = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryZipcode = ((String) in.readValue((String.class.getClassLoader())));
            instance.totalQuantity = ((String) in.readValue((String.class.getClassLoader())));
            instance.totalAmount = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public RestaurantDetails[] newArray(int size) {
            return (new RestaurantDetails[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public RestaurantDetails() {
    }

    /**
     * @param cuisineType
     * @param deliveryZipcode
     * @param deliveryType
     * @param minOrderAmount
     * @param deliveryCharge
     * @param id
     * @param deliveryTime
     * @param email
     * @param address
     * @param description
     * @param zipCode
     * @param closingTime
     * @param workingDays
     * @param longitude
     * @param contactPersonName
     * @param latitude
     * @param mobileNumber
     * @param openingTime
     * @param restaurantName
     */
    public RestaurantDetails(String id, String restaurantName, String minOrderAmount, String contactPersonName, String address, String latitude, String longitude, String zipCode, String openingTime, String closingTime, String description, String cuisineType, String workingDays, String mobileNumber, String email, String deliveryTime, String deliveryCharge, String deliveryType, String deliveryZipcode, String totalQuantity, String totalAmount) {
        super();
        this.id = id;
        this.restaurantName = restaurantName;
        this.minOrderAmount = minOrderAmount;
        this.contactPersonName = contactPersonName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.description = description;
        this.cuisineType = cuisineType;
        this.workingDays = workingDays;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.deliveryTime = deliveryTime;
        this.deliveryCharge = deliveryCharge;
        this.deliveryType = deliveryType;
        this.deliveryZipcode = deliveryZipcode;
        this.totalQuantity = totalQuantity;
        this.totalAmount= totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryZipcode() {
        return deliveryZipcode;
    }

    public void setDeliveryZipcode(String deliveryZipcode) {
        this.deliveryZipcode = deliveryZipcode;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(restaurantName);
        dest.writeValue(minOrderAmount);
        dest.writeValue(contactPersonName);
        dest.writeValue(address);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(zipCode);
        dest.writeValue(openingTime);
        dest.writeValue(closingTime);
        dest.writeValue(description);
        dest.writeValue(cuisineType);
        dest.writeValue(workingDays);
        dest.writeValue(mobileNumber);
        dest.writeValue(email);
        dest.writeValue(deliveryTime);
        dest.writeValue(deliveryCharge);
        dest.writeValue(deliveryType);
        dest.writeValue(deliveryZipcode);
        dest.writeValue(totalQuantity);
        dest.writeValue(totalAmount);
    }

    public int describeContents() {
        return 0;
    }
}