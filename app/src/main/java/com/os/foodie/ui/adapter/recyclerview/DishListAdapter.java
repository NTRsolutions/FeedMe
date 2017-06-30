package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.discount.dishlist.DishDatum;

import java.util.ArrayList;

public class DishListAdapter extends RecyclerView.Adapter<DishListAdapter.WorkingDaysViewHolder> {

    private Context context;
    public ArrayList<DishDatum> dishData=new ArrayList<>();

    public DishListAdapter(Context context, ArrayList<DishDatum> workingDays) {
        this.context = context;
        this.dishData = workingDays;
    }

    class WorkingDaysViewHolder extends RecyclerView.ViewHolder {

        public CheckedTextView ctvCuisineType;

        public WorkingDaysViewHolder(View itemView) {
            super(itemView);

            ctvCuisineType = (CheckedTextView) itemView.findViewById(R.id.recyclerview_cuisine_type_ctv_cuisine_type);
        }
    }

    @Override
    public WorkingDaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cuisine_type_working_days, parent, false);
        return new WorkingDaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkingDaysViewHolder holder, final int position) {

        final DishDatum dishDatum = dishData.get(position);

        holder.ctvCuisineType.setText(dishDatum.getName());

        if (dishDatum.isChecked()) {

            holder.ctvCuisineType.setChecked(true);

        } else {

            holder.ctvCuisineType.setChecked(false);
        }

        holder.ctvCuisineType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckedTextView checkedTextView = ((CheckedTextView) v);

                if (checkedTextView.isChecked()) {
                    checkedTextView.setChecked(false);
                    dishData.get(position).setChecked(false);
                } else {
                    checkedTextView.setChecked(true);
                    dishData.get(position).setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishData.size();
    }
}