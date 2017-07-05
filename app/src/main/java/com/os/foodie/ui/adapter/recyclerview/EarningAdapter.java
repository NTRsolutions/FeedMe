package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.earning.Earning;

import java.util.ArrayList;

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.EarningViewHolder> {

    private Context context;
    public ArrayList<Earning> earnings = new ArrayList<>();

    public EarningAdapter(Context context, ArrayList<Earning> earnings) {
        this.context = context;
        this.earnings = earnings;
    }

    class EarningViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderId, tvDate, tvPaymentMethod, tvAmount;

        public EarningViewHolder(View itemView) {
            super(itemView);

            tvOrderId = (TextView) itemView.findViewById(R.id.recyclerview_earning_tv_order_id);
            tvDate = (TextView) itemView.findViewById(R.id.recyclerview_earning_tv_date);
            tvPaymentMethod = (TextView) itemView.findViewById(R.id.recyclerview_earning_tv_payment_method);
            tvAmount = (TextView) itemView.findViewById(R.id.recyclerview_earning_tv_amount);
        }
    }

    @Override
    public EarningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_earning, parent, false);
        return new EarningAdapter.EarningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EarningViewHolder holder, int position) {

        Earning earning = earnings.get(position);

        holder.tvOrderId.setText(earning.getOrderId());
        holder.tvPaymentMethod.setText(earning.getPaymentType());
        holder.tvDate.setText(earning.getPaymentDate());
        holder.tvAmount.setText("$" + earning.getAmount());
    }

    @Override
    public int getItemCount() {
        return earnings.size();
    }
}