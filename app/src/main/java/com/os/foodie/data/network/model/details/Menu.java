
package com.os.foodie.data.network.model.details;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu implements Parcelable
{

    @SerializedName("course_type")
    @Expose
    private String courseType;
    @SerializedName("dish")
    @Expose
    private List<Dish> dish = null;
    public final static Creator<Menu> CREATOR = new Creator<Menu>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Menu createFromParcel(Parcel in) {
            Menu instance = new Menu();
            instance.courseType = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.dish, (com.os.foodie.data.network.model.details.Dish.class.getClassLoader()));
            return instance;
        }

        public Menu[] newArray(int size) {
            return (new Menu[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Menu() {
    }

    /**
     * 
     * @param dish
     * @param courseType
     */
    public Menu(String courseType, List<Dish> dish) {
        super();
        this.courseType = courseType;
        this.dish = dish;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public List<Dish> getDish() {
        return dish;
    }

    public void setDish(List<Dish> dish) {
        this.dish = dish;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(courseType);
        dest.writeList(dish);
    }

    public int describeContents() {
        return  0;
    }

}
