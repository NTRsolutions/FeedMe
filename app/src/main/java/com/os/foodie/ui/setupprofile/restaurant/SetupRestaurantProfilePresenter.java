package com.os.foodie.ui.setupprofile.restaurant;

import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.changepassword.ChangePasswordResponse;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineTypeResponse;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileRequest;
import com.os.foodie.data.network.model.setupprofile.restaurant.SetupRestaurantProfileResponse;
import com.os.foodie.data.prefs.PreferencesHelper;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.TimeFormatUtils;

import java.io.File;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SetupRestaurantProfilePresenter<V extends SetupRestaurantProfileMvpView> extends BasePresenter<V> implements SetupRestaurantProfileMvpPresenter<V> {

    private Calendar openingDate;
    private Calendar closingDate;

    public SetupRestaurantProfilePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public boolean isCurrentUserInfoInitialized() {
        return getDataManager().isCurrentUserInfoInitialized();
    }

    @Override
    public void deleteRestaurantImage(String imageId) {
        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .deleteRestaurantImage(imageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ChangePasswordResponse>() {
                        @Override
                        public void accept(ChangePasswordResponse cuisineTypeResponse) throws Exception {

                            Log.d("Message", ">>" + cuisineTypeResponse.getResponse().getMessage());

                            getMvpView().hideLoading();

                            if (cuisineTypeResponse.getResponse().getStatus() == 0) {
                                getMvpView().onError(cuisineTypeResponse.getResponse().getMessage());
                            } else {

                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void setCurrentUserInfoInitialized(boolean initialized) {
        getDataManager().setCurrentUserInfoInitialized(initialized);
    }

    @Override
    public void onCuisineTypeClick() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getCuisineTypeList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CuisineTypeResponse>() {
                        @Override
                        public void accept(CuisineTypeResponse cuisineTypeResponse) throws Exception {

                            getMvpView().hideLoading();
                            Log.d("Message", ">>" + cuisineTypeResponse.getResponse().getMessage());

                            if (cuisineTypeResponse.getResponse().getStatus() == 0) {
                                getMvpView().onCuisineTypeListReceive(new ArrayList<CuisineType>());
//                                getMvpView().onError(cuisineTypeResponse.getResponse().getMessage());
                            } else {
//                                getMvpView().onCuisineTypeListReceive(cuisineTypeResponse.getResponse().getData().getCourses());
                                getMvpView().onCuisineTypeListReceive((ArrayList<CuisineType>) cuisineTypeResponse.getResponse().getData().get(0).getCuisineType());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            getMvpView().hideLoading();
                            Log.d("Message", ">>" + throwable.getMessage());
                            getMvpView().onError(R.string.api_default_error);
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void saveRestaurantProfile(final SetupRestaurantProfileRequest restaurantProfileRequest, HashMap<String, File> fileMap, boolean isEditProfile) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (restaurantProfileRequest.getCuisineType() == null || restaurantProfileRequest.getCuisineType().isEmpty()) {
                getMvpView().onError(R.string.empty_cuisine_type);
                return;
            }

            if (restaurantProfileRequest.getWorkingDays() == null || restaurantProfileRequest.getWorkingDays().isEmpty()) {
                getMvpView().onError(R.string.empty_working_days);
                return;
            }

            if (restaurantProfileRequest.getAddress() == null || restaurantProfileRequest.getAddress().isEmpty()) {
                getMvpView().onError(R.string.empty_address);
                return;
            }

            if (restaurantProfileRequest.getCountry() == null || restaurantProfileRequest.getCountry().isEmpty()) {
                getMvpView().onError(R.string.empty_country);
                return;
            }

            if (restaurantProfileRequest.getCity() == null || restaurantProfileRequest.getCity().isEmpty()) {
                getMvpView().onError(R.string.empty_city);
                return;
            }

            if (restaurantProfileRequest.getZipCode() == null || restaurantProfileRequest.getZipCode().isEmpty()) {
                getMvpView().onError(R.string.empty_zip_code);
                return;
            }

            if (restaurantProfileRequest.getOpeningTime() == null || restaurantProfileRequest.getOpeningTime().isEmpty()) {
                getMvpView().onError(R.string.empty_opening_hours);
                return;
            }

            if (restaurantProfileRequest.getClosingTime() == null || restaurantProfileRequest.getClosingTime().isEmpty()) {
                getMvpView().onError(R.string.empty_closing_hours);
                return;
            }

            if (restaurantProfileRequest.getMinOrderAmount() == null || restaurantProfileRequest.getMinOrderAmount().isEmpty()) {
                getMvpView().onError(R.string.empty_minimum_order_amount);
                return;
            }

            if (restaurantProfileRequest.getDeliveryCharge() == null || restaurantProfileRequest.getDeliveryCharge().isEmpty()) {
                getMvpView().onError(R.string.empty_delivery_charges);
                return;
            }

            if (restaurantProfileRequest.getDeliveryZipcode() == null || restaurantProfileRequest.getDeliveryZipcode().isEmpty()) {
                getMvpView().onError(R.string.empty_delivery_zip_codes);
                return;
            }

            if (restaurantProfileRequest.getDescription() == null || restaurantProfileRequest.getDescription().isEmpty()) {
                getMvpView().onError(R.string.empty_description);
                return;
            }

            if (!isEditProfile) {
                if (fileMap.size() == 0) {
                    getMvpView().onError(R.string.mandatory_image);
                    return;
                }
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .setRestaurantProfile(restaurantProfileRequest, fileMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SetupRestaurantProfileResponse>() {
                        @Override
                        public void accept(SetupRestaurantProfileResponse restaurantProfileResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (restaurantProfileResponse.getResponse().getStatus() == 0) {
                                getMvpView().onError(restaurantProfileResponse.getResponse().getMessage());
                            } else {
                                Log.d("Message", "onSuccess>>" + restaurantProfileResponse.getResponse().getMessage());
                                getDataManager().setCurrentUserInfoInitialized(true);
                                getDataManager().setCurrency(CommonUtils.dataDecode(restaurantProfileRequest.getCurrency()));
//                                getDataManager().setCurrency(URLDecoder.decode(restaurantProfileRequest.getCurrency(),"UTF-8"));
                                getMvpView().onRestaurantProfileSaved();
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));

        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public PreferencesHelper getPreferencesHelper() {
        return getDataManager();
    }

    @Override
    public void setTime(final EditText etTimeToSet, final EditText etTimeToCompare, final boolean isOpeningTime) {

        openingDate = Calendar.getInstance();
        closingDate = Calendar.getInstance();

        int hour;
        int minute;

        if (etTimeToSet.getText().toString() != null && !etTimeToSet.getText().toString().isEmpty()) {

            String convertTimeIn12Format = TimeFormatUtils.changeTimeFormat(etTimeToSet.getText().toString(), "hh:mm a", "HH:mm");

            String time[] = convertTimeIn12Format.split(":");

            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);

        } else {
            hour = 0;
            minute = 0;
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(getMvpView().getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String hour, min;

                        hour = hourOfDay + "";
                        min = minute + "";

                        if (hour.length() == 1) {
                            hour = "0" + hour;
                        }
                        if (min.length() == 1) {
                            min = "0" + min;
                        }


                        if (etTimeToCompare != null && !etTimeToCompare.getText().toString().isEmpty()) {

                            String time[] = etTimeToCompare.getText().toString().split(":");
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                            if (isOpeningTime) {

                                try {
                                    openingDate.setTime(sdf.parse(hourOfDay + ":" + minute));
                                    closingDate.setTime(sdf.parse(time[0] + ":" + time[1]));
//                                Date date2 = sdf.parse(time[0] + ":" + time[1]);
//
//                                openingDate.setTime(date1);
//                                closingDate.setTime(date2);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (closingDate.getTimeInMillis() <= openingDate.getTimeInMillis()) {
                                    Log.d("isOpeningTime", "closingDate<openingDate");
                                    getMvpView().onError(R.string.opening_time_after_closing_time);
                                    return;
                                } else {
                                    Log.d("isOpeningTime", "closingDate!!!<openingDate");
                                }

                            } else {

                                try {
                                    openingDate.setTime(sdf.parse(time[0] + ":" + time[1]));
                                    closingDate.setTime(sdf.parse(hourOfDay + ":" + minute));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (closingDate.getTimeInMillis() <= openingDate.getTimeInMillis()) {
                                    Log.d("isNotOpeningTime", "closingDate<openingDate");
                                    getMvpView().onError(R.string.closing_time_before_opening_time);
                                    return;
                                } else {
                                    Log.d("isNotOpeningTime", "closingDate!!!<openingDate");
                                }
                            }
                        }


                        etTimeToSet.setText(TimeFormatUtils.changeTimeFormat(hour + ":" + min, "HH:mm", "hh:mm a"));
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }

    @Override
    public void setError(@StringRes int resId) {
        setError(resId);
    }

    @Override
    public void setError(String message) {
        setError(message);
    }

    @Override
    public void dismissDialog() {
        getMvpView().hideLoading();
    }

    @Override
    public void getGeocoderLocationAddress(Context context, LatLng latLng) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getGeocoderLocationAddress(context, latLng)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Address>>() {
                        @Override
                        public void accept(List<Address> addresses) throws Exception {

                            getMvpView().hideLoading();

                            if (addresses != null && addresses.size() > 0) {

                                getMvpView().setAllAddress((ArrayList<Address>) addresses);
////                                return (ArrayList<Address>) addresses;
//
//                                Address address = addresses.get(0);
//
//                                String fullAddress = "";
//
//                                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                                    if (i <= 1)
//                                        fullAddress = fullAddress + address.getAddressLine(i) + " - ";
//                                    else
//                                        break;
//                                }
//
//
//                                Log.d("getAdminArea", ">>" + address.getAdminArea());
//                                Log.d("getSubAdminArea", ">>" + address.getSubAdminArea());
//
//                                Log.d("getCountryName", ">>" + address.getCountryName());
//                                Log.d("getPostalCode", ">>" + address.getPostalCode());
//
//                                Log.d("getLocality", ">>" + address.getLocality());
//                                Log.d("getSubLocality", ">>" + address.getSubLocality());
//
//                                Log.d("fullAddress", ">>" + fullAddress);
//
////                                Log.d("address_str", ">>" + fullAddress);
////                                etCurrentLocation.setText(fullAddress);
//
////                        String postalCode = address.getPostalCode();
////                        String city = address.getLocality();
////                        String state = address.getAdminArea();
////                        String country = address.getCountryName();
////
////                        Log.d("postalCode", ">>" + postalCode);
////                        Log.d("city", ">>" + city);
////                        Log.d("state", ">>" + state);
////                        Log.d("country", ">>" + country);
////                        etCurrentLocation.setSelection(etCurrentLocation.getText().length());
////                        etCurrentLocation.clearFocus();
                            } else {
                                getMvpView().onError(R.string.geocoder_location_address_not_found);
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
                        }
                    }));

        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}