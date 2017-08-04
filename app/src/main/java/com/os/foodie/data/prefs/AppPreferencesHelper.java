package com.os.foodie.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_LOGGED_IN = "PREF_KEY_USER_LOGGED_IN";
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_IS_FACEBOOK = "PREF_KEY_IS_FACEBOOK";
    private static final String PREF_KEY_CURRENT_USER_TYPE = "PREF_KEY_CURRENT_USER_TYPE";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_USER_INITIALIZED = "PREF_KEY_CURRENT_USER_INITIALIZED";
    private static final String PREF_KEY_RESTAURANT_LOGO_URL = "PREF_KEY_RESTAURANT_LOGO_URL";
    private static final String PREF_KEY_CUSTOMER_RESTAURANT_ID = "PREF_KEY_CUSTOMER_RESTAURANT_ID";
    private static final String PREF_KEY_NOTIFICATION_STATUS = "PREF_KEY_NOTIFICATION_STATUS";
    private static final String PREF_KEY_CURRENCY = "PREF_KEY_CURRENCY";
    private static final String PREF_KEY_DEVICE_ID = "PREF_KEY_DEVICE_ID";
    private static final String PREF_KEY_LANGUAGE = "PREF_KEY_LANGUAGE";
    private static final String PREF_KEY_LATITUDE = "PREF_KEY_LATITUDE";
    private static final String PREF_KEY_LONGITUDE = "PREF_KEY_LONGITUDE";
    private static final String PREF_KEY_CITY = "PREF_KEY_CITY";

    private final SharedPreferences sharedPreferences;

    public AppPreferencesHelper(Context context, String prefFileName) {
        sharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isCurrentUserLoggedIn() {
        return sharedPreferences.getBoolean(PREF_KEY_USER_LOGGED_IN, false);
    }

    @Override
    public void setCurrentUserLoggedIn(boolean userLoggedIn) {
        sharedPreferences.edit().putBoolean(PREF_KEY_USER_LOGGED_IN, userLoggedIn).apply();
    }

    @Override
    public String getCurrentUserId() {
        return sharedPreferences.getString(PREF_KEY_CURRENT_USER_ID, null);
    }

    @Override
    public void setCurrentUserId(String userId) {
        sharedPreferences.edit().putString(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public String getCurrentUserType() {
        return sharedPreferences.getString(PREF_KEY_CURRENT_USER_TYPE, null);
    }

    @Override
    public void setCurrentUserType(String isCustomer) {
        sharedPreferences.edit().putString(PREF_KEY_CURRENT_USER_TYPE, isCustomer).apply();
    }

    @Override
    public boolean isFacebook() {
        return sharedPreferences.getBoolean(PREF_KEY_IS_FACEBOOK, false);
    }

    @Override
    public void setFacebook(boolean setFacebook) {
        sharedPreferences.edit().putBoolean(PREF_KEY_IS_FACEBOOK, setFacebook).apply();
    }

    @Override
    public String getCurrentUserName() {
        return sharedPreferences.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        sharedPreferences.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

    @Override
    public boolean isCurrentUserInfoInitialized() {
        return sharedPreferences.getBoolean(PREF_KEY_CURRENT_USER_INITIALIZED, false);
    }

    @Override
    public void setCurrentUserInfoInitialized(boolean initialized) {
        sharedPreferences.edit().putBoolean(PREF_KEY_CURRENT_USER_INITIALIZED, initialized).apply();
    }

    @Override
    public String getRestaurantLogoURL() {
        return sharedPreferences.getString(PREF_KEY_RESTAURANT_LOGO_URL, "");
    }

    @Override
    public void setRestaurantLogoURL(String imageURL) {
        sharedPreferences.edit().putString(PREF_KEY_RESTAURANT_LOGO_URL, imageURL).apply();
    }

    @Override
    public String getCustomerRestaurantId() {
        return sharedPreferences.getString(PREF_KEY_CUSTOMER_RESTAURANT_ID, "");
    }

    @Override
    public void setCustomerRestaurantId(String restaurantId) {
        sharedPreferences.edit().putString(PREF_KEY_CUSTOMER_RESTAURANT_ID, restaurantId).apply();
    }

    @Override
    public String getNotificationStatus() {
        return sharedPreferences.getString(PREF_KEY_NOTIFICATION_STATUS, "");
    }

    @Override
    public void setNotificationStatus(String status) {
        sharedPreferences.edit().putString(PREF_KEY_NOTIFICATION_STATUS, status).apply();
    }

    @Override
    public String getCurrency() {
        return sharedPreferences.getString(PREF_KEY_CURRENCY, "");
    }

    @Override
    public void setCurrency(String currency) {
        sharedPreferences.edit().putString(PREF_KEY_CURRENCY, currency).apply();
    }

    @Override
    public String getDeviceId() {
        return sharedPreferences.getString(PREF_KEY_DEVICE_ID, "");
    }

    @Override
    public void setDeviceId(String status) {
        sharedPreferences.edit().putString(PREF_KEY_DEVICE_ID, status).apply();
    }

    @Override
    public String getLanguage() {
        return sharedPreferences.getString(PREF_KEY_LANGUAGE, "en");
    }

    @Override
    public void setLanguage(String language) {
        sharedPreferences.edit().putString(PREF_KEY_LANGUAGE, language).apply();
    }

    @Override
    public String getLatitude() {
        return sharedPreferences.getString(PREF_KEY_LATITUDE, "");
    }

    @Override
    public String getLongitude() {
        return sharedPreferences.getString(PREF_KEY_LONGITUDE, "");
    }

    @Override
    public String getCityName() {
        return sharedPreferences.getString(PREF_KEY_CITY, "");
    }

    @Override
    public void setLatLng(String latitude, String longitude) {
        sharedPreferences.edit().putString(PREF_KEY_LATITUDE, latitude).apply();
        sharedPreferences.edit().putString(PREF_KEY_LONGITUDE, longitude).apply();
    }

    @Override
    public void setCityName(String cityName) {
        sharedPreferences.edit().putString(PREF_KEY_CITY, cityName).apply();
    }

//    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
//    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
//    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";
//    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
//
//      Go Down Temp
//
//    @Override
//    public String getCurrentUserName() {
//        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
//    }
//
//    @Override
//    public void setCurrentUserName(String userName) {
//        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
//    }
//
//    @Override
//    public String getCurrentUserEmail() {
//        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
//    }
//
//    @Override
//    public void setCurrentUserEmail(String email) {
//        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
//    }
//
//    @Override
//    public String getCurrentUserProfilePicUrl() {
//        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
//    }
//
//    @Override
//    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
//        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
//    }
//
//      Start Here Temp
//
//    @Override
//    public String getAccessToken() {
//        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
//    }
//
//    @Override
//    public void setAccessToken(String accessToken) {
//        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
//    }
}