package com.os.foodie.ui.filters;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineTypeResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FiltersPresenter<V extends FiltersMvpView> extends BasePresenter<V> implements FiltersMvpPresenter<V> {

    public FiltersPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
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

                            Log.d("Message", ">>" + cuisineTypeResponse.getResponse().getMessage());
                            getMvpView().hideLoading();

                            if (cuisineTypeResponse.getResponse().getStatus() == 0) {
                                getMvpView().onError(cuisineTypeResponse.getResponse().getMessage());
                            } else {
                                Log.d("getCuisineType", ">>" + cuisineTypeResponse.getResponse().getData().get(0).getCuisineType().size());
                                getMvpView().onCuisineTypeListReceive((ArrayList<CuisineType>) cuisineTypeResponse.getResponse().getData().get(0).getCuisineType());
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
        getCompositeDisposable().dispose();
    }
}