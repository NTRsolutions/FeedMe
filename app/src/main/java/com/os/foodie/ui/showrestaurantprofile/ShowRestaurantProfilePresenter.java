package com.os.foodie.ui.showrestaurantprofile;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.details.CustomerRestaurantDetailsRequest;
import com.os.foodie.data.network.model.showrestaurantprofile.RestaurantProfileResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShowRestaurantProfilePresenter<V extends ShowRestaurantProfileMvpView> extends BasePresenter<V> implements ShowRestaurantProfileMvpPresenter<V> {

    public ShowRestaurantProfilePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantProfile(String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantProfile(new CustomerRestaurantDetailsRequest(getDataManager().getCurrentUserId(), restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<RestaurantProfileResponse>() {
                        @Override
                        public void accept(RestaurantProfileResponse restaurantDetailsResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (restaurantDetailsResponse.getResponse().getStatus() == 1) {

                                getMvpView().setRestaurantProfileDetail(restaurantDetailsResponse);

                            } else {
                                Log.d("getMessage", ">>Failed");
                               // getMvpView().onError(restaurantDetailsResponse.getResponse().getMessage());
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
        } else

        {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}