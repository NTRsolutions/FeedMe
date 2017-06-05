package com.os.foodie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkingDay implements Parcelable {

    private String day;
    private boolean isChecked;

    public final static Parcelable.Creator<WorkingDay> CREATOR = new Creator<WorkingDay>() {

        @SuppressWarnings({
                "unchecked"
        })
        public WorkingDay createFromParcel(Parcel in) {
            WorkingDay instance = new WorkingDay();
            instance.day = ((String) in.readValue((String.class.getClassLoader())));
            instance.isChecked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            return instance;
        }

        public WorkingDay[] newArray(int size) {
            return (new WorkingDay[size]);
        }
    };

    public WorkingDay() {
    }

    public WorkingDay(String day, boolean isChecked) {
        this.day = day;
        this.isChecked = isChecked;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(day);
        dest.writeValue(isChecked);
    }

    public int describeContents() {
        return 0;
    }
}