package com.os.foodie.ui.earning;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.model.TempEarningModel;
import com.os.foodie.ui.adapter.recyclerview.EarningAdapter;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListFragment;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class EarningFragment extends BaseFragment implements EarningMvpView {

    public static final String TAG = "EarningFragment";

    private TextView tvAlert;
    private RecyclerView recyclerView;
    private EarningAdapter earningAdapter;
    private ArrayList<TempEarningModel> tempEarningModels;

    private EarningMvpPresenter<EarningMvpView> earningMvpPresenter;

    public EarningFragment() {
        // Required empty public constructor
    }

    public static EarningFragment newInstance() {
        Bundle args = new Bundle();
        EarningFragment fragment = new EarningFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_earning, container, false);

        initPresenter();
        earningMvpPresenter.onAttach(this);

        setUp(view);

        tempEarningModels = new ArrayList<TempEarningModel>();
        earningAdapter = new EarningAdapter(getActivity(), tempEarningModels);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(earningAdapter);

        for (int i = 0; i < 10; i++) {

            TempEarningModel tempEarningModel = new TempEarningModel();

            tempEarningModel.setEarningId("5465654" + i);
            tempEarningModel.setTransactionId("1654" + i);
            tempEarningModel.setDate("12 June 2017");
            tempEarningModel.setAmount("50");

            tempEarningModels.add(tempEarningModel);
        }

        earningAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RestaurantMainActivity) getActivity()).setTitle(getString(R.string.title_fragment_earning));
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        earningMvpPresenter = new EarningPresenter<>(appDataManager, compositeDisposable);
    }

    @Override
    protected void setUp(View view) {
        tvAlert = (TextView) view.findViewById(R.id.activity_earning_tv_empty_alert);
        recyclerView = (RecyclerView) view.findViewById(R.id.activity_earning_recyclerview);
    }

    @Override
    public void onDestroyView() {
        earningMvpPresenter.dispose();
        super.onDestroyView();
    }
}
