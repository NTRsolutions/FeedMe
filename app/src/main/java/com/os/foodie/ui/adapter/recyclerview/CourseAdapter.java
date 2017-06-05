package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.details.Dish;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Object> objectArrayList;

    private final int TITLE = 1;
    private final int CONTENT = 2;

    public CourseAdapter(Context context, ArrayList<Object> objectArrayList) {
        this.context = context;
        this.objectArrayList = objectArrayList;
    }

    public class CourseTitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public CourseTitleViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.recyclerview_course_title_tv_title);
        }
    }

    public class CourseContentViewHolder extends RecyclerView.ViewHolder {

        TextView tvCourseName, tvCourseDescription, tvPrice;

        public CourseContentViewHolder(View itemView) {
            super(itemView);

            tvCourseName = (TextView) itemView.findViewById(R.id.recyclerview_course_content_tv_course_name);
            tvCourseDescription = (TextView) itemView.findViewById(R.id.recyclerview_course_content_tv_course_description);
            tvPrice = (TextView) itemView.findViewById(R.id.recyclerview_course_content_tv_price);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TITLE:
                View v1 = inflater.inflate(R.layout.recyclerview_course_title, parent, false);
                viewHolder = new CourseTitleViewHolder(v1);
                break;
            case CONTENT:
                View v2 = inflater.inflate(R.layout.recyclerview_course_content, parent, false);
                viewHolder = new CourseContentViewHolder(v2);
                break;
//            default:
//                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
//                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
//                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

            case TITLE:
                CourseTitleViewHolder courseTitleViewHolder = (CourseTitleViewHolder) holder;
                setupTitle(courseTitleViewHolder, position);
                break;
            case CONTENT:
                CourseContentViewHolder courseContentViewHolder = (CourseContentViewHolder) holder;
                setupContent(courseContentViewHolder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (objectArrayList.get(position) instanceof Dish) {
            return CONTENT;
        } else if (objectArrayList.get(position) instanceof String) {
            return TITLE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return objectArrayList.size();
    }

    public void setupTitle(CourseTitleViewHolder holder, int position) {
        String title = (String) objectArrayList.get(position);
        holder.tvTitle.setText(title);
    }

    public void setupContent(CourseContentViewHolder holder, int position) {
        Dish dish = (Dish) objectArrayList.get(position);
        holder.tvCourseName.setText(dish.getCourseName());
        holder.tvCourseDescription.setText(dish.getDescription());

        String price = dish.getPrice();

        if (price.contains(".00")) {
            price = price.replace(".00", "");
        }

        holder.tvPrice.setText("$"+price);
    }
}