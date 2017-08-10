package com.os.foodie.ui.menu.show.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemRequest;
import com.os.foodie.data.network.model.menu.delete.DeleteMenuItemResponse;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuRequest;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuResponse;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemRequest;
import com.os.foodie.data.network.model.menu.status.StatusMenuItemResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpView;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RestaurantMenuPresenter<V extends RestaurantMenuMvpView> extends BasePresenter<V> implements RestaurantMenuMvpPresenter<V> {
    DataManager dataManager;
    public RestaurantMenuPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
        this.dataManager=dataManager;
    }

    @Override
    public void getRestaurantMenuList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantMenuList(new GetRestaurantMenuRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<GetRestaurantMenuResponse>() {
                                @Override
                                public void onNext(GetRestaurantMenuResponse getRestaurantMenuResponse) {
                                    System.out.print("next");
                                    getMvpView().hideLoading();

                                    if (getRestaurantMenuResponse.getResponse().getStatus() == 1) {

                                        if (getRestaurantMenuResponse.getResponse().getDishes() != null) {
                                            getMvpView().createRestaurantMenu(getRestaurantMenuResponse.getResponse().getDishes());
                                        } else {
                                            Log.d("Error", ">>Err");
                                            getMvpView().onError(R.string.empty_menu);
                                        }

                                    } else {
                                        getMvpView().onError(getRestaurantMenuResponse.getResponse().getMessage());
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    getMvpView().onError(e.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                    getMvpView().hideLoading();
                                    System.out.print("complete");

                                }
                            })
                   /* .subscribe(new Consumer<GetRestaurantMenuResponse>() {
                        @Override
                        public void accept(GetRestaurantMenuResponse getRestaurantMenuResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (getRestaurantMenuResponse.getResponse().getStatus() == 1) {

                                if (getRestaurantMenuResponse.getResponse().getDishes() != null) {
                                    getMvpView().createRestaurantMenu(getRestaurantMenuResponse.getResponse().getDishes());
                                } else {
                                    Log.d("Error", ">>Err");
                                    getMvpView().onError(R.string.empty_menu);
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
                    })*/);
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

                            if (statusMenuItemResponse.getResponse().getIsDeleted() != null && statusMenuItemResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), statusMenuItemResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

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

                            if (deleteMenuItemResponse.getResponse().getIsDeleted() != null && deleteMenuItemResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), deleteMenuItemResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

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

        getMvpView().hideLoading();

        getCompositeDisposable().dispose();
    }

    @Override
    public DataManager getAppDataManager() {
        return dataManager;
    }
}