package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.reviews.ReviewList;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    public ArrayList<ReviewList> reviews = new ArrayList<>();

    public ReviewAdapter(Context context, ArrayList<ReviewList> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUserName, tvReview, tvTime;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            tvUserName = (TextView) itemView.findViewById(R.id.recyclerview_review_tv_user_name);
            tvReview = (TextView) itemView.findViewById(R.id.recyclerview_review_tv_review);
            tvTime = (TextView) itemView.findViewById(R.id.recyclerview_review_tv_time);
        }
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {

        ReviewList reviewList = reviews.get(position);

        holder.tvUserName.setText(reviewList.getName());
        holder.tvReview.setText(reviewList.getReview());
        holder.tvTime.setText(reviewList.getTime());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}