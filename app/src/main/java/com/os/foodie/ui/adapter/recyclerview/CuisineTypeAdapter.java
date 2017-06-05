package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;

import java.util.ArrayList;

public class CuisineTypeAdapter extends RecyclerView.Adapter<CuisineTypeAdapter.CuisineTypeViewHolder> {

    private Context context;
    public ArrayList<CuisineType> cuisineTypes;

    public CuisineTypeAdapter(Context context, ArrayList<CuisineType> cuisineTypes) {
        this.context = context;
        this.cuisineTypes = cuisineTypes;
    }

    class CuisineTypeViewHolder extends RecyclerView.ViewHolder {

        public CheckedTextView ctvCuisineType;

        public CuisineTypeViewHolder(View itemView) {
            super(itemView);

            ctvCuisineType = (CheckedTextView) itemView.findViewById(R.id.recyclerview_cuisine_type_ctv_cuisine_type);
        }
    }

    @Override
    public CuisineTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cuisine_type_working_days, parent, false);
        return new CuisineTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CuisineTypeViewHolder holder, final int position) {

        CuisineType cuisineType = cuisineTypes.get(position);

        holder.ctvCuisineType.setText(cuisineType.getName());

        if (cuisineType.isChecked()) {

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
                    cuisineTypes.get(position).setChecked(false);
                } else {
                    checkedTextView.setChecked(true);
                    cuisineTypes.get(position).setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cuisineTypes.size();
    }
}