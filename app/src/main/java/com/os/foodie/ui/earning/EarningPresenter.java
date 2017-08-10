package com.os.foodie.ui.earning;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.earning.GetEarningRequest;
import com.os.foodie.data.network.model.earning.GetEarningResponse;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuRequest;
import com.os.foodie.data.network.model.menu.show.restaurant.GetRestaurantMenuResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class EarningPresenter<V extends EarningMvpView> extends BasePresenter<V> implements EarningMvpPresenter<V> {

    public EarningPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getEarnings() {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getEarnings(new GetEarningRequest(getDataManager().getCurrentUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetEarningResponse>() {
                        @Override
                        public void accept(GetEarningResponse earningResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (earningResponse.getResponse().getIsDeleted() != null && earningResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), earningResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (earningResponse.getResponse().getStatus() == 1) {

                                if (earningResponse.getResponse().getEarnings() == null || earningResponse.getResponse().getEarnings().isEmpty()) {

                                    getMvpView().onError(earningResponse.getResponse().getMessage());
                                }

                                getMvpView().setEarnings(earningResponse);

                            } else {

                                getMvpView().setEarnings(earningResponse);
//                                getMvpView().onError(earningResponse.getResponse().getMessage());
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