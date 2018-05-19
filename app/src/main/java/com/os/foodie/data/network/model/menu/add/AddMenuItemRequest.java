
package com.os.foodie.data.network.model.menu.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMenuItemRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("veg_nonveg")
    @Expose
    private String vegNonveg;
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
    @SerializedName("avail")
    @Expose
    private String avail;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddMenuItemRequest() {
    }

    /**
     * 
     * @param price
     * @param vegNonveg
     * @param description
     * @param name
     * @param avail
     * @param courseId
     * @param restaurantId
     */
    public AddMenuItemRequest(String restaurantId, String dishId, String courseId, String vegNonveg, String name, String description, String price, String avail, String itemNameArabic) {
        super();
        this.restaurantId = restaurantId;
        this.dishId = dishId;
        this.courseId = courseId;
        this.vegNonveg = vegNonveg;
        this.name = name;
        this.description = description;
        this.price = price;
        this.avail = avail;
        this.nameArabic=itemNameArabic;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getVegNonveg() {
        return vegNonveg;
    }

    public void setVegNonveg(String vegNonveg) {
        this.vegNonveg = vegNonveg;
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

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }
}
