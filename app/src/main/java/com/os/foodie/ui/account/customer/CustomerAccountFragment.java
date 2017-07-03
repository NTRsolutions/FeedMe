package com.os.foodie.ui.account.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountDetailResponse;
import com.os.foodie.data.network.model.account.edit.customer.EditCustomerAccountRequest;
import com.os.foodie.data.network.model.account.GetAccountDetailResponse;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.home.customer.CustomerHomePresenter;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerAccountFragment extends BaseFragment implements CustomerAccountMvpView, View.OnClickListener {

    public static final String TAG = "CustomerAccountFragment";

    private EditText etFirstName, etLastName, etEmail, etPhoneNum;
    private RippleAppCompatButton btSave;

    private CustomerAccountMvpPresenter<CustomerAccountMvpView> customerAccountMvpPresenter;

    public CustomerAccountFragment() {
    }

    public static CustomerAccountFragment newInstance() {
        Bundle args = new Bundle();
        CustomerAccountFragment fragment = new CustomerAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_account, container, false);
        initView(view);

        ((CustomerMainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.title_fragment_customer_home));

        initPresenter();
//        customerAccountMvpPresenter = new CustomerAccountPresenter(AppController.get(getActivity()).getAppDataManager(), AppController.get(getActivity()).getCompositeDisposable());
        customerAccountMvpPresenter.onAttach(this);

        customerAccountMvpPresenter.getCustomerAccountDetail();
//        fabAdd.setOnClickListener(this);

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        customerAccountMvpPresenter = new CustomerAccountPresenter(appDataManager, compositeDisposable);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CustomerMainActivity) getActivity()).setTitle(getString(R.string.action_my_account));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_with_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {
            btSave.setVisibility(View.VISIBLE);
            setHasOptionsMenu(false);
            setEditTextEnable(true);
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == btSave.getId()) {

            EditCustomerAccountRequest editCustomerAccountRequest = new EditCustomerAccountRequest();
            editCustomerAccountRequest.setFirstName(etFirstName.getText().toString().trim());
            editCustomerAccountRequest.setLastName(etLastName.getText().toString().trim());
            editCustomerAccountRequest.setEmail(etEmail.getText().toString().trim());
            editCustomerAccountRequest.setMobileNumber(etPhoneNum.getText().toString().trim());
            editCustomerAccountRequest.setUserId(AppController.get(getActivity()).getAppDataManager().getCurrentUserId());

            customerAccountMvpPresenter.editCustomerAccountDetail(editCustomerAccountRequest);
        }
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void setAccountDetail(GetAccountDetailResponse getAccountDetailResponse) {
        etFirstName.setText(getAccountDetailResponse.getResponse().getFirstName());
        etLastName.setText(getAccountDetailResponse.getResponse().getLastName());
        etEmail.setText(getAccountDetailResponse.getResponse().getEmail());
        etPhoneNum.setText(getAccountDetailResponse.getResponse().getMobileNumber());
    }

    @Override
    public void editCustomerAccountDetail(EditCustomerAccountDetailResponse editCustomerAccountDetailResponse) {
        setHasOptionsMenu(true);
        btSave.setVisibility(View.GONE);
        ((CustomerMainActivity) getActivity()).setCustomerName();
        setEditTextEnable(false);
    }

    private void initView(View view) {

        setHasOptionsMenu(true);

        etFirstName = (EditText) view.findViewById(R.id.fragment_customer_account_et_first_name);
        etLastName = (EditText) view.findViewById(R.id.fragment_customer_account_et_last_name);
        etEmail = (EditText) view.findViewById(R.id.fragment_customer_account_et_email);
        etPhoneNum = (EditText) view.findViewById(R.id.fragment_customer_account_et_phone_number);

        btSave = (RippleAppCompatButton) view.findViewById(R.id.activity_customer_bt_save_profile);
        btSave.setOnClickListener(this);
    }

    private void setEditTextEnable(boolean setEnable) {
        etFirstName.setEnabled(setEnable);
        etLastName.setEnabled(setEnable);
        etPhoneNum.setEnabled(setEnable);
        etEmail.setEnabled(setEnable);
    }

    @Override
    public void onDestroyView() {
        customerAccountMvpPresenter.dispose();
//        customerAccountMvpPresenter.onDetach();
        super.onDestroyView();
    }
}