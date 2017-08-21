package com.os.foodie.utils;

import android.content.SharedPreferences;

import org.intellij.lang.annotations.Language;

public class AppConstants {

    //    Splash
    public static final int SPLASH_DURATION = 3;

    //    Veg - Non Veg Types
    public static final String VEG = "Vg";
    public static final String NONVEG = "NonVg";

    //    User Types
    public static final String CUSTOMER = "Customer";
    public static final String RESTAURANT = "Restaurant";

    //    SharedPreferences File Name
    public static final String PREFERENCE_DEFAULT = "PREFERENCE_DEFAULT";

    //    Payment Methods
    public static final String COD = "Cash on delivery";
    public static final String ONLINE = "Online";

    //    Fragment Arguments
    public static final String VIEWPAGER_POSITION = "VIEWPAGER_POSITION";
    public static final String CUISINE_TYPES_ARRAYLIST = "CUISINE_TYPES_ARRAYLIST";
    public static final String IS_FAB_NEEDED = "IS_FAB_NEEDED";
    public static final String COURSE_TYPES_ARRAYLIST = "COURSE_TYPES_ARRAYLIST";
    public static final String EDIT_DISH = "EDIT_DISH";
    public static final String DISH_ARRAYLIST = "DISH_ARRAYLIST";
    public static final String WORKING_DAYS_ARRAYLIST = "WORKING_DAYS_ARRAYLIST";

    //    Slide Arguments
    public static final int TOTAL_SLIDE = 4;

    //    Intent Extras
    public static final String PASSWORD_RESET_MESSAGE = "PASSWORD_RESET_MESSAGE";
    public static final String FACEBOOK_SIGN_UP_MODEL = "FACEBOOK_SIGN_UP_MODEL";
    public static final String FRESCO_URL_LIST = "FRESCO_URL_LIST";
    public static final String RESTAURANT_ID = "RESTAURANT_ID";
    public static final String RESTAURANT_DETAILS = "RESTAURANT_DETAILS";
    public static final String DELIVERY_ADDRESS = "DELIVERY_ADDRESS";
    public static final String POSITION = "POSITION";
    public static final String CARD = "CARD";
    public static final String CHECKOUT = "CHECKOUT";
    public static final String CUISINE_SEARCH = "CUISINE_SEARCH";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String USER_ID = "USER_ID";
    public static final String OTP = "OTP";
    public static final String ORDER_STATUS = "ORDER_STATUS";
//    public static final String STEP = "STEP";

    public static final String DISH_LIST_ARRAYLIST = "DISH_LIST_ARRAYLIST";
    public static final String DISCOUNT_EDIT_DATA = "discount_edit_data";
    public static final String EXIST_DISH_IDS = "EXIST_DISH_IDS";

    public static final String SLUG = "slug";
    public static final String PAGE_NAME = "page_name";

    public static final String TERMS_AND_CONDITION_SLUG = "terms-and-conditions";
    public static final String TERMS_AND_CONDITION_PAGE_NAME = "Terms of Use";

    public static final String PRIVACY_POLICY_SLUG = "privacy-policy";
    public static final String PRIVACY_POLICY_PAGE_NAME = "Privacy Policy";

    public static final String FAQ_SLUG = "faq";
    public static final String FAQ_PAGE_NAME = "FAQ";

    //  Languages
    public static final String LANG_ENG = "eng";
    public static final String LANG_EN = "en";
    public static final String LANG_AR = "ar";

    //    Notification Types
    public static final String NOTIFICATION_TYPE_ORDER_RECEIVED = "order_received";
    public static final String NOTIFICATION_TYPE_ORDER_ACCEPTED = "order_accept";
    public static final String NOTIFICATION_TYPE_ORDER_REJECTED = "order_reject";
    public static final String NOTIFICATION_TYPE_ORDER_READY_PICK = "ready_to_pick";
    public static final String NOTIFICATION_TYPE_ORDER_READY_DELIVER = "ready_to_deliver";
    public static final String NOTIFICATION_TYPE_ORDER_IN_TRANSIT = "in_transit";
    public static final String NOTIFICATION_TYPE_ORDER_PICKED = "picked";
    public static final String NOTIFICATION_TYPE_ORDER_DELIVERED = "delivered";

    //    Package
    public static String PACKAGE_FACEBOOK = "com.facebook";
    public static String PACKAGE_FACEBOOK_KATANA = "com.facebook.katana";
    public static String PACKAGE_TWITTER = "com.twitter.android";
    public static String PACKAGE_GOOGLE_PLUS = "com.google.android.apps.plus";

    public static String PACKAGE_GMAIL = "com.google.android.gm";

    public static String playStoreURL = "market://details?id=";
    public static String SDCARD_PATH = "sdcard/Foodi/";
    public static String dummyTextShare = "Foodi !";
    public static String NO_INTERNET_CONNECTION = "No Internet Connected";
    public static String INTENT_TYPE = "image/*";


    //    Order Status
    public static final String ORDER_DECLINE = "decline";
    public static final String ORDER_UNDER_PREPARATION = "under preparation";

//    public static final String CUSTOMER_SIGN_UP_REQUEST = "CUSTOMER_SIGN_UP_REQUEST";
//    public static final String RESTAURANT_SIGN_UP_REQUEST = "RESTAURANT_SIGN_UP_REQUEST";
//
//    public static final String CUSTOMER_SIGN_UP_RESPONSE = "CUSTOMER_SIGN_UP_RESPONSE";
//    public static final String RESTAURANT_SIGN_UP_RESPONSE = "RESTAURANT_SIGN_UP_RESPONSE";
//
//    public static final String CUSTOMER_OTP_VERIFICATION = "CUSTOMER_OTP_VERIFICATION";
//    public static final String RESTAURANT_OTP_VERIFICATION = "RESTAURANT_OTP_VERIFICATION";

    //    Others
    public static final long NULL_INDEX = -1L;

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

}