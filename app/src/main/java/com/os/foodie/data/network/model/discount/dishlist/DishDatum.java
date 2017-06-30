
package com.os.foodie.data.network.model.discount.dishlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DishDatum implements Parcelable
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;




    public final static Creator<DishDatum> CREATOR = new Creator<DishDatum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DishDatum createFromParcel(Parcel in) {
            DishDatum instance = new DishDatum();
            instance.dishId = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.courseId = ((String) in.readValue((String.class.getClassLoader())));
            instance.courseName = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.price = ((String) in.readValue((String.class.getClassLoader())));
            instance.avail = ((String) in.readValue((String.class.getClassLoader())));
            instance.vegNonveg = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DishDatum[] newArray(int size) {
            return (new DishDatum[size]);
        }

    }
    ;

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
    }

    public int describeContents() {
        return  0;
    }

}
