package com.os.foodie.data.prefs;

import com.os.foodie.data.DataManager;

public interface PreferencesHelper {

    boolean isCurrentUserLoggedIn();

    void setCurrentUserLoggedIn(boolean setLoggedIn);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentUserType();

    void setCurrentUserType(String userType);

    boolean isFacebook();

    void setFacebook(boolean setFacebook);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    boolean isCurrentUserInfoInitialized();

    void setCurrentUserInfoInitialized(boolean initialized);

    String getRestaurantLogoURL();

    void setRestaurantLogoURL(String logoURL);

    String getCustomerRestaurantId();

    void setCustomerRestaurantId(String restaurantId);

    String getNotificationStatus();

    void setNotificationStatus(String status);

    String getCurrency();

    void setCurrency(String currency);

    String getDeviceId();

    void setDeviceId(String status);

    String getLanguage();

    void setLanguage(String language);

    String getLatitude();

    String getLongitude();

    String getCityName();

    void setLatLng(String latitude, String longitude);

    void setCityName(String cityName);


//    String getCurrentUserEmail();
//
//    void setCurrentUserEmail(String email);
//
//    String getCurrentUserProfilePicUrl();
//
//    void setCurrentUserProfilePicUrl(String profilePicUrl);
//
//    String getAccessToken();
//
//    void setAccessToken(String accessToken);

}
