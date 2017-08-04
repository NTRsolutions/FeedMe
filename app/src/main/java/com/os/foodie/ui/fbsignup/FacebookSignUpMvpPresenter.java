package com.os.foodie.ui.fbsignup;

import com.os.foodie.model.FacebookSignUpModel;
import com.os.foodie.ui.base.MvpPresenter;

import java.io.File;
import java.util.HashMap;

public interface FacebookSignUpMvpPresenter<V extends FacebookSignUpMvpView> extends MvpPresenter<V> {

    void onSubmit(FacebookSignUpModel facebookSignUpModel, String restaurantName, String countryCode, String phone, String deviceId, String deviceType, String Latitude, String Longitude, String language, HashMap<String, File> fileMap);

    String getDeviceId();

    void dispose();
}