package com.os.foodie.ui.setting.staticpages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.ui.base.BaseActivity;
import com.os.foodie.utils.AppConstants;

public class StaticPageActivity extends BaseActivity implements StaticPageMvpView {

    private TextView tvPageData;

    private StaticPageMvpPresenter<StaticPageMvpView> staticPageMvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(AppConstants.PAGE_NAME));
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_home_up_orange));

        staticPageMvpPresenter = new StaticPagePresenter<>(AppController.get(this).getAppDataManager(), AppController.get(this).getCompositeDisposable());
        staticPageMvpPresenter.onAttach(StaticPageActivity.this);

        initView();

        staticPageMvpPresenter.GetStaticData(getIntent().getStringExtra(AppConstants.SLUG));
    }

    @Override
    public void onStaticData(String data) {
        tvPageData.setText(Html.fromHtml(data));
    }

    @Override
    protected void setUp() {
    }

    private void initView() {
        tvPageData = (TextView) findViewById(R.id.activity_static_page_tv_data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }
}