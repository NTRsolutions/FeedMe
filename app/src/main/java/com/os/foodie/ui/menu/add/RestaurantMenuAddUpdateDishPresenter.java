package com.os.foodie.ui.menu.add;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.coursetype.list.Course;
import com.os.foodie.data.network.model.coursetype.list.GetCourseTypeResponse;
import com.os.foodie.data.network.model.menu.add.AddMenuItemRequest;
import com.os.foodie.data.network.model.menu.add.AddMenuItemResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantMenuAddUpdateDishPresenter<V extends RestaurantMenuAddUpdateDishMvpView> extends BasePresenter<V> implements RestaurantMenuAddUpdateDishMvpPresenter<V> {

    public RestaurantMenuAddUpdateDishPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getCourseTypeList() {
        Log.d("Error", ">>Err");

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getCourseTypeList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetCourseTypeResponse>() {
                        @Override
                        public void accept(GetCourseTypeResponse courseTypeResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (courseTypeResponse.getResponse().getStatus() == 1) {

                                if (courseTypeResponse.getResponse().getData().get(0).getCourses() != null) {
                                    Log.d("getCourses", ">>" + ((ArrayList<Course>) courseTypeResponse.getResponse().getData().get(0).getCourses()).size());
                                    getMvpView().onCourseTypeReceived((ArrayList<Course>) courseTypeResponse.getResponse().getData().get(0).getCourses());
                                } else {
                                    Log.d("Error", ">>Err");
                                    getMvpView().onError("Course not found");
                                }

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
    public void addRestaurantMenuItem(String dishId, String courseId, String itemName, String price, String description, String vegNonVeg) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            if (courseId == null || courseId.isEmpty()) {
                getMvpView().onError(R.string.empty_menu_item_course_type);
                return;
            }
            if (itemName == null || itemName.isEmpty()) {
                getMvpView().onError(R.string.empty_menu_item_name);
                return;
            }
            if (price == null || price.isEmpty()) {
                getMvpView().onError(R.string.empty_menu_item_price);
                return;
            }
            if (description == null || description.isEmpty()) {
                getMvpView().onError(R.string.empty_menu_item_description);
                return;
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .addRestaurantMenuItem(new AddMenuItemRequest(getDataManager().getCurrentUserId(), dishId, courseId, vegNonVeg, itemName, description, price, "1"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddMenuItemResponse>() {
                        @Override
                        public void accept(AddMenuItemResponse addMenuItemResponse) throws Exception {

                            getMvpView().hideLoading();

                            Log.d("addMenuItemResponse", "onSuccess>>" + addMenuItemResponse.getResponse().getDishId().toString());

                            if (addMenuItemResponse.getResponse().getStatus() == 1) {

                                Log.d("Message", "onSuccess>>" + addMenuItemResponse.getResponse().getMessage());
                                getMvpView().onMenuItemAdded();

                            } else {
                                getMvpView().onError(addMenuItemResponse.getResponse().getMessage());
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.api_default_error);
//                            Log.d("Error", ">>Err" + throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }
}