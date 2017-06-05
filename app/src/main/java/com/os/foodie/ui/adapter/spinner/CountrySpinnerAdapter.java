package com.os.foodie.ui.adapter.spinner;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.locationinfo.country.Country;

import java.util.List;

public class CountrySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context context;
    private List<Country> arrayList;

    public CountrySpinnerAdapter(Context context, List<Country> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.spinner_text, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.spinner_text_tv_text);
        textView.setText(arrayList.get(position).getName());
        textView.setEllipsize(TextUtils.TruncateAt.END);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(android.R.layout.simple_spinner_item, parent, false);

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(arrayList.get(position).getName());
        textView.setSingleLine(true);

//        TextView txt = new TextView(context);
//        txt.setGravity(Gravity.CENTER);
//        txt.setPadding(40, 40, 40, 40);
//        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
//        txt.setText(arrayList.get(position));
//        txt.setTextColor(Color.parseColor("#000000"));
        return convertView;
    }
}