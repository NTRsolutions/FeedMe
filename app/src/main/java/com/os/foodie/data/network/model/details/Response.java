
package com.os.foodie.data.network.model.details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("is_like")
    @Expose
    private Integer isLike;
    @SerializedName("in_cart")
    @Expose
    private Integer inCart;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("avg_rating")
    @Expose
    private String avgRating;
    @SerializedName("review_count")
    @Expose
    private Integer reviewCount;
    @SerializedName("like_count")
    @Expose
    private Integer likeCount;
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
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("delivery_zipcode")
    @Expose
    private String deliveryZipcode;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_cart_quantity")
    @Expose
    private String totalQuantity;
    @SerializedName("total_cart_amount")
    @Expose
    private String totalAmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param cuisineType
     * @param likeCount
     * @param deliveryZipcode
     * @param minOrderAmount
     * @param id
     * @param description
     * @param zipCode
     * @param longitude
     * @param contactPersonName
     * @param inCart
     * @param restaurantName
     * @param logo
     * @param status
     * @param deliveryType
     * @param paymentMethod
     * @param menu
     * @param deliveryCharge
     * @param message
     * @param deliveryTime
     * @param address
     * @param isLike
     * @param email
     * @param closingTime
     * @param workingDays
     * @param images
     * @param latitude
     * @param mobileNumber
     * @param openingTime
     * @param avgRating
     * @param reviewCount
     */
    public Response(Integer isLike, Integer inCart, String id, String avgRating, Integer reviewCount, Integer likeCount, String restaurantName, String minOrderAmount, String contactPersonName, String address, String latitude, String longitude, String zipCode, String openingTime, String closingTime, String description, String cuisineType, String workingDays, String mobileNumber, String email, String deliveryTime, String deliveryCharge, String deliveryType, String paymentMethod, String deliveryZipcode, List<Image> images, String logo, List<Menu> menu, Integer status, String message, String totalQuantity, String totalAmount) {
        super();
        this.isLike = isLike;
        this.inCart = inCart;
        this.id = id;
        this.avgRating = avgRating;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
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
        this.paymentMethod = paymentMethod;
        this.deliveryZipcode = deliveryZipcode;
        this.images = images;
        this.logo = logo;
        this.menu = menu;
        this.status = status;
        this.message = message;
        this.totalQuantity = totalQuantity;
        this.totalAmount= totalAmount;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getInCart() {
        return inCart;
    }

    public void setInCart(Integer inCart) {
        this.inCart = inCart;
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

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
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

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
