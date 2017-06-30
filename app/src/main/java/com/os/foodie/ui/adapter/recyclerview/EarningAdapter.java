package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.model.TempEarningModel;

import java.util.ArrayList;

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.EarningViewHolder> {

    private Context context;
    public ArrayList<TempEarningModel> tempEarningModels = new ArrayList<>();

    public EarningAdapter(Context context, ArrayList<TempEarningModel> tempEarningModels) {
        this.context = context;
        this.tempEarningModels = tempEarningModels;
    }

    class EarningViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEarningId, tvTransactionId, tvDate, tvAmount;

        public EarningViewHolder(View itemView) {
            super(itemView);

            tvEarningId = (TextView) itemView.findViewById(R.id.recyclerview_earning_tv_earning_id);
            tvTransactionId = (TextView) itemView.findViewById(R.id.recyclerview_earning_tv_transaction_id);
            tvDate = (TextView) itemView.findViewById(R.id.recyclerview_earning_tv_date);
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

        TempEarningModel tempEarningModel = tempEarningModels.get(position);

        holder.tvEarningId.setText(tempEarningModel.getEarningId());
        holder.tvTransactionId.setText(tempEarningModel.getTransactionId());
        holder.tvDate.setText(tempEarningModel.getDate());
        holder.tvAmount.setText("$" + tempEarningModel.getAmount());
    }

    @Override
    public int getItemCount() {
        return tempEarningModels.size();
    }
}