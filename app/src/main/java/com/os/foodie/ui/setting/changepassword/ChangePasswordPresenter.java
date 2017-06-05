package com.os.foodie.ui.setting.changepassword;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.changepassword.ChangePasswordRequest;
import com.os.foodie.data.network.model.changepassword.ChangePasswordResponse;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpRequest;
import com.os.foodie.data.network.model.signup.customer.CustomerSignUpResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;
import com.os.foodie.utils.ValidationUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordPresenter<V extends ChangePasswordMvpView> extends BasePresenter<V> implements ChangePasswordMvpPresenter<V> {

    public ChangePasswordPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (currentPassword == null || currentPassword.isEmpty()) {
                getMvpView().onError(R.string.empty_current_password);
                return;
            }
            if (newPassword == null || newPassword.isEmpty()) {
                getMvpView().onError(R.string.empty_new_password);
                return;
            }
            if (confirmNewPassword == null || confirmNewPassword.isEmpty()) {
                getMvpView().onError(R.string.empty_confirm_new_password);
                return;
            }
            if (newPassword.length() < 6) {
                getMvpView().onError(R.string.minimum_password);
                return;
            }
            if (!newPassword.equals(confirmNewPassword)) {
                getMvpView().onError(R.string.not_match_new_password);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .changePassword(new ChangePasswordRequest(getDataManager().getCurrentUserId(), currentPassword, newPassword))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ChangePasswordResponse>() {
                        @Override
                        public void accept(ChangePasswordResponse changePasswordResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (changePasswordResponse.getResponse().getStatus() == 1) {

                                getMvpView().onError(changePasswordResponse.getResponse().getMessage());
                                getMvpView().onPasswordChanged();

                            } else {
                                getMvpView().onError(changePasswordResponse.getResponse().getMessage());
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
}