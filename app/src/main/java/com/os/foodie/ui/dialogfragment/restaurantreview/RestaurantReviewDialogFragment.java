package com.os.foodie.ui.dialogfragment.restaurantreview;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.restaurantreview.RestaurantReviewRequest;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryPresenter;
import com.os.foodie.utils.AppConstants;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;

public class RestaurantReviewDialogFragment extends DialogFragment implements View.OnClickListener, RestaurantReviewMvpView {

    private CircleImageView restaurantRatingUserimageIv;
    private TextView restaurantRatingUsernameTv;
    private EditText restaurantRatingCommentEt;
    private AppCompatRatingBar restaurantRatingRatingbar;
    private RippleAppCompatButton restaurantRatingDoneBt;
    String restaurant_id = "", restaurant_name = "", restaurant_image = "", order_id = "";

    private RestaurantReviewMvpPresenter<RestaurantReviewMvpView> restaurantReviewMvpPresenter;

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();

        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_feedback_restaureant, container, false);

        initPresenter();

        initView(rootView);

        onLoad();

        return rootView;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        restaurantReviewMvpPresenter = new RestaurantReviewPresenter<>(appDataManager, compositeDisposable);

//        restaurantReviewMvpPresenter = new RestaurantReviewPresenter<>(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        restaurantReviewMvpPresenter.onAttach(this);
    }

    private void onLoad() {

        Bundle bundle = new Bundle();

        bundle = getArguments();

        restaurant_id = bundle.getString("restaurant_id");
        restaurant_name = bundle.getString("restaurant_name");
        restaurant_image = bundle.getString("restaurant_image");
        order_id = bundle.getString("order_id");

        restaurantRatingUsernameTv.setText(restaurant_name);

        Glide.with(getActivity())
                .load(restaurant_image)
//                .placeholder(ContextCompat.getDrawable(getActivity(), R.mipmap.img_placeholder))
                .error(ContextCompat.getDrawable(getActivity(), R.mipmap.img_placeholder))
                .into(restaurantRatingUserimageIv);
    }

    @Override
    public void onClick(View v) {

        if (restaurantRatingDoneBt.getId() == v.getId()) {

            if (restaurantRatingCommentEt.getText().toString().trim().length() == 0) {

                Log.d("Review", ">>Response");

                Snackbar snackbar = Snackbar.make(restaurantRatingDoneBt, getString(R.string.comment_error), Snackbar.LENGTH_LONG);
                snackbar.show();

                return;

            } else if (restaurantRatingRatingbar.getRating() == 0) {

                Log.d("Review", ">>Response");

                Snackbar snackbar = Snackbar.make(restaurantRatingDoneBt, getString(R.string.rate_error), Snackbar.LENGTH_LONG);
                snackbar.show();

                return;

            } else {

                RestaurantReviewRequest restaurantReviewRequest = new RestaurantReviewRequest();

                restaurantReviewRequest.setOrderId(order_id);
                restaurantReviewRequest.setRestaurantId(restaurant_id);
                restaurantReviewRequest.setReview(restaurantRatingCommentEt.getText().toString().trim());
                restaurantReviewRequest.setRating(restaurantRatingRatingbar.getRating() + "");

                restaurantReviewMvpPresenter.SendRestaurantReview(restaurantReviewRequest);
            }
        }
    }

    private void initView(View rootView) {

        restaurantRatingUserimageIv = (CircleImageView) rootView.findViewById(R.id.restaurant_rating_userimage_iv);
        restaurantRatingUsernameTv = (TextView) rootView.findViewById(R.id.restaurant_rating_username_tv);
        restaurantRatingCommentEt = (EditText) rootView.findViewById(R.id.restaurant_rating_comment_et);
        restaurantRatingRatingbar = (AppCompatRatingBar) rootView.findViewById(R.id.restaurant_rating_ratingbar);
        restaurantRatingDoneBt = (RippleAppCompatButton) rootView.findViewById(R.id.restaurant_rating_done_bt);

        restaurantRatingDoneBt.setOnClickListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(@StringRes int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onErrorLong(@StringRes int resId) {

    }

    @Override
    public void onErrorLong(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void finish() {

        ((OrderHistoryDetailActivity) getActivity()).feedbackComplete();

        dismiss();
    }

    @Override
    public void onDestroyView() {
        restaurantReviewMvpPresenter.dispose();
        super.onDestroyView();
    }

    /* Bundle bundle = new Bundle();
            bundle.putString("restaurant_id", "");
            bundle.putString("restaurant_name", "");
            bundle.putString("restaurant_image", "");
            bundle.putString("order_id", "");
    RestaurantReviewDialogFragment restaurantReviewDialogFragment = new RestaurantReviewDialogFragment();
            restaurantReviewDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
            restaurantReviewDialogFragment.setArguments(bundle);
            restaurantReviewDialogFragment.show(getChildFragmentManager(), "RestaurantReviewDialogFragment");*/
}