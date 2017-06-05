package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.coursetype.list.Course;

import java.util.ArrayList;

public class CourseTypeAdapter extends RecyclerView.Adapter<CourseTypeAdapter.CourseTypeViewHolder> {

    private Context context;
    public ArrayList<Course> courses;

    public CourseTypeAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    class CourseTypeViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCourseType;

        public CourseTypeViewHolder(View itemView) {
            super(itemView);

            tvCourseType = (TextView) itemView.findViewById(R.id.recyclerview_course_type_ctv_course_type);
        }
    }

    @Override
    public CourseTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_course_type, parent, false);
        return new CourseTypeAdapter.CourseTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseTypeViewHolder holder, int position) {

        Course course = courses.get(position);

        holder.tvCourseType.setText(course.getName());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}