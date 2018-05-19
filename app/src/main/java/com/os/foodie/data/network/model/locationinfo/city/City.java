
package com.os.foodie.data.network.model.locationinfo.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City implements Parcelable {

    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("name")
    @Expose
    private String name;
    private transient boolean isChecked;

    public final static Creator<City> CREATOR = new Creator<City>() {

        @SuppressWarnings({"unchecked"})
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    /**
     * No args constructor for use in serialization
     */
    public City() {
    }

    protected City(Parcel in) {
        this.cityId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.isChecked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    /**
     * @param cityId
     * @param name
     */
    public City(String cityId, String name) {
        super();
        this.cityId = cityId;
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cityId);
        dest.writeValue(name);
        dest.writeValue(isChecked);
    }
}
