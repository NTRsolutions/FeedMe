package com.os.foodie.ui.dialogfragment.cuisine.add;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.cuisinetype.add.AddCuisineTypeRequest;
import com.os.foodie.data.network.model.cuisinetype.add.AddCuisineTypeResponse;
import com.os.foodie.feature.callback.AddCuisineTypeCallback;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.setupprofile.restaurant.SetupRestaurantProfileFragment;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddCuisineTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText etCuisineType;
    private RippleAppCompatButton btSubmit;

    private ProgressDialog progressDialog;
    private AddCuisineTypeCallback addCuisineTypeCallback;

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

        addCuisineTypeCallback = SetupRestaurantProfileFragment.addCuisineTypeCallback;//(AddCuisineTypeCallback) getActivity();

//        getDialog().setTitle("Add Cuisine Type");

        View rootView = inflater.inflate(R.layout.dialog_fragment_add_cuisine_type, container, false);

        etCuisineType = (EditText) rootView.findViewById(R.id.dialog_fragment_add_cuisine_type_et_cuisine_type);
        btSubmit = (RippleAppCompatButton) rootView.findViewById(R.id.dialog_fragment_add_cuisine_type_bt_submit);

        btSubmit.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (btSubmit.getId() == v.getId()) {
            addCuisineType();
        }
    }


    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void addCuisineType() {

        if (NetworkUtils.isNetworkConnected(getContext())) {

            if (etCuisineType.getText().toString() == null || etCuisineType.getText().toString().isEmpty()) {

                Toast.makeText(getActivity(), getResources().getString(R.string.empty_cuisine_type), Toast.LENGTH_SHORT).show();
                return;
            }

            showLoading();
//            progressDialog.show();

            AppController.get((AppCompatActivity) getActivity()).getCompositeDisposable().add(AppController.get((AppCompatActivity) getActivity()).getAppDataManager()
                    .addCuisineType(new AddCuisineTypeRequest(etCuisineType.getText().toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddCuisineTypeResponse>() {
                        @Override
                        public void accept(AddCuisineTypeResponse addCuisineTypeResponse) throws Exception {

                            Log.d("Message", ">>" + addCuisineTypeResponse.getResponse().getMessage());
                            hideLoading();

                            if (addCuisineTypeResponse.getResponse().getStatus() == 0) {

//                                if (getActivity() instanceof SetupRestaurantProfileFragment) {
                                Toast.makeText(getActivity(), addCuisineTypeResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
//                                    ((SetupRestaurantProfileFragment) getActivity()).onError(addCuisineTypeResponse.getResponse().getMessage());
//                                }
                            } else {
                                addCuisineTypeCallback.addNewCuisineType(addCuisineTypeResponse.getResponse().getCategoryId(), etCuisineType.getText().toString());
                                dismiss();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            hideLoading();

//                            if (getActivity() instanceof SetupRestaurantProfileFragment) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.api_default_error), Toast.LENGTH_SHORT).show();
//                                ((SetupRestaurantProfileFragment) getActivity()).onError(R.string.api_default_error);
//                            }
                        }
                    }));
        } else {

//            if (getActivity() instanceof SetupRestaurantProfileFragment) {
            Toast.makeText(getActivity(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
//                ((SetupRestaurantProfileFragment) getActivity()).onError(R.string.connection_error);
//            }
        }
    }
}