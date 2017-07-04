package com.os.foodie.data;

import android.content.Context;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.data.network.ApiHelper;
import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountRequest;
import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountRequest;
import com.os.foodie.data.network.model.account.edit.restaurant.EditRestaurantAccountResponse;
import com.os.foodie.data.network.model.account.GetAccountDetailRequest;
import com.os.foodie.data.network.model.account.GetAccountDetailResponse;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.cart.add.AddToCartResponse;
import com.os.foodie.data.network.model.cart.clear.ClearCartRequest;
import com.os.foodie.data.network.model.cart.clear.ClearCartResponse;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartRequest;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartResponse;
import com.os.foodie.data.network.model.cart.update.UpdateCartRequest;
import com.os.foodie.data.network.model.cart.update.UpdateCartResponse;
import com.os.foodie.data.network.model.cart.view.ViewCartRequest;
import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.data.network.model.changepassword.ChangePasswordRequest;
import com.os.foodie.data.network.model.changepassword.ChangePasswordResponse;
import com.os.foodie.data.network.model.checkout.CheckoutRequest;
import com.os.foodie.data.network.model.checkout.CheckoutResponse;
import com.os.foodie.data.network.model.coursetype.add.AddCourseTypeRequest;
import com.os.foodie.data.network.model.coursetype.add.AddCourseTypeResponse;
import com.os.foodie.data.network.model.coursetype.list.GetCourseTypeResponse;
import com.os.foodie.data.network.model.cuisinetype.add.AddCuisineTypeRequest;
import com.os.foodie.data.network.model.cuisinetype.add.AddCuisineTypeResponse;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineTypeResponse;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressResponse;
import com.os.foodie.data.network.model.deliveryaddress.delete.DeleteAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.delete.DeleteAddressResponse;
import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.getall.GetAllAddressResponse;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressResponse;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsRequest;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
import com.os.foodie.data.network.model.discount.add.AddDiscountRequest;
import com.os.foodie.data.network.model.discount.add.AddDiscountResponse;
import com.os.foodie.data.network.model.discount.dishlist.DishListRequest;
import com.os.foodie.data.network.model.discount.dishlist.DishListResponse;
import com.os.foodie.data.network.model.discount.list.DeleteDiscountRequest;
import com.os.foodie.data.network.model.discount.list.DiscountListResponse;
import com.os.foodie.data.network.model.forgotpassword.ForgotPasswordRequest;
import com.os.foodie.data.network.model.forgotpassword.ForgotPasswordResponse;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListRequest;
import com.os.foodie.data.network.model.home.customer.GetRestaurantListResponse;
import com.os.foodie.data.network.model.like.LikeDislikeRequest;
import com.os.foodie.data.network.model.like.LikeDislikeResponse;
import com.os.foodie.data.network.model.locationinfo.city.CityListRequest;
import com.os.foodie.data.network.model.locationinfo.city.CityListResponse;
import com.os.foodie.data.network.model.locationinfo.country.CountryListResponse;
import com.os.foodie.data.network.model.locationinfo.set.SetUserLocationRequest;
import com.os.foodie.data.network.model.locationinfo.set.SetUserLocationResponse;
import com.os.foodie.data.network.model.login.LoginRequest;
import com.os.foodie.data.network.model.login.LoginResponse;
import com.os.foodie.data.network.model.logout.LogoutRequest;
import com.os.foodie.data.network.model.logout.LogoutResponse;
import com.os.foodie.data.network.model.menu.add.AddMenuItemRequest;
import com.os.foodie.data.network.model.menu.add.AddMenuItemResponse;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemRequest;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemResponse;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuRequest;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuResponse;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemRequest;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemResponse;
import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailRequest;
import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailResponse;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailRequest;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailResponse;
import com.os.foodie.data.network.model.notification.SetNotificationResponse;
import com.os.foodie.data.network.model.order.customer.history.CustomerOrderHistoryRequest;
import com.os.foodie.data.network.model.order.customer.history.CustomerOrderHistoryResponse;
import com.os.foodie.data.network.model.order.restaurant.detail.OrderHistoryDetail;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderRequest;
import com.os.foodie.data.network.model.orderlist.acceptreject.AcceptRejectOrderResponse;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListRequest;
import com.os.foodie.data.network.model.orderlist.show.GetOrderListResponse;
import com.os.foodie.data.network.model.otp.OtpVerificationRequest;
import com.os.foodie.data.network.model.otp.OtpVerificationResponse;
import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardRequest;
import com.os.foodie.data.network.model.payment.addcard.AddPaymentCardResponse;
import com.os.foodie.data.network.model.payment.delete.DeletePaymentCardRequest;
import com.os.foodie.data.network.model.payment.delete.DeletePaymentCardResponse;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardRequest;
import com.os.foodie.data.network.model.payment.getall.GetAllPaymentCardResponse;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileRequest;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileResponse;
import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.network.model.fblogin.FacebookLoginRequest;
import com.os.foodie.data.network.model.fblogin.FacebookLoginResponse;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpRequest;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpResponse;
import com.os.foodie.data.network.model.staticpage.StaticPageRequest;
import com.os.foodie.data.network.model.staticpage.StaticPageResponse;
import com.os.foodie.data.prefs.PreferencesHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context context;
    private final PreferencesHelper preferencesHelper;
    private final ApiHelper apiHelper;

    //    Temporary
    public AppDataManager(Context context, PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        this.context = context;
        this.preferencesHelper = preferencesHelper;
        this.apiHelper = apiHelper;
    }

    @Override
    public boolean isCurrentUserLoggedIn() {
        return preferencesHelper.isCurrentUserLoggedIn();
    }

    @Override
    public void setCurrentUserLoggedIn(boolean userLoggedIn) {

        if (!userLoggedIn) {
            setCurrentUserId(null);
            setCurrentUserType(null);
            setCurrentUserInfoInitialized(false);
        }

        preferencesHelper.setCurrentUserLoggedIn(userLoggedIn);
    }

    @Override
    public String getCurrentUserId() {
        return preferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        preferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserType() {
        return preferencesHelper.getCurrentUserType();
    }

    @Override
    public void setCurrentUserType(String userType) {
        preferencesHelper.setCurrentUserType(userType);
    }

    @Override
    public String getCurrentUserName() {
        return preferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        preferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public boolean isCurrentUserInfoInitialized() {
        return preferencesHelper.isCurrentUserInfoInitialized();
    }

    @Override
    public void setCurrentUserInfoInitialized(boolean initialized) {
        preferencesHelper.setCurrentUserInfoInitialized(initialized);
    }

    @Override
    public String getRestaurantLogoURL() {
        return preferencesHelper.getRestaurantLogoURL();
    }

    @Override
    public void setRestaurantLogoURL(String imageURL) {
        preferencesHelper.setRestaurantLogoURL(imageURL);
    }

    @Override
    public String getCustomerRestaurantId() {
        return preferencesHelper.getCustomerRestaurantId();
    }

    @Override
    public void setCustomerRestaurantId(String restaurantId) {
        preferencesHelper.setCustomerRestaurantId(restaurantId);
    }

    @Override
    public String getNotificationStatus() {
        return preferencesHelper.getNotificationStatus();
    }

    @Override
    public void setNotificationStatus(String status) {
        preferencesHelper.setNotificationStatus(status);
    }

    @Override
    public Observable<CustomerSignUpResponse> doCustomerSignUp(CustomerSignUpRequest customerSignUpRequest) {
        return apiHelper.doCustomerSignUp(customerSignUpRequest);
    }

    @Override
    public Observable<RestaurantSignUpResponse> doRestaurantSignUp(RestaurantSignUpRequest restaurantSignUpRequest, HashMap<String, File> fileMap) {
        return apiHelper.doRestaurantSignUp(restaurantSignUpRequest, fileMap);
    }

    @Override
    public Observable<FacebookLoginResponse> doFacebookLogin(FacebookLoginRequest facebookLoginRequest) {
        return apiHelper.doFacebookLogin(facebookLoginRequest);
    }

    @Override
    public Observable<LoginResponse> doLogin(LoginRequest loginRequest) {
        return apiHelper.doLogin(loginRequest);
    }

    @Override
    public Observable<OtpVerificationResponse> verifyOTP(OtpVerificationRequest otpVerificationRequest) {
        return apiHelper.verifyOTP(otpVerificationRequest);
    }

    @Override
    public Observable<ForgotPasswordResponse> resetPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return apiHelper.resetPassword(forgotPasswordRequest);
    }

    @Override
    public Observable<CountryListResponse> getCountryList() {
        return apiHelper.getCountryList();
    }

    @Override
    public Observable<CityListResponse> getCityList(CityListRequest cityListRequest) {
        return apiHelper.getCityList(cityListRequest);
    }

    @Override
    public Observable<SetUserLocationResponse> setUserLocationInfo(SetUserLocationRequest setUserLocationRequest) {
        return apiHelper.setUserLocationInfo(setUserLocationRequest);
    }

    @Override
    public Observable<CuisineTypeResponse> getCuisineTypeList() {
        return apiHelper.getCuisineTypeList();
    }

    @Override
    public Observable<AddCuisineTypeResponse> addCuisineType(AddCuisineTypeRequest addCuisineTypeRequest) {
        return apiHelper.addCuisineType(addCuisineTypeRequest);
    }

    @Override
    public Observable<SetupRestaurantProfileResponse> setRestaurantProfile(SetupRestaurantProfileRequest restaurantProfileRequest, HashMap<String, File> fileMap) {
        return apiHelper.setRestaurantProfile(restaurantProfileRequest, fileMap);
    }

    @Override
    public Observable<GetRestaurantMenuResponse> getRestaurantMenuList(GetRestaurantMenuRequest restaurantMenuRequest) {
        return apiHelper.getRestaurantMenuList(restaurantMenuRequest);
    }

    @Override
    public Observable<GetCourseTypeResponse> getCourseTypeList() {
        return apiHelper.getCourseTypeList();
    }

    @Override
    public Observable<AddCourseTypeResponse> addCourseType(AddCourseTypeRequest addCourseTypeRequest) {
        return apiHelper.addCourseType(addCourseTypeRequest);
    }

    @Override
    public Observable<List<Address>> getGeocoderLocationAddress(Context context, LatLng latLng) {
        return apiHelper.getGeocoderLocationAddress(context, latLng);
    }

    @Override
    public Observable<GetRestaurantListResponse> getRestaurantList(GetRestaurantListRequest getRestaurantListRequest) {
        return apiHelper.getRestaurantList(getRestaurantListRequest);
    }

    @Override
    public Observable<CustomerRestaurantDetailsResponse> getRestaurantDetails(CustomerRestaurantDetailsRequest restaurantDetailsRequest) {
        return apiHelper.getRestaurantDetails(restaurantDetailsRequest);
    }

    @Override
    public Observable<LikeDislikeResponse> doLikeDislike(LikeDislikeRequest likeDislikeRequest) {
        return apiHelper.doLikeDislike(likeDislikeRequest);
    }

    @Override
    public Observable<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest) {
        return apiHelper.changePassword(changePasswordRequest);
    }

    @Override
    public Observable<GetAccountDetailResponse> getCustomerAccountDetail(GetAccountDetailRequest getAccountDetailRequest) {
        return apiHelper.getCustomerAccountDetail(getAccountDetailRequest);
    }

    @Override
    public Observable<EditCustomerAccountDetailResponse> editCustomerAccount(EditCustomerAccountRequest editCustomerAccountRequest) {
        return apiHelper.editCustomerAccount(editCustomerAccountRequest);
    }

    @Override
    public Observable<AddToCartResponse> addToCart(AddToCartRequest addToCartRequest) {
        return apiHelper.addToCart(addToCartRequest);
    }

    @Override
    public Observable<ViewCartResponse> getCartDetails(ViewCartRequest viewCartRequest) {
        return apiHelper.getCartDetails(viewCartRequest);
    }

    @Override
    public Observable<RemoveFromCartResponse> removeFromCart(RemoveFromCartRequest removeFromCartRequest) {
        return apiHelper.removeFromCart(removeFromCartRequest);
    }

    @Override
    public Observable<UpdateCartResponse> updateCart(UpdateCartRequest updateCartRequest) {
        return apiHelper.updateCart(updateCartRequest);
    }

    @Override
    public Observable<ClearCartResponse> clearCart(ClearCartRequest clearCartRequest) {
        return apiHelper.clearCart(clearCartRequest);
    }

    @Override
    public Observable<AddMenuItemResponse> addRestaurantMenuItem(AddMenuItemRequest addMenuItemRequest) {
        return apiHelper.addRestaurantMenuItem(addMenuItemRequest);
    }

    @Override
    public Observable<StatusMenuItemResponse> changeStatusRestaurantMenuItem(StatusMenuItemRequest statusMenuItemRequest) {
        return apiHelper.changeStatusRestaurantMenuItem(statusMenuItemRequest);
    }

    @Override
    public Observable<DeleteMenuItemResponse> deleteRestaurantMenuItem(DeleteMenuItemRequest deleteMenuItemRequest) {
        return apiHelper.deleteRestaurantMenuItem(deleteMenuItemRequest);
    }

    @Override
    public Observable<RestaurantProfileResponse> getRestaurantProfile(CustomerRestaurantDetailsRequest customerRestaurantDetailsRequest) {
        return apiHelper.getRestaurantProfile(customerRestaurantDetailsRequest);
    }

    @Override
    public Observable<ChangePasswordResponse> deleteRestaurantImage(String restaurantId) {
        return apiHelper.deleteRestaurantImage(restaurantId);
    }

    @Override
    public Observable<EditRestaurantAccountResponse> editRestaurantAccount(EditRestaurantAccountRequest editRestaurantAccountRequest, HashMap<String, File> fileMap) {
        return apiHelper.editRestaurantAccount(editRestaurantAccountRequest, fileMap);
    }

    @Override
    public Observable<GetAllAddressResponse> getAllAddress(GetAllAddressRequest getAllAddressRequest) {
        return apiHelper.getAllAddress(getAllAddressRequest);
    }

    @Override
    public Observable<DeleteAddressResponse> deleteAddress(DeleteAddressRequest deleteAddressRequest) {
        return apiHelper.deleteAddress(deleteAddressRequest);
    }

    @Override
    public Observable<AddDeliveryAddressResponse> addDeliveryAddress(AddDeliveryAddressRequest addDeliveryAddressRequest) {
        return apiHelper.addDeliveryAddress(addDeliveryAddressRequest);
    }

    @Override
    public Observable<UpdateAddressResponse> updateDeliveryAddress(UpdateAddressRequest updateAddressRequest) {
        return apiHelper.updateDeliveryAddress(updateAddressRequest);
    }

    @Override
    public Observable<AddPaymentCardResponse> addPaymentCard(AddPaymentCardRequest addPaymentCardRequest) {
        return apiHelper.addPaymentCard(addPaymentCardRequest);
    }

    @Override
    public Observable<GetAllPaymentCardResponse> getAllPaymentCard(GetAllPaymentCardRequest getAllPaymentCardRequest) {
        return apiHelper.getAllPaymentCard(getAllPaymentCardRequest);
    }

    @Override
    public Observable<DeletePaymentCardResponse> deletePaymentCard(DeletePaymentCardRequest deletePaymentCardRequest) {
        return apiHelper.deletePaymentCard(deletePaymentCardRequest);
    }

    @Override
    public Observable<CheckoutResponse> checkoout(CheckoutRequest checkoutRequest) {
        return apiHelper.checkoout(checkoutRequest);
    }

    @Override
    public Observable<GetOrderListResponse> getOrderList(GetOrderListRequest getOrderListRequest) {
        return apiHelper.getOrderList(getOrderListRequest);
    }

    @Override
    public Observable<AcceptRejectOrderResponse> acceptRejectOrder(AcceptRejectOrderRequest acceptRejectOrderRequest) {
        return apiHelper.acceptRejectOrder(acceptRejectOrderRequest);
    }

    @Override
    public Observable<LogoutResponse> logout(LogoutRequest logoutRequest) {
        return apiHelper.logout(logoutRequest);
    }

    @Override
    public Observable<SetMerchantDetailResponse> setMerchantDetail(SetMerchantDetailRequest merchantDetailRequest) {
        return apiHelper.setMerchantDetail(merchantDetailRequest);
    }

    @Override
    public Observable<GetMerchantDetailResponse> getMerchantDetail(GetMerchantDetailRequest merchantDetailRequest) {
        return apiHelper.getMerchantDetail(merchantDetailRequest);
    }

//    Abhinav

    @Override
    public Observable<DishListResponse> showDishlist(DishListRequest dishListRequest) {
        return apiHelper.showDishlist(dishListRequest);
    }

    @Override
    public Observable<AddDiscountResponse> addDiscount(AddDiscountRequest addDiscountRequest) {
        return apiHelper.addDiscount(addDiscountRequest);
    }

    @Override
    public Observable<DiscountListResponse> DiscountList(DishListRequest dishListRequest) {
        return apiHelper.DiscountList(dishListRequest);
    }

    @Override
    public Observable<AddDiscountResponse> deleteDiscount(DeleteDiscountRequest deleteDiscountRequest) {
        return apiHelper.deleteDiscount(deleteDiscountRequest);
    }

    @Override
    public Observable<GetOrderListResponse> getOrderHistoryList(GetOrderListRequest getOrderListRequest) {
        return apiHelper.getOrderHistoryList(getOrderListRequest);
    }

    @Override
    public Observable<GetOrderListResponse> getCustomerOrderHistoryList(CustomerOrderHistoryRequest customerOrderHistoryRequest) {
//    public Observable<CustomerOrderHistoryResponse> getCustomerOrderHistoryList(CustomerOrderHistoryRequest customerOrderHistoryRequest) {
        return apiHelper.getCustomerOrderHistoryList(customerOrderHistoryRequest);
    }

    @Override
    public Observable<OrderHistoryDetail> getOrderHistoryDetail(String orderId) {
        return apiHelper.getOrderHistoryDetail(orderId);
    }

    @Override
    public Observable<StaticPageResponse> staticPage(StaticPageRequest staticPageRequest) {
        return apiHelper.staticPage(staticPageRequest);
    }

    @Override
    public Observable<SetNotificationResponse> setNotification(String user_id) {
        return apiHelper.setNotification(user_id);
    }

    //    private final Context mContext;
//    private final DbHelper mDbHelper;
//    private final PreferencesHelper mPreferencesHelper;
//    private final ApiHelper mApiHelper;
//
//    public AppDataManager(Context context,
//                          DbHelper dbHelper,
//                          PreferencesHelper preferencesHelper,
//                          ApiHelper apiHelper) {
//        mContext = context;
//        mDbHelper = dbHelper;
//        mPreferencesHelper = preferencesHelper;
//        mApiHelper = apiHelper;
//    }
//
//    @Override
//    public ApiHeader getApiHeader() {
//        return mApiHelper.getApiHeader();
//    }
//
//    @Override
//    public String getAccessToken() {
//        return mPreferencesHelper.getAccessToken();
//    }
//
//    @Override
//    public void setAccessToken(String accessToken) {
//        mPreferencesHelper.setAccessToken(accessToken);
//        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
//    }
//
//    @Override
//    public Observable<Long> insertUser(User user) {
//        return mDbHelper.insertUser(user);
//    }
//
//    @Override
//    public Observable<List<User>> getAllUsers() {
//        return mDbHelper.getAllUsers();
//    }
//
//    @Override
//    public Observable<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest
//                                                                  request) {
//        return mApiHelper.doGoogleLoginApiCall(request);
//    }
//
//    @Override
//    public Observable<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
//                                                                    request) {
//        return mApiHelper.doFacebookLoginApiCall(request);
//    }
//
//    @Override
//    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
//                                                                  request) {
//        return mApiHelper.doServerLoginApiCall(request);
//    }
//
//    @Override
//    public Observable<LogoutResponse> doLogoutApiCall() {
//        return mApiHelper.doLogoutApiCall();
//    }


//    @Override
//    public Long getCurrentUserId() {
//        return mPreferencesHelper.getCurrentUserId();
//    }
//
//    @Override
//    public void setCurrentUserId(Long userId) {
//        mPreferencesHelper.setCurrentUserId(userId);
//    }
//
//    @Override
//    public String getCurrentUserName() {
//        return mPreferencesHelper.getCurrentUserName();
//    }
//
//    @Override
//    public void setCurrentUserName(String userName) {
//        mPreferencesHelper.setCurrentUserName(userName);
//    }
//
//    @Override
//    public String getCurrentUserEmail() {
//        return mPreferencesHelper.getCurrentUserEmail();
//    }
//
//    @Override
//    public void setCurrentUserEmail(String email) {
//        mPreferencesHelper.setCurrentUserEmail(email);
//    }
//
//    @Override
//    public String getCurrentUserProfilePicUrl() {
//        return mPreferencesHelper.getCurrentUserProfilePicUrl();
//    }
//
//    @Override
//    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
//        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
//    }
//
//    @Override
//    public void updateApiHeader(Long userId, String accessToken) {
//        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
//        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
//    }
//
//    @Override
//    public void updateUserInfo(
//            String accessToken,
//            Long userId,
//            LoggedInMode loggedInMode,
//            String userName,
//            String email,
//            String profilePicPath) {
//
//        setAccessToken(accessToken);
//        setCurrentUserId(userId);
//        setCurrentUserLoggedInMode(loggedInMode);
//        setCurrentUserName(userName);
//        setCurrentUserEmail(email);
//        setCurrentUserProfilePicUrl(profilePicPath);
//
//        updateApiHeader(userId, accessToken);
//    }
//
//    @Override
//    public void setUserAsLoggedOut() {
//        updateUserInfo(
//                null,
//                null,
//                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
//                null,
//                null,
//                null);
//    }
//
//    @Override
//    public Observable<Boolean> isQuestionEmpty() {
//        return mDbHelper.isQuestionEmpty();
//    }
//
//    @Override
//    public Observable<Boolean> isOptionEmpty() {
//        return mDbHelper.isOptionEmpty();
//    }
//
//    @Override
//    public Observable<Boolean> saveQuestion(Question question) {
//        return mDbHelper.saveQuestion(question);
//    }
//
//    @Override
//    public Observable<Boolean> saveOption(Option option) {
//        return mDbHelper.saveOption(option);
//    }
//
//    @Override
//    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
//        return mDbHelper.saveQuestionList(questionList);
//    }
//
//    @Override
//    public Observable<Boolean> saveOptionList(List<Option> optionList) {
//        return mDbHelper.saveOptionList(optionList);
//    }
//
//    @Override
//    public Observable<List<Question>> getAllQuestions() {
//        return mDbHelper.getAllQuestions();
//    }
//
//    @Override
//    public Observable<Boolean> seedDatabaseQuestions() {
//
//        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
//        final Gson gson = builder.create();
//
//        return mDbHelper.isQuestionEmpty()
//                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
//                    @Override
//                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
//                            throws Exception {
//                        if (isEmpty) {
//                            Type type = $Gson$Types
//                                    .newParameterizedTypeWithOwner(null, List.class,
//                                            Question.class);
//                            List<Question> questionList = gson.fromJson(
//                                    CommonUtils.loadJSONFromAsset(mContext,
//                                            AppConstants.SEED_DATABASE_QUESTIONS),
//                                    type);
//
//                            return saveQuestionList(questionList);
//                        }
//                        return Observable.just(false);
//                    }
//                });
//    }
//
//    @Override
//    public Observable<Boolean> seedDatabaseOptions() {
//
//        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
//        final Gson gson = builder.create();
//
//        return mDbHelper.isOptionEmpty()
//                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
//                    @Override
//                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
//                            throws Exception {
//                        if (isEmpty) {
//                            Type type = new TypeToken<List<Option>>() {
//                            }
//                                    .getType();
//                            List<Option> optionList = gson.fromJson(
//                                    CommonUtils.loadJSONFromAsset(mContext,
//                                            AppConstants.SEED_DATABASE_OPTIONS),
//                                    type);
//
//                            return saveOptionList(optionList);
//                        }
//                        return Observable.just(false);
//                    }
//                });
//    }
}