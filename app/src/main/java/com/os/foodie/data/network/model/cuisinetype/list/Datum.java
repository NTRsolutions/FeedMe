
package com.os.foodie.data.network.model.cuisinetype.list;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("cuisine_type")
    @Expose
    private List<CuisineType> cuisineType = null;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            in.readList(instance.cuisineType, (CuisineType.class.getClassLoader()));
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
     * @param cuisineType
     */
    public Datum(List<CuisineType> cuisineType) {
        super();
        this.cuisineType = cuisineType;
    }

    public List<CuisineType> getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(List<CuisineType> cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cuisineType);
    }

    public int describeContents() {
        return  0;
    }

}
