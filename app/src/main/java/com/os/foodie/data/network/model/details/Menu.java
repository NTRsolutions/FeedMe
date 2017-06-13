
package com.os.foodie.data.network.model.details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("course_type")
    @Expose
    private String courseType;
    @SerializedName("dish")
    @Expose
    private List<Dish> dish = null;

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

}
