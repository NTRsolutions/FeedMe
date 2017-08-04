package com.os.foodie.ui.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.notification.NotificationListResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.adapter.recyclerview.NotificationAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListFragment;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class NotificationFragments extends BaseFragment implements NotificationMvpView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "NotificationFragments";

    private TextView alertTv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView notificationListRv;
    private NotificationAdapter notificationAdapter;

    public static NotificationFragments notificationFragments;

    AppDataManager appDataManager;
    private NotificationMvpPresenter<NotificationMvpView> notificationMvpPresenter;

    public NotificationFragments() {
        // Required empty public constructor
    }

    public static NotificationFragments newInstance() {
        Bundle args = new Bundle();
        NotificationFragments fragment = new NotificationFragments();
        fragment.setArguments(args);
        notificationFragments = fragment;
        return fragment;
    }

    public static NotificationFragments getInstance() {
        return notificationFragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_notification, container, false);
        initView(view);

        setHasOptionsMenu(true);

        initPresenter();
        notificationMvpPresenter.onAttach(this);

        if (appDataManager.getCurrentUserType().equals(AppConstants.CUSTOMER)) {
            notificationMvpPresenter.getNotificationList(appDataManager.getCurrentUserId(), "", null);
        } else {
            notificationMvpPresenter.getNotificationList("", appDataManager.getCurrentUserId(), null);
        }

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);
        appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);

        notificationMvpPresenter = new NotificationPresenter<>(appDataManager, compositeDisposable);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof CustomerMainActivity)
            ((CustomerMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.notification));
        else
            ((RestaurantMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.notification));
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setNotificationList(NotificationListResponse notificationList) {

        if (notificationList.getResponse().getNotificationList().size() > 0) {
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            notificationListRv.setVisibility(View.VISIBLE);
            alertTv.setVisibility(View.GONE);
            notificationAdapter = new NotificationAdapter(getActivity(), notificationList.getResponse().getNotificationList(), notificationMvpPresenter);
            notificationListRv.setAdapter(notificationAdapter);
        } else {
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            notificationListRv.setVisibility(View.GONE);
            alertTv.setVisibility(View.VISIBLE);
//            notificationAdapter = new NotificationAdapter(getActivity(), notificationList.getResponse().getNotificationList(), notificationMvpPresenter);
//            notificationListRv.setAdapter(notificationAdapter);
        }
    }

    private void initView(View rootView) {
        alertTv = (TextView) rootView.findViewById(R.id.alert_tv);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.notification_swipe_refresh);
        notificationListRv = (RecyclerView) rootView.findViewById(R.id.notification_list);
        notificationListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

        if (appDataManager.getCurrentUserType().equals(AppConstants.CUSTOMER)) {
            notificationMvpPresenter.getNotificationList(appDataManager.getCurrentUserId(), "", swipeRefreshLayout);
        } else {
            notificationMvpPresenter.getNotificationList("", appDataManager.getCurrentUserId(), swipeRefreshLayout);
        }
    }

    public void newOrder() {

        Fragment fragment = null;

        if (appDataManager.getCurrentUserType().equals(AppConstants.CUSTOMER)) {
            fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_customer_main_cl_fragment);
        } else {
            fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_restaurant_main_cl_fragment);
        }

        if (fragment instanceof NotificationFragments) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (appDataManager.getCurrentUserType().equals(AppConstants.CUSTOMER)) {
                        notificationMvpPresenter.getNotificationList(appDataManager.getCurrentUserId(), "", null);
                    } else {
                        notificationMvpPresenter.getNotificationList("", appDataManager.getCurrentUserId(), null);
                    }
                }
            });
        }
    }
}