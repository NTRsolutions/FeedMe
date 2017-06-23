package com.os.foodie.ui.adapter.recyclerview;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.deliveryaddress.getall.Address;
import com.os.foodie.ui.deliveryaddress.addedit.AddEditDeliveryAddressActivity;
import com.os.foodie.ui.deliveryaddress.select.SelectDeliveryAddressMvpPresenter;
import com.os.foodie.ui.deliveryaddress.select.SelectDeliveryAddressMvpView;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressMvpPresenter;
import com.os.foodie.ui.deliveryaddress.show.DeliveryAddressMvpView;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.DialogUtils;

import java.util.ArrayList;

public class SelectDeliveryAddressAdapter extends RecyclerView.Adapter<SelectDeliveryAddressAdapter.SelectDeliveryAddressViewHolder> {

    private Activity activity;
    private ArrayList<Address> addresses;
    private SelectDeliveryAddressMvpPresenter<SelectDeliveryAddressMvpView> selectDeliveryAddressMvpPresenter;

    public SelectDeliveryAddressAdapter(Activity activity, ArrayList<Address> addresses, SelectDeliveryAddressMvpPresenter<SelectDeliveryAddressMvpView> selectDeliveryAddressMvpPresenter) {
        this.activity = activity;
        this.addresses = addresses;
        this.selectDeliveryAddressMvpPresenter = selectDeliveryAddressMvpPresenter;
    }

    class SelectDeliveryAddressViewHolder extends RecyclerView.ViewHolder {

        public RadioButton rbSelect;
        public TextView tvName, tvStreetNumber, tvColony, tvCityStatePin, tvCountry, tvMobileNumber;
        public Button btEdit, btDelete;

        public SelectDeliveryAddressViewHolder(View itemView) {
            super(itemView);

            rbSelect = (RadioButton) itemView.findViewById(R.id.recyclerview_select_delivery_address_rb_select);
            tvName = (TextView) itemView.findViewById(R.id.recyclerview_select_delivery_address_tv_name);
            tvStreetNumber = (TextView) itemView.findViewById(R.id.recyclerview_select_delivery_address_tv_street_number);
            tvColony = (TextView) itemView.findViewById(R.id.recyclerview_select_delivery_address_tv_colony);
            tvCityStatePin = (TextView) itemView.findViewById(R.id.recyclerview_select_delivery_address_tv_city_state_pin);
            tvCountry = (TextView) itemView.findViewById(R.id.recyclerview_select_delivery_address_tv_country);
            tvMobileNumber = (TextView) itemView.findViewById(R.id.recyclerview_select_delivery_address_tv_mobile_number);

            btEdit = (Button) itemView.findViewById(R.id.recyclerview_select_delivery_address_bt_edit);
            btDelete = (Button) itemView.findViewById(R.id.recyclerview_select_delivery_address_bt_delete);
        }
    }

    @Override
    public SelectDeliveryAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_select_delivery_address, parent, false);
        return new SelectDeliveryAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectDeliveryAddressViewHolder holder, final int position) {

        Address address = addresses.get(position);

        if (address.isChecked()) {
            holder.rbSelect.setChecked(true);
        } else {
            holder.rbSelect.setChecked(false);
        }

        holder.tvName.setText(address.getFullName());
        holder.tvStreetNumber.setText(address.getFlatNumber());
        holder.tvColony.setText(address.getColony());

        if (address.getLandmark() != null && !address.getLandmark().isEmpty()) {
            holder.tvColony.setText(address.getLandmark() + "\n" + holder.tvColony.getText());
        }

        holder.tvCityStatePin.setText(address.getCity() + " " + address.getState() + " " + address.getPincode());

        holder.tvCountry.setText(address.getCountry());
        holder.tvMobileNumber.setText("Mobile Number: " + address.getMobileNumber());

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        selectDeliveryAddressMvpPresenter.deleteAddress(addresses.get(position).getId(), position);
                    }
                };

                DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                DialogUtils.showAlert(activity,
                        R.string.alert_dialog_title_delete_address, R.string.alert_dialog_text_delete_address,
                        activity.getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
                        activity.getResources().getString(R.string.alert_dialog_bt_no), negativeButton);
            }
        });

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, AddEditDeliveryAddressActivity.class);
                intent.putExtra(AppConstants.POSITION, position);
                intent.putExtra(AppConstants.DELIVERY_ADDRESS, addresses.get(position));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }
}