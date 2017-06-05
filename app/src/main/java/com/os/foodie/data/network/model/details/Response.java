
package com.os.foodie.data.network.model.details;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("is_like")
    @Expose
    private String isLike;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
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
    @SerializedName("min_order_amount")
    @Expose
    private String minOrderAmount;
    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;
    @SerializedName("delivery_zipcode")
    @Expose
    private String deliveryZipcode;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;
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
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.isLike = ((String) in.readValue((String.class.getClassLoader())));
            instance.logo = ((String) in.readValue((String.class.getClassLoader())));
            instance.restaurantName = ((String) in.readValue((String.class.getClassLoader())));
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
            instance.minOrderAmount = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryType = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryZipcode = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.images, (com.os.foodie.data.network.model.details.Image.class.getClassLoader()));
            in.readList(instance.menu, (com.os.foodie.data.network.model.details.Menu.class.getClassLoader()));
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
     * @param cuisineType
     * @param deliveryZipcode
     * @param minOrderAmount
     * @param id
     * @param description
     * @param zipCode
     * @param longitude
     * @param contactPersonName
     * @param restaurantName
     * @param logo
     * @param deliveryType
     * @param status
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
     */
    public Response(String id, String isLike, String logo, String restaurantName, String contactPersonName, String address, String latitude, String longitude, String zipCode, String openingTime, String closingTime, String description, String cuisineType, String workingDays, String mobileNumber, String email, String deliveryTime, String deliveryCharge, String minOrderAmount, String deliveryType, String deliveryZipcode, List<Image> images, List<Menu> menu, Integer status, String message) {
        super();
        this.id = id;
        this.isLike = isLike;
        this.logo = logo;
        this.restaurantName = restaurantName;
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
        this.minOrderAmount = minOrderAmount;
        this.deliveryType = deliveryType;
        this.deliveryZipcode = deliveryZipcode;
        this.images = images;
        this.menu = menu;
        this.status = status;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(isLike);
        dest.writeValue(logo);
        dest.writeValue(restaurantName);
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
        dest.writeValue(minOrderAmount);
        dest.writeValue(deliveryType);
        dest.writeValue(deliveryZipcode);
        dest.writeList(images);
        dest.writeList(menu);
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
