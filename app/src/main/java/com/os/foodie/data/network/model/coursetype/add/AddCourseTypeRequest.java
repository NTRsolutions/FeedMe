
package com.os.foodie.data.network.model.coursetype.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCourseTypeRequest {

    @SerializedName("course_type")
    @Expose
    private String courseType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddCourseTypeRequest() {
    }

    /**
     * 
     * @param courseType
     */
    public AddCourseTypeRequest(String courseType) {
        super();
        this.courseType = courseType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

}
