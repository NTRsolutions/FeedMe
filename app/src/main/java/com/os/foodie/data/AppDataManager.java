package com.os.foodie.data;

import android.content.Context;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.data.network.ApiHelper;
import com.os.foodie.data.network.model.account.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.EditCustomerAccountRequest;
import com.os.foodie.data.network.model.account.GetAccountDetailRequest;
import com.os.foodie.data.network.model.account.GetAccountDetailResponse;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.cart.add.AddToCartResponse;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartRequest;
import com.os.foodie.data.network.model.cart.remove.RemoveFromCartResponse;
import com.os.foodie.data.network.model.cart.update.UpdateCartRequest;
import com.os.foodie.data.network.model.cart.update.UpdateCartResponse;
import com.os.foodie.data.network.model.cart.view.ViewCartRequest;
import com.os.foodie.data.network.model.cart.view.ViewCartResponse;
import com.os.foodie.data.network.model.changepassword.ChangePasswordRequest;
import com.os.foodie.data.network.model.changepassword.ChangePasswordResponse;
import com.os.foodie.data.network.model.coursetype.add.AddCourseTypeRequest;
import com.os.foodie.data.network.model.coursetype.add.AddCourseTypeResponse;
import com.os.foodie.data.network.model.coursetype.list.GetCourseTypeResponse;
import com.os.foodie.data.network.model.cuisinetype.add.AddCuisineTypeRequest;
import com.os.foodie.data.network.model.cuisinetype.add.AddCuisineTypeResponse;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineTypeResponse;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsRequest;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsResponse;
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
import com.os.foodie.data.network.model.menu.add.AddMenuItemRequest;
import com.os.foodie.data.network.model.menu.add.AddMenuItemResponse;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemRequest;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemResponse;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuRequest;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuResponse;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemRequest;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemResponse;
import com.os.foodie.data.network.model.otp.OtpVerificationRequest;
import com.os.foodie.data.network.model.otp.OtpVerificationResponse;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileRequest;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileResponse;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.data.network.model.fblogin.FacebookLoginRequest;
import com.os.foodie.data.network.model.fblogin.FacebookLoginResponse;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpRequest;
import com.os.foodie.data.network.model.signup.restaurant.RestaurantSignUpResponse;
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