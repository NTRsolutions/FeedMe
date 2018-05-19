package com.os.foodie.ui.dialogfragment.orderstatus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.os.foodie.R;
import com.os.foodie.ui.custom.RippleAppCompatButton;

@SuppressLint("ValidFragment")
public class OrderStatusDialogFragment extends DialogFragment implements View.OnClickListener {


    /*OrderStatusDialogFragment dishListDialogFragment = new OrderStatusDialogFragment();
        dishListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
        dishListDialogFragment.show(getSupportFragmentManager(), "OrderStatusDialogFragment");*/

    OrderStatusCallback orderStatusCallback;
    private ImageView dialogFragmentWorkingDaysIvDone;

    private RippleAppCompatButton doneBtn;
    private RadioGroup order_status_radio_group;

    String orderType = "", currentOrderStatus = "";
    private AppCompatRadioButton underPreparationRb;
    private AppCompatRadioButton readyToPickOrDeliverRb;
    private AppCompatRadioButton inTransitRb;
    private AppCompatRadioButton deliveryOrPickedRb;

    public OrderStatusDialogFragment() {
    }

    public OrderStatusDialogFragment(String orderType, String currentOrderStatus) {

        this.orderType = orderType;
        this.currentOrderStatus = currentOrderStatus;
    }


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

        View rootView = inflater.inflate(R.layout.dialog_fragment_order_status, container, false);

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        dialogFragmentWorkingDaysIvDone = (ImageView) rootView.findViewById(R.id.dialog_fragment_working_days_iv_done);
        doneBtn = (RippleAppCompatButton) rootView.findViewById(R.id.done_btn);
        order_status_radio_group = (RadioGroup) rootView.findViewById(R.id.order_status_radio_group);

        doneBtn.setOnClickListener(this);
        dialogFragmentWorkingDaysIvDone.setOnClickListener(this);

        orderStatusCallback = (OrderStatusCallback) getActivity();
        underPreparationRb = (AppCompatRadioButton) rootView.findViewById(R.id.under_preparation_rb);
        readyToPickOrDeliverRb = (AppCompatRadioButton) rootView.findViewById(R.id.ready_to_pick_or_deliver_rb);
        inTransitRb = (AppCompatRadioButton) rootView.findViewById(R.id.in_transit_rb);
        deliveryOrPickedRb = (AppCompatRadioButton) rootView.findViewById(R.id.delivery_or_picked_rb);

        if (orderType.equalsIgnoreCase("Pick Only")) {
            deliveryOrPickedRb.setVisibility(View.GONE);
            String[] pickOrderStatus = getResources().getStringArray(R.array.pick_order_status);
            underPreparationRb.setText(pickOrderStatus[0]);
            readyToPickOrDeliverRb.setText(pickOrderStatus[1]);
            inTransitRb.setText(pickOrderStatus[2]);
            if (currentOrderStatus.equalsIgnoreCase(pickOrderStatus[0])) {
                underPreparationRb.setChecked(true);
                underPreparationRb.setEnabled(false);
            } else if (currentOrderStatus.equalsIgnoreCase(pickOrderStatus[1])) {
                readyToPickOrDeliverRb.setChecked(true);
                underPreparationRb.setEnabled(false);
                readyToPickOrDeliverRb.setEnabled(false);
            }

        } else {
            deliveryOrPickedRb.setVisibility(View.VISIBLE);
            String[] deliveryOrderStatus = getResources().getStringArray(R.array.delivery_order_status);
            underPreparationRb.setText(deliveryOrderStatus[0]);
            readyToPickOrDeliverRb.setText(deliveryOrderStatus[1]);
            inTransitRb.setText(deliveryOrderStatus[2]);
            deliveryOrPickedRb.setText(deliveryOrderStatus[3]);


            if (currentOrderStatus.equalsIgnoreCase(deliveryOrderStatus[0])) {
                underPreparationRb.setChecked(true);
                underPreparationRb.setEnabled(false);
            } else if (currentOrderStatus.equalsIgnoreCase(deliveryOrderStatus[1])) {
                readyToPickOrDeliverRb.setChecked(true);
                underPreparationRb.setEnabled(false);
                readyToPickOrDeliverRb.setEnabled(false);
            } else if (currentOrderStatus.equalsIgnoreCase(deliveryOrderStatus[2])) {
                inTransitRb.setChecked(true);
                underPreparationRb.setEnabled(false);
                readyToPickOrDeliverRb.setEnabled(false);
                inTransitRb.setEnabled(false);
            }

        }

    }

    @Override
    public void onClick(View v) {

        if (doneBtn.getId() == v.getId()) {
            int id = order_status_radio_group.getCheckedRadioButtonId();
            String status = "";

            if (id == R.id.under_preparation_rb) {
                status = underPreparationRb.getText().toString();
            } else if (id == R.id.ready_to_pick_or_deliver_rb) {
                status = readyToPickOrDeliverRb.getText().toString();
            } else if (id == R.id.in_transit_rb) {
                status = inTransitRb.getText().toString();
            } else if (id == R.id.delivery_or_picked_rb) {
                status = deliveryOrPickedRb.getText().toString();
            }

            orderStatusCallback.OrderStatusReturn(status);
            dismiss();

        } else if (v == dialogFragmentWorkingDaysIvDone) {
            dismiss();
        }
    }


}