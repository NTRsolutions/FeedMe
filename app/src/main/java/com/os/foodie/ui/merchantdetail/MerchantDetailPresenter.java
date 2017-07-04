package com.os.foodie.ui.merchantdetail;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.disposables.CompositeDisposable;

public class MerchantDetailPresenter<V extends MerchantDetailMvpView> extends BasePresenter<V> implements MerchantDetailMvpPresenter<V> {

    public MerchantDetailPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getRestaurantList() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

//            if (email == null || email.isEmpty()) {
//                getMvpView().onError(R.string.empty_email);
//                return;
//            }
//
//            getMvpView().showLoading();
//
//            getCompositeDisposable().add(getDataManager()
//                    .getRestaurantList(new GetRestaurantListRequest(getDataManager().getCurrentUserId(), "", filters))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<GetRestaurantListResponse>() {
//                        @Override
//                        public void accept(GetRestaurantListResponse getRestaurantListResponse) throws Exception {
//
//                            getMvpView().hideLoading();
//
//                            if (getRestaurantListResponse.getResponse().getStatus() == 1) {
//
//                                if (getRestaurantListResponse.getResponse().getRestaurantList() != null) {
//                                    Log.d("size", ">>" + getRestaurantListResponse.getResponse().getRestaurantList().size());
//                                    getMvpView().notifyDataSetChanged((ArrayList<RestaurantList>) getRestaurantListResponse.getResponse().getRestaurantList());
//
//                                } else {
//                                    Log.d("Error", ">>Err");
////                                    getMvpView().onError(R.string.no_restaurant);
//                                    getMvpView().notifyDataSetChanged();
//                                }
//
//                            } else {
////                                getMvpView().onError("No Restaurant found");
//                                getMvpView().notifyDataSetChanged();
//                            }
//
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            getMvpView().hideLoading();
//                            Log.d("Error", ">>ErrThorwed");
//                            getMvpView().onError(R.string.api_default_error);
//                            Log.d("Error", ">>Err" + throwable.getMessage());
//                        }
//                    }));
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void dispose() {
        getCompositeDisposable().dispose();
    }
}
