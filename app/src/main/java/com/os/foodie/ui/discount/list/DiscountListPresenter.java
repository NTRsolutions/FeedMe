package com.os.foodie.ui.discount.list;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.discount.add.AddDiscountResponse;
import com.os.foodie.data.network.model.discount.dishlist.DishListRequest;
import com.os.foodie.data.network.model.discount.list.DeleteDiscountRequest;
import com.os.foodie.data.network.model.discount.list.DiscountList;
import com.os.foodie.data.network.model.discount.list.DiscountListResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DiscountListPresenter<V extends DiscountListMvpView> extends BasePresenter<V> implements DiscountListMvpPresenter<V> {

    public DiscountListPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void DiscountListing() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .DiscountList(new DishListRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DiscountListResponse>() {
                        @Override
                        public void accept(DiscountListResponse discountListResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (discountListResponse.getResponse().getIsDeleted() != null && discountListResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

                                Locale locale = new Locale(AppConstants.LANG_EN);
                                Locale.setDefault(locale);

                                Configuration config = new Configuration();
                                config.locale = locale;

                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), discountListResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (discountListResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + discountListResponse.getResponse().getMessage());
                                //getMvpView().onPasswordReset(forgotPasswordResponse.getResponse().getMessage());
                                ArrayList<DiscountList> discountList = discountListResponse.getResponse().getDiscountList();
                                getMvpView().onShowDiscountList(discountList);

                            } else {

                                ArrayList<DiscountList> discountList = new ArrayList<DiscountList>();
                                getMvpView().onShowDiscountList(discountList);
//                                getMvpView().onError(discountListResponse.getResponse().getMessage());
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
    public void deleteDiscountList(String discount_id) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .deleteDiscount(new DeleteDiscountRequest(discount_id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddDiscountResponse>() {
                        @Override
                        public void accept(AddDiscountResponse addDiscountResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (addDiscountResponse.getResponse().getIsDeleted() != null && addDiscountResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

                                Locale locale = new Locale(AppConstants.LANG_EN);
                                Locale.setDefault(locale);

                                Configuration config = new Configuration();
                                config.locale = locale;

                                getMvpView().getContext().getResources().updateConfiguration(config, getMvpView().getContext().getResources().getDisplayMetrics());

                                Intent intent = new Intent(getMvpView().getContext(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getMvpView().getContext().startActivity(intent);

                                getDataManager().setLanguage(AppConstants.LANG_EN);

                                setUserAsLoggedOut();

                                Toast.makeText(getMvpView().getContext(), addDiscountResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (addDiscountResponse.getResponse().getStatus() == 1) {
                                Log.d("getMessage", ">>" + addDiscountResponse.getResponse().getMessage());
                                //getMvpView().onPasswordReset(forgotPasswordResponse.getResponse().getMessage());
                                getMvpView().onRefreshList();

                            } else {
                                getMvpView().onError(addDiscountResponse.getResponse().getMessage());
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
    public String getCurrency() {
        return getDataManager().getCurrency();
    }

    @Override
    public void dispose() {

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }
}