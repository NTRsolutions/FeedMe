package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.payment.getall.Card;

import java.util.ArrayList;
import java.util.List;

public class SelectPaymentAdapter extends RecyclerView.Adapter<SelectPaymentAdapter.PaymentViewHolder> {

    private Context context;
    private List<Card> cardArrayList = new ArrayList<>();

    private int selectedPosition;
    private RadioButton radioButton;

    public SelectPaymentAdapter(Context context, List<Card> cardArrayList) {
        this.context = context;
        this.cardArrayList = cardArrayList;
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCardNumber;
        public RadioButton rbSelector;

        public PaymentViewHolder(View view) {
            super(view);
            this.tvCardNumber = (TextView) view.findViewById(R.id.recyclerview_select_payment_tv_card_number);
            this.rbSelector = (RadioButton) view.findViewById(R.id.recyclerview_select_payment_rb_select);
        }
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_select_payment, parent, false);
        return new PaymentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {

        Card card = cardArrayList.get(position);

        if (card.isChecked()) {
            holder.rbSelector.setChecked(true);
        } else {
            holder.rbSelector.setChecked(false);
        }

        holder.tvCardNumber.setText(card.getCreditCardNumber());
    }

    @Override
    public int getItemCount() {
        return cardArrayList.size();
    }
}