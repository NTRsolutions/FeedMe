package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.ui.dialogfragment.city.CityOnClickListener;

import java.util.ArrayList;

public class City2ListAdapter extends RecyclerView.Adapter<City2ListAdapter.City2ListViewHolder> {

    private Context context;
    private ArrayList<City> cities;

    public City2ListAdapter(Context context, ArrayList<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    class City2ListViewHolder extends RecyclerView.ViewHolder {

        public CheckedTextView ctvCity;

        public City2ListViewHolder(View itemView) {
            super(itemView);

            ctvCity = (CheckedTextView) itemView.findViewById(R.id.item_city2_list_tv_city);
        }
    }

    @Override
    public City2ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_city2_list, parent, false);
        return new City2ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(City2ListViewHolder holder, final int position) {

        final City city = cities.get(position);

        holder.ctvCity.setText(city.getName());

        if (city.isChecked()) {

            holder.ctvCity.setChecked(true);

        } else {

            holder.ctvCity.setChecked(false);
        }

        holder.ctvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckedTextView checkedTextView = ((CheckedTextView) v);

                if (checkedTextView.isChecked()) {
                    checkedTextView.setChecked(false);
                    cities.get(position).setChecked(false);
                } else {
                    checkedTextView.setChecked(true);
                    cities.get(position).setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}