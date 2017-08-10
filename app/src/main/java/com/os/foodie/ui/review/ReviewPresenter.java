package com.os.foodie.ui.review;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.data.DataManager;
import com.os.foodie.data.network.model.payment.delete.DeletePaymentCardRequest;
import com.os.foodie.data.network.model.payment.delete.DeletePaymentCardResponse;
import com.os.foodie.data.network.model.reviews.GetReviewsRequest;
import com.os.foodie.data.network.model.reviews.GetReviewsResponse;
import com.os.foodie.ui.base.BasePresenter;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReviewPresenter<V extends ReviewMvpView> extends BasePresenter<V> implements ReviewMvpPresenter<V> {

    public ReviewPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getReviews(String restaurantId) {

        if (NetworkUtils.isNetworkConnected(getMvpView().getContext())) {

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getReviews(new GetReviewsRequest(restaurantId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GetReviewsResponse>() {
                        @Override
                        public void accept(GetReviewsResponse reviewsResponse) throws Exception {

                            getMvpView().hideLoading();

                            if (reviewsResponse.getResponse().getIsDeleted() != null && reviewsResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

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

                                Toast.makeText(getMvpView().getContext(), reviewsResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (reviewsResponse.getResponse().getStatus() == 1) {

                                getMvpView().onReviewListReceived(reviewsResponse);

                            } else {

                                getMvpView().onReviewListReceived(reviewsResponse);
//                                getMvpView().onError(reviewsResponse.getResponse().getMessage());
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
}