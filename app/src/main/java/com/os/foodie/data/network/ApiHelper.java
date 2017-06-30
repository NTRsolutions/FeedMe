package com.os.foodie.data.network;

import android.content.Context;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;
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
import com.os.foodie.data.network.model.menu.add.AddMenuItemRequest;
import com.os.foodie.data.network.model.menu.add.AddMenuItemResponse;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemRequest;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemResponse;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuRequest;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuResponse;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemRequest;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemResponse;
import com.os.foodie.data.network.model.order.detail.OrderHistoryDetail;
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

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public interface ApiHelper {

    Observable<CustomerSignUpResponse> doCustomerSignUp(CustomerSignUpRequest customerSignUpRequest);

    Observable<RestaurantSignUpResponse> doRestaurantSignUp(RestaurantSignUpRequest restaurantSignUpRequest, HashMap<String, File> fileMap);

    Observable<FacebookLoginResponse> doFacebookLogin(FacebookLoginRequest facebookLoginRequest);

    Observable<LoginResponse> doLogin(LoginRequest loginRequest);

    Observable<OtpVerificationResponse> verifyOTP(OtpVerificationRequest otpVerificationRequest);

    Observable<ForgotPasswordResponse> resetPassword(ForgotPasswordRequest forgotPasswordRequest);

    Observable<CountryListResponse> getCountryList();

    Observable<CityListResponse> getCityList(CityListRequest cityListRequest);

    Observable<SetUserLocationResponse> setUserLocationInfo(SetUserLocationRequest setUserLocationRequest);

    Observable<CuisineTypeResponse> getCuisineTypeList();

    Observable<AddCuisineTypeResponse> addCuisineType(AddCuisineTypeRequest addCuisineTypeRequest);

    Observable<SetupRestaurantProfileResponse> setRestaurantProfile(SetupRestaurantProfileRequest restaurantProfileRequest, HashMap<String, File> fileMap);

    Observable<GetRestaurantMenuResponse> getRestaurantMenuList(GetRestaurantMenuRequest restaurantMenuRequest);

    Observable<AddMenuItemResponse> addRestaurantMenuItem(AddMenuItemRequest addMenuItemRequest);

    Observable<StatusMenuItemResponse> changeStatusRestaurantMenuItem(StatusMenuItemRequest statusMenuItemRequest);

    Observable<DeleteMenuItemResponse> deleteRestaurantMenuItem(DeleteMenuItemRequest deleteMenuItemRequest);

    Observable<GetCourseTypeResponse> getCourseTypeList();

    Observable<AddCourseTypeResponse> addCourseType(AddCourseTypeRequest addCourseTypeRequest);

    Observable<List<Address>> getGeocoderLocationAddress(Context context, LatLng latLng);

    Observable<GetRestaurantListResponse> getRestaurantList(GetRestaurantListRequest getRestaurantListRequest);

    Observable<CustomerRestaurantDetailsResponse> getRestaurantDetails(CustomerRestaurantDetailsRequest restaurantDetailsRequest);

    Observable<LikeDislikeResponse> doLikeDislike(LikeDislikeRequest likeDislikeRequest);

    Observable<ChangePasswordResponse> changePassword(ChangePasswordRequest changePasswordRequest);

    Observable<GetAccountDetailResponse> getCustomerAccountDetail(GetAccountDetailRequest getAccountDetailRequest);

    Observable<EditCustomerAccountDetailResponse> editCustomerAccount(EditCustomerAccountRequest editCustomerAccountRequest);

    Observable<AddToCartResponse> addToCart(AddToCartRequest addToCartRequest);

    Observable<ViewCartResponse> getCartDetails(ViewCartRequest viewCartRequest);

    Observable<RemoveFromCartResponse> removeFromCart(RemoveFromCartRequest removeFromCartRequest);

    Observable<UpdateCartResponse> updateCart(UpdateCartRequest updateCartRequest);

    Observable<ClearCartResponse> clearCart(ClearCartRequest clearCartRequest);

    Observable<RestaurantProfileResponse> getRestaurantProfile(CustomerRestaurantDetailsRequest customerRestaurantDetailsRequest);

    Observable<ChangePasswordResponse> deleteRestaurantImage(String restaurantId);

    Observable<EditRestaurantAccountResponse> editRestaurantAccount(EditRestaurantAccountRequest editRestaurantAccountRequest, HashMap<String, File> fileMap);

    Observable<GetAllAddressResponse> getAllAddress(GetAllAddressRequest getAllAddressRequest);

    Observable<DeleteAddressResponse> deleteAddress(DeleteAddressRequest deleteAddressRequest);

    Observable<AddDeliveryAddressResponse> addDeliveryAddress(AddDeliveryAddressRequest addDeliveryAddressRequest);

    Observable<UpdateAddressResponse> updateDeliveryAddress(UpdateAddressRequest updateAddressRequest);

    Observable<AddPaymentCardResponse> addPaymentCard(AddPaymentCardRequest addPaymentCardRequest);

    Observable<GetAllPaymentCardResponse> getAllPaymentCard(GetAllPaymentCardRequest getAllPaymentCardRequest);

    Observable<DeletePaymentCardResponse> deletePaymentCard(DeletePaymentCardRequest deletePaymentCardRequest);

    Observable<CheckoutResponse> checkoout(CheckoutRequest checkoutRequest);

    Observable<GetOrderListResponse> getOrderList(GetOrderListRequest getOrderListRequest);

    Observable<AcceptRejectOrderResponse> acceptRejectOrder(AcceptRejectOrderRequest acceptRejectOrderRequest);

//    Abhi

    Observable<DishListResponse> showDishlist(DishListRequest dishListRequest);

    Observable<AddDiscountResponse> addDiscount(AddDiscountRequest addDiscountRequest);

    Observable<DiscountListResponse> DiscountList(DishListRequest dishListRequest);

    Observable<AddDiscountResponse> deleteDiscount(DeleteDiscountRequest deleteDiscountRequest);

//    Monika
Observable<GetOrderListResponse> getOrderHistoryList(GetOrderListRequest getOrderListRequest);

    Observable<OrderHistoryDetail> getOrderHistoryDetail(String orderId);

//    Observable<CustomerSignUpResponse> getFacebookDetail(CustomerSignUpRequest customerSignUpRequest);
}