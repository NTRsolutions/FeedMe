package com.os.foodie.ui.merchantdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailResponse;
import com.os.foodie.data.network.model.merchantdetails.get.Response;
import com.os.foodie.data.network.model.merchantdetails.get.Restaurant;
import com.os.foodie.data.network.model.merchantdetails.set.SetMerchantDetailRequest;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.base.BaseFragment;
import com.os.foodie.ui.main.customer.CustomerMainActivity;
import com.os.foodie.ui.main.restaurant.RestaurantMainActivity;
import com.os.foodie.utils.AppConstants;

import io.reactivex.disposables.CompositeDisposable;

public class MerchantDetailFragments extends BaseFragment implements MerchantDetailMvpView, View.OnClickListener {

    public static final String TAG = "MerchantDetailFragments";

    private EditText etAccountHolderName, etBankName, etAccountNumber, etConfirmAccountNumber, etIFSC;
    private Button btSave, btCancel;

    private GetMerchantDetailResponse merchantDetailResponse;

    private MerchantDetailMvpPresenter<MerchantDetailMvpView> merchantDetailMvpPresenter;

    public MerchantDetailFragments() {
        // Required empty public constructor
    }

    public static MerchantDetailFragments newInstance() {
        Bundle args = new Bundle();
        MerchantDetailFragments fragment = new MerchantDetailFragments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_merchant_details, container, false);

        setHasOptionsMenu(true);

        initPresenter();
        merchantDetailMvpPresenter.onAttach(this);

        initView(view);

        merchantDetailMvpPresenter.getMerchantDetails();

        return view;
    }

    public void initPresenter() {

        AppApiHelpter appApiHelpter = new AppApiHelpter();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(getActivity(), AppConstants.PREFERENCE_DEFAULT);

        AppDataManager appDataManager = new AppDataManager(getActivity(), appPreferencesHelper, appApiHelpter);
        merchantDetailMvpPresenter = new MerchantDetailPresenter(appDataManager, compositeDisposable);
    }

    public void initView(View view) {

        etAccountHolderName = (EditText) view.findViewById(R.id.fragment_merchant_details_et_account_holder_name);
        etBankName = (EditText) view.findViewById(R.id.fragment_merchant_details_et_bank_name);
        etAccountNumber = (EditText) view.findViewById(R.id.fragment_merchant_details_et_account_number);
        etConfirmAccountNumber = (EditText) view.findViewById(R.id.fragment_merchant_details_et_confirm_account_number);
        etIFSC = (EditText) view.findViewById(R.id.fragment_merchant_details_et_ifsc_code);

        btSave = (Button) view.findViewById(R.id.activity_merchant_details_bt_save);
        btCancel = (Button) view.findViewById(R.id.activity_merchant_details_bt_cancel);

        btSave.setOnClickListener(this);
        btCancel.setOnClickListener(this);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_with_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {

            setHasOptionsMenu(false);

            btSave.setVisibility(View.VISIBLE);
            btCancel.setVisibility(View.VISIBLE);

            etAccountHolderName.setEnabled(true);
            etBankName.setEnabled(true);
            etAccountNumber.setEnabled(true);
            etConfirmAccountNumber.setEnabled(true);
            etIFSC.setEnabled(true);
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        hideKeyboard();

        if (v.getId() == btSave.getId()) {

            SetMerchantDetailRequest merchantDetailRequest = new SetMerchantDetailRequest();

            merchantDetailRequest.setAccountHolderName(etAccountHolderName.getText().toString());
            merchantDetailRequest.setBankName(etBankName.getText().toString());
            merchantDetailRequest.setAccountNumber(etAccountNumber.getText().toString());
            merchantDetailRequest.setIfscCode(etIFSC.getText().toString());

            merchantDetailMvpPresenter.setMerchantDetails(merchantDetailRequest, etConfirmAccountNumber.getText().toString());

        } else if (v.getId() == btCancel.getId()) {

            setHasOptionsMenu(true);

            setMerchantDetail(merchantDetailResponse);

            btSave.setVisibility(View.GONE);
            btCancel.setVisibility(View.GONE);

            etAccountHolderName.setEnabled(false);
            etBankName.setEnabled(false);
            etAccountNumber.setEnabled(false);
            etConfirmAccountNumber.setEnabled(false);
            etIFSC.setEnabled(false);
        }
    }

    @Override
    protected void setUp(View view) {
    }

    @Override
    public void onDestroyView() {
        merchantDetailMvpPresenter.dispose();
        super.onDestroyView();
    }

    @Override
    public void setMerchantDetail(GetMerchantDetailResponse merchantDetailResponse) {

        this.merchantDetailResponse = merchantDetailResponse;

        etAccountHolderName.setText(merchantDetailResponse.getResponse().getRestaurant().getAccountHolderName());
        etBankName.setText(merchantDetailResponse.getResponse().getRestaurant().getBankName());
        etAccountNumber.setText(merchantDetailResponse.getResponse().getRestaurant().getAccountNumber());
        etConfirmAccountNumber.setText(merchantDetailResponse.getResponse().getRestaurant().getAccountNumber());
        etIFSC.setText(merchantDetailResponse.getResponse().getRestaurant().getIfscCode());
    }

    @Override
    public void onMerchantDetailUpdationSuccess() {

        if (this.merchantDetailResponse == null) {
            merchantDetailResponse = new GetMerchantDetailResponse();

            merchantDetailResponse.setResponse(new Response());
            merchantDetailResponse.getResponse().setRestaurant(new Restaurant());
        }

        merchantDetailResponse.getResponse().getRestaurant().setAccountHolderName(etAccountHolderName.getText().toString());
        merchantDetailResponse.getResponse().getRestaurant().setBankName(etBankName.getText().toString());
        merchantDetailResponse.getResponse().getRestaurant().setAccountNumber(etAccountNumber.getText().toString());
        merchantDetailResponse.getResponse().getRestaurant().setIfscCode(etIFSC.getText().toString());


        setHasOptionsMenu(true);

        setMerchantDetail(merchantDetailResponse);

        btSave.setVisibility(View.GONE);
        btCancel.setVisibility(View.GONE);

        etAccountHolderName.setEnabled(false);
        etBankName.setEnabled(false);
        etAccountNumber.setEnabled(false);
        etConfirmAccountNumber.setEnabled(false);
        etIFSC.setEnabled(false);
    }

    @Override
    public void onMerchantDetailUpdationFail() {

        setMerchantDetail(merchantDetailResponse);
    }
}