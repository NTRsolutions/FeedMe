package com.os.foodie.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_LOGGED_IN = "PREF_KEY_USER_LOGGED_IN";
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_TYPE = "PREF_KEY_CURRENT_USER_TYPE";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_USER_INITIALIZED = "PREF_KEY_CURRENT_USER_INITIALIZED";
    private static final String PREF_KEY_RESTAURANT_LOGO_URL = "PREF_KEY_RESTAURANT_LOGO_URL";

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