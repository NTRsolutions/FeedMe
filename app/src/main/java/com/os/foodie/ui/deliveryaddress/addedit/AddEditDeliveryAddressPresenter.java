package com.os.foodie.ui.deliveryaddress.addedit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.citycountrylist.CityCountryListRequest;
import com.os.foodie.data.network.model.citycountrylist.CityCountryListResponse;
import com.os.foodie.data.network.model.citycountrylist.Country;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressResponse;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressResponse;
import com.os.foodie.data.network.model.deliveryaddress.zip.LocationAndCityCountryListResponse;
import com.os.foodie.data.network.model.forgotpassword.ForgotPasswordResponse;
import com.os.foodie.data.network.model.locationinfo.get.GetUserLocationDetailRequest;
import com.os.foodie.data.network.model.locationinfo.get.GetUserLocationDetailResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AddEditDeliveryAddressPresenter<V extends AddEditDeliveryAddressMvpView> extends BasePresenter<V> implements AddEditDeliveryAddressMvpPresenter<V> {

    public AddEditDeliveryAddressPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void addDeliverAddress(final AddDeliveryAddressRequest addDeliveryAddressRequest) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (addDeliveryAddressRequest.getFullName() == null || (addDeliveryAddressRequest.getFullName().isEmpty())) {
                getMvpView().onError(R.string.empty_fullname);
                return;
            }
            if (addDeliveryAddressRequest.getMobileNumber() == null || addDeliveryAddressRequest.getMobileNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(addDeliveryAddressRequest.getMobileNumber())) {
                getMvpView().onError(R.string.invalid_phone);
                return;
            }
           /* if (addDeliveryAddressRequest.getPincode() == null || addDeliveryAddressRequest.getPincode().isEmpty()) {
                getMvpView().onError(R.string.empty_zip_code);
                return;
            }*/

            if (addDeliveryAddressRequest.getFlatNumber() == null || addDeliveryAddressRequest.getFlatNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_flat);
                return;
            }
            if (addDeliveryAddressRequest.getColony() == null || addDeliveryAddressRequest.getColony().isEmpty()) {
                getMvpView().onError(R.string.empty_colony);
                return;
            }
            if (addDeliveryAddressRequest.getCity() == null || addDeliveryAddressRequest.getCity().isEmpty()) {
                getMvpView().onError(R.string.empty_city);
                return;
            }
            if (addDeliveryAddressRequest.getState() == null || addDeliveryAddressRequest.getState().isEmpty()) {
                getMvpView().onError(R.string.empty_state);
                return;
            }
            if (addDeliveryAddressRequest.getCountry() == null || addDeliveryAddressRequest.getCountry().isEmpty()) {
                getMvpView().onError(R.string.empty_country);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .addDeliveryAddress(addDeliveryAddressRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddDeliveryAddressResponse>() {
                        @Override
                        public void accept(AddDeliveryAddressResponse addDeliveryAddressResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (addDeliveryAddressResponse.getResponse().getIsDeleted() != null && addDeliveryAddressResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), addDeliveryAddressResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (addDeliveryAddressResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + addDeliveryAddressResponse.getResponse().getMessage());

                                Address address = setAddress(addDeliveryAddressResponse.getResponse().getAddressId(), addDeliveryAddressRequest);

                                getMvpView().onError(addDeliveryAddressResponse.getResponse().getMessage());
                                getMvpView().onDeliveryAddressAdd(address);
//                                getMvpView().onPasswordReset("Password Reset");

                            } else {
                                getMvpView().onError(addDeliveryAddressResponse.getResponse().getMessage());
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
    public void updateDeliverAddress(final UpdateAddressRequest updateAddressRequest) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (updateAddressRequest.getFullName() == null || (updateAddressRequest.getFullName().isEmpty())) {
                getMvpView().onError(R.string.empty_fullname);
                return;
            }
            if (updateAddressRequest.getMobileNumber() == null || updateAddressRequest.getMobileNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(updateAddressRequest.getMobileNumber())) {
                getMvpView().onError(R.string.invalid_phone);
                return;
            }
           /* if (updateAddressRequest.getPincode() == null || updateAddressRequest.getPincode().isEmpty()) {
                getMvpView().onError(R.string.empty_zip_code);
                return;
            }*/

            if (updateAddressRequest.getFlatNumber() == null || updateAddressRequest.getFlatNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_flat);
                return;
            }
            if (updateAddressRequest.getColony() == null || updateAddressRequest.getColony().isEmpty()) {
                getMvpView().onError(R.string.empty_colony);
                return;
            }
            if (updateAddressRequest.getCity() == null || updateAddressRequest.getCity().isEmpty()) {
                getMvpView().onError(R.string.empty_city);
                return;
            }
            if (updateAddressRequest.getState() == null || updateAddressRequest.getState().isEmpty()) {
                getMvpView().onError(R.string.empty_state);
                return;
            }
            if (updateAddressRequest.getCountry() == null || updateAddressRequest.getCountry().isEmpty()) {
                getMvpView().onError(R.string.empty_country);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .updateDeliveryAddress(updateAddressRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UpdateAddressResponse>() {
                        @Override
                        public void accept(UpdateAddressResponse updateAddressResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (updateAddressResponse.getResponse().getIsDeleted() != null && updateAddressResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

//                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), updateAddressResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (updateAddressResponse.getResponse().getStatus() == 1) {

                                Log.d("getMessage", ">>" + updateAddressResponse.getResponse().getMessage());

                                Address address = setAddress(updateAddressRequest);

                                getMvpView().onError(updateAddressResponse.getResponse().getMessage());
                                getMvpView().onDeliveryAddressEdit(address);

                            } else {
                                getMvpView().onError(updateAddressResponse.getResponse().getMessage());
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

    private Address setAddress(String addressId, AddDeliveryAddressRequest addDeliveryAddressRequest) {

        Address address = new Address();

        address.setId(addressId);
        address.setUserId(addDeliveryAddressRequest.getUserId());
        address.setFullName(addDeliveryAddressRequest.getFullName());
        address.setMobileNumber(addDeliveryAddressRequest.getMobileNumber());
        address.setPincode(addDeliveryAddressRequest.getPincode());
        address.setFlatNumber(addDeliveryAddressRequest.getFlatNumber());
        address.setColony(addDeliveryAddressRequest.getColony());
        address.setLandmark(addDeliveryAddressRequest.getLandmark());
        address.setCity(addDeliveryAddressRequest.getCity());
        address.setState(addDeliveryAddressRequest.getState());
        address.setCountry(addDeliveryAddressRequest.getCountry());

        return address;
    }

    private Address setAddress(UpdateAddressRequest updateAddressRequest) {

        Address address = new Address();

        address.setId(updateAddressRequest.getAddressId());
        address.setUserId(updateAddressRequest.getUserId());
        address.setFullName(updateAddressRequest.getFullName());
        address.setMobileNumber(updateAddressRequest.getMobileNumber());
        address.setPincode(updateAddressRequest.getPincode());
        address.setFlatNumber(updateAddressRequest.getFlatNumber());
        address.setColony(updateAddressRequest.getColony());
        address.setLandmark(updateAddressRequest.getLandmark());
        address.setCity(updateAddressRequest.getCity());
        address.setState(updateAddressRequest.getState());
        address.setCountry(updateAddressRequest.getCountry());

        return address;
    }

    @Override
    public void getGeocoderLocationAddress(Context context, LatLng latLng) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getGeocoderLocationAddress(context, latLng)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<android.location.Address>>() {
                        @Override
                        public void accept(List<android.location.Address> addresses) throws Exception {

                            getMvpView().hideLoading();

                            if (addresses != null && addresses.size() > 0) {

                                getMvpView().setAllAddress((ArrayList<android.location.Address>) addresses);

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

//    @Override
//    public Observable<CityCountryListResponse> getCityCountryList() {
//
//        return /*getCompositeDisposable().add(*/getDataManager()
//                .getCityCountryList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                    /*.subscribe(new Consumer<CityCountryListResponse>() {
//                        @Override
//                        public void accept(CityCountryListResponse cityCountryListResponse) throws Exception {
//
//                            if (cityCountryListResponse.getResponse().getStatus() == 1) {
//                                Log.d("getMessage", ">>" + cityCountryListResponse.getResponse().getMessage());
//                                getMvpView().setCityCountryListAdapter((ArrayList<Country>) cityCountryListResponse.getResponse().getCountry());
//
//                            } else {
//                                getMvpView().onError(cityCountryListResponse.getResponse().getMessage());
//                            }
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            getMvpView().onError(R.string.api_default_error);
//                            Log.d("Error", ">>Err" + throwable.getMessage());
//                        }
//                    }))*/;
//
//    }

    @Override
    public void getLocationDetailsAndCityCountryList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            String language = "";

            if (getDataManager().getLanguage().equals(AppConstants.LANG_AR)) {
                language = AppConstants.LANG_AR;
            } else {
                language = AppConstants.LANG_ENG;
            }

            getCompositeDisposable().add(Observable.zip(getDataManager().getCityCountryList(new CityCountryListRequest(language)), getDataManager().getUserLocationDetail(new GetUserLocationDetailRequest(getDataManager().getCurrentUserId())), new BiFunction<CityCountryListResponse, GetUserLocationDetailResponse, LocationAndCityCountryListResponse>() {
                @Override
                public LocationAndCityCountryListResponse apply(CityCountryListResponse cityCountryListResponse, GetUserLocationDetailResponse userLocationDetailResponse) throws Exception {

                    return new LocationAndCityCountryListResponse(cityCountryListResponse, userLocationDetailResponse);
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LocationAndCityCountryListResponse>() {
                        @Override
                        public void accept(LocationAndCityCountryListResponse locationAndCityCountryListResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (locationAndCityCountryListResponse.getCityCountryListResponse().getResponse().getStatus() == 1 && locationAndCityCountryListResponse.getUserLocationDetailResponse().getResponse().getStatus() == 1) {

                                Log.d("getMessage", ">>" + locationAndCityCountryListResponse.getCityCountryListResponse().getResponse().getMessage());
                                Log.d("getMessage", ">>" + locationAndCityCountryListResponse.getUserLocationDetailResponse().getResponse().getMessage());

                                getMvpView().setCityCountryListAdapter((ArrayList<Country>) locationAndCityCountryListResponse.getCityCountryListResponse().getResponse().getCountry(), locationAndCityCountryListResponse.getUserLocationDetailResponse().getResponse());

                            } else if (locationAndCityCountryListResponse.getCityCountryListResponse().getResponse().getStatus() == 0) {

                                getMvpView().onError(locationAndCityCountryListResponse.getCityCountryListResponse().getResponse().getMessage());

                            } else if (locationAndCityCountryListResponse.getUserLocationDetailResponse().getResponse().getStatus() == 0) {

                                getMvpView().onError(locationAndCityCountryListResponse.getUserLocationDetailResponse().getResponse().getMessage());
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
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}