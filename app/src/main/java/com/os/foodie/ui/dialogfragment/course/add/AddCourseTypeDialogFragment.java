package com.os.foodie.ui.dialogfragment.course.add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.os.foodie.data.network.model.coursetype.add.AddCourseTypeRequest;
import com.os.foodie.data.network.model.coursetype.add.AddCourseTypeResponse;
import com.os.foodie.feature.callback.AddCourseTypeCallback;
import com.os.foodie.ui.custom.RippleAppCompatButton;
import com.os.foodie.ui.welcome.WelcomeActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.NetworkUtils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddCourseTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText etCourseType;
    private RippleAppCompatButton btSubmit;

    private ProgressDialog progressDialog;
    private AddCourseTypeCallback addCourseTypeCallback;

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

        addCourseTypeCallback = (AddCourseTypeCallback) getActivity();

//        getDialog().setTitle("Add Course Type");

        View rootView = inflater.inflate(R.layout.dialog_fragment_add_course_type, container, false);

        etCourseType = (EditText) rootView.findViewById(R.id.dialog_fragment_add_course_type_et_course_type);
        btSubmit = (RippleAppCompatButton) rootView.findViewById(R.id.dialog_fragment_add_course_type_bt_submit);

        btSubmit.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        if (btSubmit.getId() == v.getId()) {
            addCourseType();
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

    public void addCourseType() {

        if (NetworkUtils.isNetworkConnected(getContext())) {

            if (etCourseType.getText().toString() == null || etCourseType.getText().toString().isEmpty()) {

                Toast.makeText(getActivity(), getResources().getString(R.string.empty_course_type), Toast.LENGTH_SHORT).show();
                return;
            }


            showLoading();
//            progressDialog.show();

            AppController.get((AppCompatActivity) getActivity()).getCompositeDisposable().add(AppController.get((AppCompatActivity) getActivity()).getAppDataManager()
                    .addCourseType(new AddCourseTypeRequest(etCourseType.getText().toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddCourseTypeResponse>() {
                        @Override
                        public void accept(AddCourseTypeResponse addCourseTypeResponse) throws Exception {

                            Log.d("Message", ">>" + addCourseTypeResponse.getResponse().getMessage());
                            hideLoading();

                            if (addCourseTypeResponse.getResponse().getIsDeleted() != null && addCourseTypeResponse.getResponse().getIsDeleted().equalsIgnoreCase("1")) {

//                                Locale locale = new Locale(AppConstants.LANG_EN);
//                                Locale.setDefault(locale);
//
//                                Configuration config = new Configuration();
//                                config.locale = locale;
//
//                                getResources().updateConfiguration(config, getResources().getDisplayMetrics());

                                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

//                                AppController.get(getActivity()).getAppDataManager().setLanguage(AppConstants.LANG_EN);


                                AppController.get(getActivity()).getAppDataManager().setRestaurantLogoURL(null);
                                AppController.get(getActivity()).getAppDataManager().setCustomerRestaurantId(null);
                                AppController.get(getActivity()).getAppDataManager().setCurrentUserName(null);
                                AppController.get(getActivity()).getAppDataManager().setCurrentUserLoggedIn(false);
                                AppController.get(getActivity()).getAppDataManager().setFacebook(false);
                                AppController.get(getActivity()).getAppDataManager().setCurrentUserId(null);
                                AppController.get(getActivity()).getAppDataManager().setCurrentUserType(null);
                                AppController.get(getActivity()).getAppDataManager().setCurrentUserInfoInitialized(false);
//                                AppController.get(getActivity()).getAppDataManager().setDeviceId("");

                                AppController.get(getActivity()).getAppDataManager().setNotificationStatus("0");

                                Toast.makeText(getActivity(), addCourseTypeResponse.getResponse().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            if (addCourseTypeResponse.getResponse().getStatus() == 0) {

//                                if (getActivity() instanceof RestaurantMenuAddUpdateDishActivity) {
                                Toast.makeText(getActivity(), addCourseTypeResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
//                                    ((RestaurantMenuAddUpdateDishActivity) getActivity()).onError(addCuisineTypeResponse.getResponse().getMessage());
//                                }
                            } else {
                                dismiss();
                                addCourseTypeCallback.addNewCourseType(addCourseTypeResponse.getResponse().getCourseId(), etCourseType.getText().toString());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            hideLoading();

//                            if (getActivity() instanceof RestaurantMenuAddUpdateDishActivity) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.api_default_error), Toast.LENGTH_SHORT).show();
//                                ((RestaurantMenuAddUpdateDishActivity) getActivity()).onError(R.string.api_default_error);
//                            }
                        }
                    }));
        } else {

//            if (getActivity() instanceof RestaurantMenuAddUpdateDishActivity) {
            Toast.makeText(getActivity(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
//                ((RestaurantMenuAddUpdateDishActivity) getActivity()).onError(R.string.connection_error);
//            }
        }
    }
}