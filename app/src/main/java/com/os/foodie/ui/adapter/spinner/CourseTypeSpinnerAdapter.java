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
import com.os.foodie.data.network.model.coursetype.list.Course;

import java.util.List;

public class CourseTypeSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context context;
    private List<Course> courseList;

    public CourseTypeSpinnerAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.spinner_text, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.spinner_text_tv_text);
        textView.setText(courseList.get(position).getName());
        textView.setEllipsize(TextUtils.TruncateAt.END);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = layoutInflater.inflate(R.layout.spinner_text, parent, false);
        convertView = layoutInflater.inflate(android.R.layout.simple_spinner_item, parent, false);

//        TextView textView = (TextView) convertView.findViewById(R.id.spinner_text_tv_text);
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(courseList.get(position).getName());
        textView.setSingleLine(true);

        return convertView;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Course getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}