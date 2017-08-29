package com.os.foodie.ui.merchantdetail;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailRequest;
import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailResponse;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailRequest;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MerchantDetailPresenter<V extends MerchantDetailMvpView> extends BasePresenter<V> implements MerchantDetailMvpPresenter<V> {

    public MerchantDetailPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void setMerchantDetails(SetMerchantDetailRequest merchantDetailRequest, String confirmAccountNumber) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (merchantDetailRequest.getAccountHolderName() == null || merchantDetailRequest.getAccountHolderName().isEmpty()) {
                getMvpView().onError(R.string.empty_account_holder_name);
                return;
            }

            if (merchantDetailRequest.getBankName() == null || merchantDetailRequest.getBankName().isEmpty()) {
                getMvpView().onError(R.string.empty_bank_name);
                return;
            }

            if (merchantDetailRequest.getAccountNumber() == null || merchantDetailRequest.getAccountNumber().isEmpty()) {
                getMvpView().onError(R.string.empty_account_number);
                return;
            }

            if (confirmAccountNumber == null || confirmAccountNumber.isEmpty()) {
                getMvpView().onError(R.string.empty_confirm_account_number);
                return;
            }

            if (!merchantDetailRequest.getAccountNumber().equals(confirmAccountNumber)) {
                getMvpView().onError(R.string.not_match_account_number);
                return;
            }

            if (merchantDetailRequest.getIfscCode() == null || merchantDetailRequest.getIfscCode().isEmpty()) {
                getMvpView().onError(R.string.empty_ifsc);
                return;
            }

            merchantDetailRequest.setRestaurantId(getDataManager().getCurrentUserId());

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .setMerchantDetail(merchantDetailRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SetMerchantDetailResponse>() {
                        @Override
                        public void accept(SetMerchantDetailResponse merchantDetailResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (merchantDetailResponse.getResponse().getIsDeleted() != null && merchantDetailResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), merchantDetailResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (merchantDetailResponse.getResponse().getStatus() == 1) {
                                getMvpView().onError(merchantDetailResponse.getResponse().getMessage());
                                getMvpView().onMerchantDetailUpdationSuccess();

                            } else {
                                getMvpView().onError(merchantDetailResponse.getResponse().getMessage());
                                getMvpView().onMerchantDetailUpdationFail();
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
    public void getMerchantDetails() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getMerchantDetail(new GetMerchantDetailRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetMerchantDetailResponse>() {
                        @Override
                        public void accept(GetMerchantDetailResponse merchantDetailResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (merchantDetailResponse.getResponse().getIsDeleted() != null && merchantDetailResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), merchantDetailResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (merchantDetailResponse.getResponse().getStatus() == 1) {
//                                getMvpView().onError(merchantDetailResponse.getResponse().getMessage());
                                getMvpView().setMerchantDetail(merchantDetailResponse);
                            } else {
                                getMvpView().onError(merchantDetailResponse.getResponse().getMessage());
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
