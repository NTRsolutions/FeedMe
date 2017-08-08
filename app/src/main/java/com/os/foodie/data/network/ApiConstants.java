package com.os.foodie.data.network;

public class ApiConstants {

//
//        OLD
    public static final String BASE_URL = "http://192.168.1.69/foodi/web_services/";
//
//    OLD LIVE
//    public static final String BASE_URL = "http://67.205.96.105:8080/foodi/web_services/";
//
//    NEW LIVE
//     public static final String BASE_URL = "https://56.octallabs.com/foodi/web_services/";


    public static final String LOGIN = "login";
    public static final String FB_LOGIN = "fb_login";

    public static final String CUSTOMER_SIGNUP = "customer_signup";
    public static final String RESTAURANT_SIGNUP = "restaurant_signup";

    public static final String OTP_VERIFICATION = "otp_confirmation";
    public static final String OTP_RESEND= "resend_otp";
    public static final String FORGOT_PASSWORD = "forgot_password";

    public static final String COUNTRY_LIST = "get_country_list";
    public static final String CITY_LIST = "get_city_list";

    public static final String SET_USER_LOCATION = "set_user_location";

    public static final String CUISINE_TYPE_LIST = "get_cuisine_type";
    public static final String ADD_CUISINE_TYPE = "add_cuisine_type";

    public static final String SET_RESTAURANT_PROFILE = "set_restaurant_profile";
    public static final String GET_RESTAURANT_PROFILE = "get_restaurant_profile";
    public static final String DELETE_RESTAURNT_IMAGES = "delete_image";

    public static final String GET_RESTAURANT_MENU = "get_restaurant_menu_item";
    public static final String ADD_RESTAURANT_MENU_ITEM = "add_menu_item";

    public static final String ACTIVE_INACTIVE_MENU_ITEM = "active_inactive_menu_item";
    public static final String DELETE_MENU_ITEM = "delete_menu_item";

    public static final String COURSE_TYPE_LIST = "get_course_type";
    public static final String ADD_COURSE_TYPE = "add_course_type";

    public static final String GET_RESTAURANT_LIST = "get_restaurant_list";
    public static final String GET_RESTAURANT_DETAILS = "get_restaurant_detail";

    public static final String RESTAURANT_LIKE = "restaurant_like";

    public static final String CHANGE_PASSWORD = "change_password";

    public static final String GET_CUSTOMER_PROFILE = "get_customer_profile";
    public static final String EDIT_CUSTOMER_PROFILE = "edit_customer_profile";

    public static final String EDIT_RESTAURANT_ACCOUNT = "edit_restaurant_account";

    public static final String ADD_TO_CART = "add_cart";
    public static final String UPDATE_CART = "edit_cart";
    public static final String REMOVE_FROM_CART = "remove_dish_from_cart";
    public static final String CART_LIST = "cart_list";
    public static final String CART_DETAIL = "cart_detail";
    public static final String CLEAR_CART = "remove_user_cart";

    public static final String GET_ADDRESS = "get_user_address";
    public static final String ADD_ADDRESS = "add_user_address";
    public static final String UPDATE_ADDRESS = "update_address";
    public static final String DELETE_ADDRESS = "remove_user_address";

    public static final String ADD_PAYMENT_CARD = "add_payment_card";
    public static final String GET_ALL_PAYMENT_CARD = "get_all_cards";
    public static final String DELETE_PAYMENT_CARD = "card_delete";

    public static final String CHECKOUT = "checkout";

    public static final String ORDER_LIST = "order_list";
    public static final String ACCEPT_REJECT_ORDER = "accept_order";

    public static final String GET_DISH_LIST = "get_dish_list";
    public static final String ADD_DISCOUNT = "add_discount";
    public static final String DISCOUNT_LIST = "discount_list";
    public static final String DELETE_DISCOUNT = "delete_discount";

    public static final String ORDER_HISTORY = "order_history";
    public static final String CUSTOMER_ORDER_HISTORY = "customer_order_history";
    public static final String ORDER_DETAIL = "order_detail";
    public static final String REORDER = "reorder";

    public static final String REVIEW = "review_list";

    public static final String EARNING = "get_earnings";

    public static final String CHANGE_ORDER_STATUS = "change_order_status";
    public static final String GIVE_REVIEW = "give_review";

    public static final String SET_MERCHANT_DETAIL = "set_restaurant_account_detail";
    public static final String GET_MERCHANT_DETAIL = "get_restaurant_account_detail";

    public static final String GET_PAGE = "get_page";
    public static final String SET_NOTIFICATION = "set_notification";

    public static final String GET_NOTIFICATION_LIST = "get_notification_list";
    public static final String READ_NOTIFICATION = "read_notification";

    public static final String CHANGE_LANGUAGE = "change_language";

    public static final String LOGOUT = "logout";
}