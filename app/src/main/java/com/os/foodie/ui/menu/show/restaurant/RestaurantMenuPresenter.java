package com.os.foodie.ui.menu.show.restaurant;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemRequest;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemResponse;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuRequest;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuResponse;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemRequest;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantMenuPresenter<V extends RestaurantMenuMvpView> extends BasePresenter<V> implements RestaurantMenuMvpPresenter<V> {

    public RestaurantMenuPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantMenuList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantMenuList(new GetRestaurantMenuRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetRestaurantMenuResponse>() {
                        @Override
                        public void accept(GetRestaurantMenuResponse getRestaurantMenuResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (getRestaurantMenuResponse.getResponse().getStatus() == 1) {

                                if (getRestaurantMenuResponse.getResponse().getDishes() != null) {
                                    getMvpView().createRestaurantMenu(getRestaurantMenuResponse.getResponse().getDishes());
                                } else {
                                    Log.d("Error", ">>Err");
                                    getMvpView().onError("Menu is empty");
                                }

                            } else {
                                getMvpView().onError(R.string.some_error);
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
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void changeStatusRestaurantMenuList(final Dish dish/*String dishId, String status*/) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .changeStatusRestaurantMenuItem(new StatusMenuItemRequest(dish.getDishId(), dish.getAvail()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<StatusMenuItemResponse>() {
                        @Override
                        public void accept(StatusMenuItemResponse statusMenuItemResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (statusMenuItemResponse.getResponse().getStatus() == 1) {

                                if (dish.getAvail().equals("1")) {
                                    getMvpView().onError(R.string.item_activated);
                                } else {
                                    getMvpView().onError(R.string.item_deactivated);
                                }

                            } else {

                                if (dish.getAvail().equals("1")) {
                                    dish.setAvail("0");
                                } else {
                                    dish.setAvail("1");
                                }

                                getMvpView().onError(R.string.some_error);
                            }

                            getMvpView().notifyDataSetChanged();

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
    public void deleteRestaurantMenuList(final Dish dish) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .deleteRestaurantMenuItem(new DeleteMenuItemRequest(dish.getDishId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<DeleteMenuItemResponse>() {
                        @Override
                        public void accept(DeleteMenuItemResponse deleteMenuItemResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (deleteMenuItemResponse.getResponse().getStatus() == 1) {

                                getMvpView().onError(deleteMenuItemResponse.getResponse().getMessage());

                                getMvpView().onMenuItemDelete(dish);

                            } else {

                                getMvpView().onError(R.string.some_error);
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
        getCompositeDisposable().dispose();
    }
}