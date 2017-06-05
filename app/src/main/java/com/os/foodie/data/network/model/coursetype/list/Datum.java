
package com.os.foodie.data.network.model.coursetype.list;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            in.readList(instance.courses, (com.os.foodie.data.network.model.coursetype.list.Course.class.getClassLoader()));
            return instance;
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param courses
     */
    public Datum(List<Course> courses) {
        super();
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(courses);
    }

    public int describeContents() {
        return  0;
    }

}
