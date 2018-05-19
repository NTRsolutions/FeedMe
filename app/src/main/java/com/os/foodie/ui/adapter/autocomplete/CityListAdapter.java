package com.os.foodie.ui.adapter.autocomplete;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.os.foodie.data.network.model.locationinfo.city.City;

import java.util.ArrayList;

public class CityListAdapter extends ArrayAdapter<City> {

    private Activity activity;
    private ArrayList<City> cities;
    private ArrayList<City> tempCities;
    private ArrayList<City> suggestions;

    public CityListAdapter(@NonNull Activity activity, ArrayList<City> cities) {
        super(activity, android.R.layout.select_dialog_item, cities);
        this.activity = activity;
        this.cities = cities;
        this.tempCities = new ArrayList<City>(cities);
        this.suggestions = new ArrayList<City>(cities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(android.R.layout.select_dialog_item, parent, false);
        }

        TextView textViewItem = (TextView) convertView.findViewById(android.R.id.text1);
        textViewItem.setText(cities.get(position).getName());

        return convertView;
    }

    @Override
    public int getCount() {

        if (cities != null) {
            return cities.size();
        } else {
            return 0;
        }
    }

    @Nullable
    @Override
    public City getItem(int position) {
        return cities.get(position);
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            City city = (City) resultValue;
            return city.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if (constraint != null) {

                suggestions.clear();

                for (City city : tempCities) {

                    if (city.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {

                        suggestions.add(city);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;

            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayList<City> c = (ArrayList<City>) results.values;

            if (results != null && results.count > 0) {

                clear();

                for (City cust : c) {

                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}