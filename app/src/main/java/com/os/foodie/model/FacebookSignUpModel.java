package com.os.foodie.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class FacebookSignUpModel implements Parcelable {

    private String fbId;
    private String firstName;
    private String lastName;
    private String contactPersonName;
    private String email;
    private Boolean isCustomer;

    public final static Parcelable.Creator<FacebookSignUpModel> CREATOR = new Creator<FacebookSignUpModel>() {

        @SuppressWarnings({
                "unchecked"
        })
        public FacebookSignUpModel createFromParcel(Parcel in) {
            FacebookSignUpModel instance = new FacebookSignUpModel();
            instance.fbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.firstName = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastName = ((String) in.readValue((String.class.getClassLoader())));
            instance.contactPersonName = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.isCustomer = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            return instance;
        }

        public FacebookSignUpModel[] newArray(int size) {
            return (new FacebookSignUpModel[size]);
        }
    };

    /**
     * No args constructor for use in serialization
     */
    public FacebookSignUpModel() {
    }

    /**
     * @param fbId
     * @param lastName
     * @param isCustomer
     * @param email
     * @param contactPersonName
     * @param firstName
     */
    public FacebookSignUpModel(String fbId, String firstName, String lastName, String contactPersonName, String email, Boolean isCustomer) {
        super();
        this.fbId = fbId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactPersonName = contactPersonName;
        this.email = email;
        this.isCustomer = isCustomer;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(fbId);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(contactPersonName);
        dest.writeValue(email);
        dest.writeValue(isCustomer);
    }

    public int describeContents() {
        return 0;
    }
}