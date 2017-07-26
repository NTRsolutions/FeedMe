package com.os.foodie.ui.account.customer;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountRequest;
import com.os.foodie.data.network.model.account.GetAccountDetailRequest;
import com.os.foodie.data.network.model.account.GetAccountDetailResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CustomerAccountPresenter<V extends CustomerAccountMvpView> extends BasePresenter<V> implements CustomerAccountMvpPresenter<V> {

    public CustomerAccountPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getCustomerAccountDetail() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getCustomerAccountDetail(new GetAccountDetailRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetAccountDetailResponse>() {
                        @Override
                        public void accept(GetAccountDetailResponse getAccountDetailResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (getAccountDetailResponse.getResponse().getStatus() == 1) {

                                if (getAccountDetailResponse.getResponse() != null) {

                                     getMvpView().setAccountDetail(getAccountDetailResponse);

                                } else {
                                    Log.d("Error", ">>Err");
                                    // getMvpView().onError(R.string.no_restaurant);
                                }

                            } else {
                                getMvpView().onError(getAccountDetailResponse.getResponse().getMessage());
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Log.d("Error", ">>ErrThorwed");
                            getMvpView().onError(R.string.api_default_error);
                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else

        {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void editCustomerAccountDetail(EditCustomerAccountRequest editCustomerAccountRequest) {
        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (editCustomerAccountRequest.getFirstName() == null || editCustomerAccountRequest.getFirstName().trim().isEmpty()) {
                getMvpView().onError(R.string.empty_first_name);
                return;
            }
            if (editCustomerAccountRequest.getLastName() == null || editCustomerAccountRequest.getLastName().trim().isEmpty()) {
                getMvpView().onError(R.string.empty_last_name);
                return;
            }
//        if (!ValidationUtils.isNameValid(fname)) {
//            getMvpView().onError(R.string.invalid_first_name);
//            return;
//        }
            if (editCustomerAccountRequest.getEmail()  == null || editCustomerAccountRequest.getEmail().isEmpty()) {
                getMvpView().onError(R.string.empty_email);
                return;
            }
            if (!ValidationUtils.isEmailValid(editCustomerAccountRequest.getEmail())) {
                getMvpView().onError(R.string.invalid_email);
                return;
            }

            if (editCustomerAccountRequest.getMobileNumber()  == null || editCustomerAccountRequest.getMobileNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_phone);
                return;
            }
            if (!ValidationUtils.isPhoneValid(editCustomerAccountRequest.getMobileNumber())) {
                getMvpView().onError(R.string.invalid_phone);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .editCustomerAccount(editCustomerAccountRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<EditCustomerAccountDetailResponse>() {
                        @Override
                        public void accept(EditCustomerAccountDetailResponse editCustomerAccountDetailResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (editCustomerAccountDetailResponse.getResponse().getStatus() == 1) {
//                                TODO OTP
//                                getMvpView().verifyOTP();
                                getDataManager().setCurrentUserName(editCustomerAccountDetailResponse.getResponse().getFirstName()+" "+editCustomerAccountDetailResponse.getResponse().getLastName());
                                getMvpView().editCustomerAccountDetail(editCustomerAccountDetailResponse);

                            } else {
                                getMvpView().onError(editCustomerAccountDetailResponse.getResponse().getMessage());
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