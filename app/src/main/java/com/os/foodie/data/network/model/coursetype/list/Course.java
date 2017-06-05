
package com.os.foodie.data.network.model.coursetype.list;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<Course> CREATOR = new Creator<Course>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Course createFromParcel(Parcel in) {
            Course instance = new Course();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Course[] newArray(int size) {
            return (new Course[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Course() {
    }

    /**
     * @param id
     * @param name
     */
    public Course(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}
