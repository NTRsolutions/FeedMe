package com.os.foodie.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.filters.FiltersPresenter;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.ui.setting.changepassword.ChangePasswordActivity;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class SettingsFragment extends BaseFragment implements SettingsMvpView, View.OnClickListener {

    public static final String TAG = "SettingsFragment";

    private Switch switchNotification;

    private LinearLayout llNotification, llChangePassword, llContactUs;
    private LinearLayout llTermsOfUse, llPrivacyPolicy, llFaq, llRateUs;

    private SettingsMvpPresenter<SettingsMvpView> settingsMvpPresenter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initPresenter();
//        settingsMvpPresenter = new SettingsPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        settingsMvpPresenter.onAttach(this);

        switchNotification = (Switch) view.findViewById(R.id.fragment_settings_switch_notification);

        llNotification = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_notification);
        llChangePassword = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_change_password);
        llContactUs = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_contact_us);
        llTermsOfUse = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_terms_of_use);
        llPrivacyPolicy = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_privacy_policy);
        llFaq = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_faq);
        llRateUs = (LinearLayout) view.findViewById(R.id.fragment_settings_ll_rate_us);

        llNotification.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        llTermsOfUse.setOnClickListener(this);
        llPrivacyPolicy.setOnClickListener(this);
        llFaq.setOnClickListener(this);
        llRateUs.setOnClickListener(this);

        return view;
    }

    public void initPresenter(){

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        settingsMvpPresenter = new SettingsPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof CustomerMainActivity) {
            ((CustomerMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.title_fragment_settings));
        } else {
            ((RestaurantMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.title_fragment_settings));
        }
    }

    @Override
    public void onClick(View v) {

        if (llNotification.getId() == v.getId()) {

            switchNotification.setChecked((switchNotification.isChecked() ? false : true));

        } else if (llChangePassword.getId() == v.getId()) {

            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onDestroyView() {
        settingsMvpPresenter.dispose();
//        settingsMvpPresenter.onDetach();
        super.onDestroyView();
    }
}