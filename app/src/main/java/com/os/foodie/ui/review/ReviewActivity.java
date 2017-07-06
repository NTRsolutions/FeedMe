package com.os.foodie.ui.review;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.payment.getall.Card;
import com.os.foodie.data.network.model.reviews.GetReviewsResponse;
import com.os.foodie.data.network.model.reviews.ReviewList;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.PaymentMethodAdapter;
import com.os.foodie.ui.adapter.recyclerview.ReviewAdapter;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class ReviewActivity extends BaseActivity implements ReviewMvpView {

    private RecyclerView recyclerView;
    private TextView tvAlert;

    private ArrayList<ReviewList> reviewArrayList;
    private ReviewAdapter reviewAdapter;

    private String restaurantId = "";

    private ReviewMvpPresenter<ReviewMvpView> reviewMvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        setUp();

        initPresenter();
        reviewMvpPresenter.onAttach(this);

        reviewArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.activity_review_recyclerview);
        tvAlert = (TextView) findViewById(R.id.activity_review_tv_alert);

        reviewAdapter = new ReviewAdapter(this, reviewArrayList);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reviewAdapter);

        reviewMvpPresenter.getReviews(restaurantId);
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(this, appPreferencesHelper, appApiHelpter);
        reviewMvpPresenter = new ReviewPresenter(appDataManager, compositeDisposable);
    }

    @Override
    protected void setUp() {

        if (getIntent().hasExtra(AppConstants.RESTAURANT_ID)) {
            restaurantId = getIntent().getStringExtra(AppConstants.RESTAURANT_ID);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void onReviewListReceived(GetReviewsResponse reviewsResponse) {

        reviewArrayList.clear();

        if (reviewsResponse.getResponse().getReviewList() != null && !reviewsResponse.getResponse().getReviewList().isEmpty()) {

            tvAlert.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            reviewArrayList.addAll(reviewsResponse.getResponse().getReviewList());

        } else {

            tvAlert.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        reviewMvpPresenter.dispose();
        super.onDestroy();
    }
}
