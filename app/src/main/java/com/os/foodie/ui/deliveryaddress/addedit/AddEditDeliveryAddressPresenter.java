package com.os.foodie.ui.deliveryaddress.addedit;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.deliveryaddress.add.AddDeliveryAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressRequest;
import com.os.foodie.data.network.model.deliveryaddress.update.UpdateAddressResponse;
import com.os.foodie.data.network.model.forgotpassword.ForgotPasswordResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
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
            if (addDeliveryAddressRequest.getPincode() == null || addDeliveryAddressRequest.getPincode().isEmpty()) {
                getMvpView().onError(R.string.empty_zip_code);
                return;
            }

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
                    .subscribe(new Consumer<ForgotPasswordResponse>() {
                        @Override
                        public void accept(ForgotPasswordResponse forgotPasswordResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (forgotPasswordResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + forgotPasswordResponse.getResponse().getMessage());

                                Address address = setAddress(addDeliveryAddressRequest);

                                getMvpView().onError(forgotPasswordResponse.getResponse().getMessage());
                                getMvpView().onDeliveryAddressAdd(address);
//                                getMvpView().onPasswordReset("Password Reset");

                            } else {
                                getMvpView().onError(forgotPasswordResponse.getResponse().getMessage());
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
            if (updateAddressRequest.getPincode() == null || updateAddressRequest.getPincode().isEmpty()) {
                getMvpView().onError(R.string.empty_zip_code);
                return;
            }

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
//            if (updateAddressRequest.getState() == null || updateAddressRequest.getState().isEmpty()) {
//                getMvpView().onError(R.string.empty_state);
//                return;
//            }
//            if (updateAddressRequest.getCountry() == null || updateAddressRequest.getCountry().isEmpty()) {
//                getMvpView().onError(R.string.empty_country);
//                return;
//            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .updateDeliveryAddress(updateAddressRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UpdateAddressResponse>() {
                        @Override
                        public void accept(UpdateAddressResponse updateAddressResponse) throws Exception {

                            getMvpView().hideLoading();

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

    private Address setAddress(AddDeliveryAddressRequest addDeliveryAddressRequest) {

        Address address = new Address();

//        TODO get address id
        address.setId(addDeliveryAddressRequest.getUserId());
        address.setUserId(addDeliveryAddressRequest.getUserId());
        address.setFullName(addDeliveryAddressRequest.getFullName());
        address.setMobileNumber(addDeliveryAddressRequest.getMobileNumber());
        address.setPincode(addDeliveryAddressRequest.getPincode());
        address.setFlatNumber(addDeliveryAddressRequest.getFlatNumber());
        address.setColony(addDeliveryAddressRequest.getColony());
        address.setLandmark(addDeliveryAddressRequest.getLandmark());
        address.setCity(addDeliveryAddressRequest.getCity());

        return address;
    }

    private Address setAddress(UpdateAddressRequest updateAddressRequest) {

        Address address = new Address();

//        TODO get address id
        address.setId(updateAddressRequest.getAddressId());
        address.setUserId(updateAddressRequest.getUserId());
        address.setFullName(updateAddressRequest.getFullName());
        address.setMobileNumber(updateAddressRequest.getMobileNumber());
        address.setPincode(updateAddressRequest.getPincode());
        address.setFlatNumber(updateAddressRequest.getFlatNumber());
        address.setColony(updateAddressRequest.getColony());
        address.setLandmark(updateAddressRequest.getLandmark());
        address.setCity(updateAddressRequest.getCity());

        return address;
    }
}