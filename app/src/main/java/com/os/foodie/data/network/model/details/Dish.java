
package com.os.foodie.data.network.model.details;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish implements Parcelable
{

    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("avail")
    @Expose
    private String avail;
    @SerializedName("veg_nonveg")
    @Expose
    private String vegNonveg;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("qty")
    @Expose
    private String qty;
    public final static Creator<Dish> CREATOR = new Creator<Dish>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Dish createFromParcel(Parcel in) {
            Dish instance = new Dish();
            instance.dishId = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.courseId = ((String) in.readValue((String.class.getClassLoader())));
            instance.courseName = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.price = ((String) in.readValue((String.class.getClassLoader())));
            instance.avail = ((String) in.readValue((String.class.getClassLoader())));
            instance.vegNonveg = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.qty = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Dish[] newArray(int size) {
            return (new Dish[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Dish() {
    }

    /**
     * 
     * @param price
     * @param vegNonveg
     * @param status
     * @param description
     * @param name
     * @param avail
     * @param courseId
     * @param dishId
     * @param courseName
     */
    public Dish(String dishId, String name, String courseId, String courseName, String description, String price, String avail, String vegNonveg, String status, String qty) {
        super();
        this.dishId = dishId;
        this.name = name;
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.avail = avail;
        this.vegNonveg = vegNonveg;
        this.status = status;
        this.qty = qty;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getVegNonveg() {
        return vegNonveg;
    }

    public void setVegNonveg(String vegNonveg) {
        this.vegNonveg = vegNonveg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(dishId);
        dest.writeValue(name);
        dest.writeValue(courseId);
        dest.writeValue(courseName);
        dest.writeValue(description);
        dest.writeValue(price);
        dest.writeValue(avail);
        dest.writeValue(vegNonveg);
        dest.writeValue(status);
        dest.writeValue(qty);
    }

    public int describeContents() {
        return  0;
    }

}
