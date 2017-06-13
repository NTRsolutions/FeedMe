package com.os.foodie.ui.deliveryaddress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.setting.SettingsFragment;

public class DeliveryAddressFragment extends BaseFragment implements DeliveryAddressMvpView, View.OnClickListener {

    public static final String TAG = "DeliveryAddressFragment";

    private DeliveryAddressMvpPresenter<DeliveryAddressMvpView> deliveryAddressMvpPresenter;

    public DeliveryAddressFragment() {
    }

    public static DeliveryAddressFragment newInstance() {
        Bundle args = new Bundle();
        DeliveryAddressFragment fragment = new DeliveryAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);

        deliveryAddressMvpPresenter = new DeliveryAddressPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        deliveryAddressMvpPresenter.onAttach(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((CustomerMainActivity) getActivity()).setTitle("Delivery Address");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void setUp(View view) {

    }
}