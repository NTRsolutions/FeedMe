package com.os.foodie.ui.locationinfo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.citycountrylist.CityCountryListResponse;
import com.os.foodie.data.network.model.citycountrylist.Country;
import com.os.foodie.data.network.model.locationinfo.city.CityListRequest;
import com.os.foodie.data.network.model.locationinfo.city.CityListResponse;
import com.os.foodie.data.network.model.locationinfo.country.CountryListResponse;
import com.os.foodie.data.network.model.locationinfo.set.SetUserLocationRequest;
import com.os.foodie.data.network.model.locationinfo.set.SetUserLocationResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocationInfoPresenter<V extends LocationInfoMvpView> extends BasePresenter<V> implements LocationInfoMvpPresenter<V> {

    public LocationInfoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void setCurrentUserInfoInitialized(boolean initialized) {
        getDataManager().setCurrentUserInfoInitialized(initialized);
//        getMvpView().onCustomerLocationSaved();
    }

    @Override
    public boolean getCurrentUserInfoInitialized() {
        return getDataManager().isCurrentUserInfoInitialized();
    }

    @Override
    public boolean isLoggedIn() {
        return getDataManager().isCurrentUserLoggedIn();
    }

    @Override
    public void setCityName(String cityName) {

        if (cityName == null || cityName.isEmpty()) {
            getMvpView().onError(R.string.empty_city);
            return;
        }

        getDataManager().setCityName(cityName);
        getMvpView().onUserLocationInfoSaved();
    }

    @Override
    public void setLatLng(String latitude, String longitude) {
        getDataManager().setLatLng(latitude, longitude);
    }

    @Override
    public void setError(@StringRes int resId) {
        getMvpView().onError(resId);
    }

    @Override
    public void setError(String message) {
        getMvpView().onErrorLong(message);
    }

    @Override
    public void getCountryList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getCompositeDisposable().add(getDataManager()
                    .getCountryList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CountryListResponse>() {
                        @Override
                        public void accept(CountryListResponse countryListResponse) throws Exception {

                            if (countryListResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + countryListResponse.getResponse().getMessage());
//                                getMvpView().setCountryAdapter(countryListResponse.getResponse().getCountry());

                            } else {
                                getMvpView().onError(countryListResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void getCityList(String countryId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getCompositeDisposable().add(getDataManager()
                    .getCityList(new CityListRequest(countryId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CityListResponse>() {
                        @Override
                        public void accept(CityListResponse cityListResponse) throws Exception {

                            if (cityListResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + cityListResponse.getResponse().getMessage());
//                                getMvpView().setCityAdapter(cityListResponse.getResponse().getCity());

                            } else {
                                getMvpView().onError(cityListResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void getCityCountryList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getCompositeDisposable().add(getDataManager()
                    .getCityCountryList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CityCountryListResponse>() {
                        @Override
                        public void accept(CityCountryListResponse cityCountryListResponse) throws Exception {

                            if (cityCountryListResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + cityCountryListResponse.getResponse().getMessage());
                                getMvpView().setCityCountryListAdapter((ArrayList<Country>) cityCountryListResponse.getResponse().getCountry());

                            } else {
                                getMvpView().onError(cityCountryListResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void setUserLocationInfo(LatLng latLng, String country, String city, String address) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (country == null || country.isEmpty()) {
                getMvpView().onError(R.string.empty_country);
                return;
            }
            if (city == null || city.isEmpty()) {
                getMvpView().onError(R.string.empty_city);
                return;
            }
            if (address == null || address.isEmpty()) {
                getMvpView().onError(R.string.empty_address);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .setUserLocationInfo(new SetUserLocationRequest(getDataManager().getCurrentUserId(), latLng.latitude + "", latLng.longitude + "", address, country, city))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SetUserLocationResponse>() {
                        @Override
                        public void accept(SetUserLocationResponse userLocationResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (userLocationResponse.getResponse().getIsDeleted() != null && userLocationResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), userLocationResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (userLocationResponse.getResponse().getStatus() == 1) {

                                getDataManager().setCurrentUserInfoInitialized(true);
                                getMvpView().onUserLocationInfoSaved();

                            } else {

                                getMvpView().onError(userLocationResponse.getResponse().getMessage());
                            }

                            Log.d("Message", ">>" + userLocationResponse.getResponse().getMessage());
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