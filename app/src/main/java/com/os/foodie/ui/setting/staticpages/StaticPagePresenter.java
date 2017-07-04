package com.os.foodie.ui.setting.staticpages;

import android.util.Log;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.staticpage.StaticPageRequest;
import com.os.foodie.data.network.model.staticpage.StaticPageResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StaticPagePresenter<V extends StaticPageMvpView> extends BasePresenter<V> implements StaticPageMvpPresenter<V> {

    public StaticPagePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void GetStaticData(String pageslug)
    {
        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .staticPage(new StaticPageRequest(pageslug))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<StaticPageResponse>() {
                        @Override
                        public void accept(StaticPageResponse staticPageResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (staticPageResponse.getResponse().getStatus() == 1)
                            {
                                getMvpView().onStaticData(staticPageResponse.getResponse().getDescription());

                            } else {
                                getMvpView().onError(staticPageResponse.getResponse().getMessage());
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
}