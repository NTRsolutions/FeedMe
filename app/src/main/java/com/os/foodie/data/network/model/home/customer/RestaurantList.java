
package com.os.foodie.data.network.model.home.customer;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantList implements Parcelable
{

    @SerializedName("is_like")
    @Expose
    private Integer isLike;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("avg_rating")
    @Expose
    private String avgRating;
    @SerializedName("review_count")
    @Expose
    private Integer reviewCount;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
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
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("logo")
    @Expose
    private String logo;
    public final static Creator<RestaurantList> CREATOR = new Creator<RestaurantList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RestaurantList createFromParcel(Parcel in) {
            RestaurantList instance = new RestaurantList();
            instance.isLike = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.avgRating = ((String) in.readValue((String.class.getClassLoader())));
            instance.reviewCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.restaurantName = ((String) in.readValue((String.class.getClassLoader())));
            instance.contactPersonName = ((String) in.readValue((String.class.getClassLoader())));
            instance.minOrderAmount = ((String) in.readValue((String.class.getClassLoader())));
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
            in.readList(instance.images, (com.os.foodie.data.network.model.home.customer.Image.class.getClassLoader()));
            instance.logo = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public RestaurantList[] newArray(int size) {
            return (new RestaurantList[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RestaurantList() {
    }

    /**
     * 
     * @param logo
     * @param cuisineType
     * @param deliveryZipcode
     * @param deliveryType
     * @param minOrderAmount
     * @param deliveryCharge
     * @param reviewCount
     * @param id
     * @param deliveryTime
     * @param email
     * @param isLike
     * @param address
     * @param description
     * @param zipCode
     * @param closingTime
     * @param images
     * @param workingDays
     * @param longitude
     * @param contactPersonName
     * @param latitude
     * @param mobileNumber
     * @param openingTime
     * @param restaurantName
     */
    public RestaurantList(Integer isLike, String id, String avgRating, Integer reviewCount, String restaurantName, String contactPersonName, String minOrderAmount, String address, String latitude, String longitude, String zipCode, String openingTime, String closingTime, String description, String cuisineType, String workingDays, String mobileNumber, String email, String deliveryTime, String deliveryCharge, String deliveryType, String deliveryZipcode, List<Image> images, String logo) {
        super();
        this.isLike = isLike;
        this.id = id;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
        this.restaurantName = restaurantName;
        this.contactPersonName = contactPersonName;
        this.minOrderAmount = minOrderAmount;
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
        this.images = images;
        this.logo = logo;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isLike);
        dest.writeValue(id);
        dest.writeValue(avgRating);
        dest.writeValue(reviewCount);
        dest.writeValue(restaurantName);
        dest.writeValue(contactPersonName);
        dest.writeValue(minOrderAmount);
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
        dest.writeList(images);
        dest.writeValue(logo);
    }

    public int describeContents() {
        return  0;
    }

}
