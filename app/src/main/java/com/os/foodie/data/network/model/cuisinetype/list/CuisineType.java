
package com.os.foodie.data.network.model.cuisinetype.list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CuisineType implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    //    @SerializedName("isChecked")
//    @Expose
//    @Expose(deserialize = false)
    @Expose(serialize = false)
    private boolean isChecked;

    public final static Creator<CuisineType> CREATOR = new Creator<CuisineType>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CuisineType createFromParcel(Parcel in) {
            CuisineType instance = new CuisineType();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.isChecked = ((boolean) in.readValue((boolean.class.getClassLoader())));
            return instance;
        }

        public CuisineType[] newArray(int size) {
            return (new CuisineType[size]);
        }
    };

    /**
     * No args constructor for use in serialization
     */
    public CuisineType() {
    }

    /**
     * @param id   //     * @param isChecked
     * @param name
     */
    public CuisineType(String id, String name/*, boolean isChecked*/) {
        super();
        this.id = id;
        this.name = name;
//        this.isChecked = isChecked;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(isChecked);
    }

    public int describeContents() {
        return 0;
    }

}
