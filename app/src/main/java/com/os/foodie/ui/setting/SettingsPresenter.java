package com.os.foodie.ui.setting;

import android.support.annotation.StringRes;
import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.notification.SetNotificationResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements SettingsMvpPresenter<V> {

    public SettingsPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }

    @Override
    public void onError(@StringRes int resId) {
        getMvpView().onError(resId);
    }

    @Override
    public void setLanguage(String languageCode) {
        getDataManager().setLanguage(languageCode);
    }

    @Override
    public void SetNotificationStatus() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            Log.d("SetNotificationStatus", ">>Called");

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .setNotification(getDataManager().getCurrentUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SetNotificationResponse>() {
                        @Override
                        public void accept(SetNotificationResponse setNotificationResponse) throws Exception {

                            Log.d("SetNotificationStatus", ">>Response");

                            getMvpView().hideLoading();

                            if (setNotificationResponse.getResponse().getStatus() == 1) {
                                getMvpView().getNotificationStatus(setNotificationResponse.getResponse().getIsNotification());

                            } else {
                                getMvpView().onError(setNotificationResponse.getResponse().getMessage());
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