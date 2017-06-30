package com.os.foodie.ui.discount.add;

import android.app.DatePickerDialog;
import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.discount.add.AddDiscountRequest;
import com.os.foodie.data.network.model.discount.add.AddDiscountResponse;
import com.os.foodie.data.network.model.discount.dishlist.DishListRequest;
import com.os.foodie.data.network.model.discount.dishlist.DishListResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DiscountAddPresenter<V extends DiscountAddMvpView> extends BasePresenter<V> implements DiscountAddMvpPresenter<V>
{

    public DiscountAddPresenter(DataManager dataManager, CompositeDisposable compositeDisposable)
    {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void addDiscount(AddDiscountRequest addDiscountRequest)
    {
        addDiscountRequest.setRestaurantId(getDataManager().getCurrentUserId());

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext()))
        {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .addDiscount(addDiscountRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddDiscountResponse>() {
                        @Override
                        public void accept(AddDiscountResponse addDiscountResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (addDiscountResponse.getResponse().getStatus() == 1)
                            {
                                Log.d("getMessage", ">>" + addDiscountResponse.getResponse().getMessage());
                                //getMvpView().onPasswordReset(forgotPasswordResponse.getResponse().getMessage());
                                getMvpView().onFinish();

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
    public void showDishList()
    {
        if (NetworkUtils.isNetworkConnected(getMvpView().getContext()))
        {
            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .showDishlist(new DishListRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DishListResponse>() {
                        @Override
                        public void accept(DishListResponse dishListResponse) throws Exception {

                            getMvpView().hideLoading();
                            if (dishListResponse.getResponse().getStatus() == 1)
                            {
                                getMvpView().onShowDishList(dishListResponse.getResponse().getDishData());

                            } else {
                                getMvpView().onError(dishListResponse.getResponse().getMessage());
                            }
                        }
                    }, new Consumer<Throwable>()
                    {
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
    public void showStartDate()
    {

    }

    @Override
    public void showEndDate()
    {

    }


}