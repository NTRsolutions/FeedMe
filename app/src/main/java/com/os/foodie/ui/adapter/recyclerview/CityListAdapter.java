package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.cuisinetype.list.CuisineType;
import com.os.foodie.data.network.model.locationinfo.city.City;
import com.os.foodie.ui.dialogfragment.city.CityOnClickListener;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListViewHolder> {

    private Context context;
    private ArrayList<City> cities;
    private CityOnClickListener cityOnClickListener;

    public CityListAdapter(Context context, ArrayList<City> cities, CityOnClickListener cityOnClickListener) {
        this.context = context;
        this.cities = cities;
        this.cityOnClickListener = cityOnClickListener;
    }

    class CityListViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCity;

        public CityListViewHolder(View itemView) {
            super(itemView);

            tvCity = (TextView) itemView.findViewById(R.id.item_city_list_tv_city);
        }
    }

    @Override
    public CityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_city_list, parent, false);
        return new CityListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityListViewHolder holder, final int position) {

        City city = cities.get(position);

        holder.tvCity.setText(city.getName());

        holder.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cityOnClickListener.onClick(cities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}