package com.os.foodie.data.prefs;

import com.os.foodie.data.DataManager;

public interface PreferencesHelper {

    boolean isCurrentUserLoggedIn();

    void setCurrentUserLoggedIn(boolean setLoggedIn);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentUserType();

    void setCurrentUserType(String userType);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    boolean isCurrentUserInfoInitialized();

    void setCurrentUserInfoInitialized(boolean initialized);

    String getRestaurantLogoURL();

    void setRestaurantLogoURL(String logoURL);
//
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
