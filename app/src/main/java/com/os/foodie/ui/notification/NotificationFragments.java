package com.os.foodie.ui.notification;

import android.os.Bundle;
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
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class NotificationFragments extends BaseFragment implements NotificationMvpView, View.OnClickListener {

    public static final String TAG = "NotificationFragments";

    private TextView alertTv;
    private SwipeRefreshLayout notificationSwipeRefresh;
    private RecyclerView notificationListRv;
    NotificationAdapter notificationAdapter;

    AppDataManager appDataManager;
    private NotificationMvpPresenter<NotificationMvpView> notificationMvpPresenter;

    public NotificationFragments() {
        // Required empty public constructor
    }

    public static NotificationFragments newInstance() {
        Bundle args = new Bundle();
        NotificationFragments fragment = new NotificationFragments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_notification, container, false);
        initView(view);

        setHasOptionsMenu(true);

        initPresenter();
        notificationMvpPresenter.onAttach(this);
        if (appDataManager.getCurrentUserType().equals(AppConstants.CUSTOMER))
            notificationMvpPresenter.getNotificationList(appDataManager.getCurrentUserId(), "");
        else
            notificationMvpPresenter.getNotificationList("", appDataManager.getCurrentUserId());
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
            notificationSwipeRefresh.setVisibility(View.VISIBLE);
            alertTv.setVisibility(View.GONE);
            notificationAdapter = new NotificationAdapter(getActivity(), notificationList.getResponse().getNotificationList(), notificationMvpPresenter);
            notificationListRv.setAdapter(notificationAdapter);
        }
    }

    private void initView(View rootView) {
        alertTv = (TextView) rootView.findViewById(R.id.alert_tv);
        notificationSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.notification_swipe_refresh);
        notificationListRv = (RecyclerView) rootView.findViewById(R.id.notification_list);
        notificationListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}