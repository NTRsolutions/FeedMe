package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.ui.details.restaurant.RestaurantDetailsActivity;
import com.os.foodie.ui.review.ReviewActivity;
import com.os.foodie.ui.search.RestaurantSearchActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.ScreenUtils;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder> {

    private Context context;
    public ArrayList<RestaurantList> restaurantLists;

    public RestaurantListAdapter(Context context, ArrayList<RestaurantList> restaurantLists) {
        this.context = context;
        this.restaurantLists = restaurantLists;
    }

    class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivRestaurantImage;
        public TextView tvRestaurantName, tvReviews, tvMinutes, tvTime;
        public FlowLayout flCuisines;
        public RatingBar rbRating;

        public RestaurantListViewHolder(View itemView) {
            super(itemView);

            ivRestaurantImage = (ImageView) itemView.findViewById(R.id.recyclerview_restaurant_iv_restaurant_image);
            tvRestaurantName = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_tv_restaurant_name);
            tvReviews = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_tv_reviews);
            tvMinutes = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_tv_minutes);
            tvTime = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_tv_time);
            flCuisines = (FlowLayout) itemView.findViewById(R.id.recyclerview_restaurant_fl_cuisine_types);
            rbRating = (RatingBar) itemView.findViewById(R.id.recyclerview_restaurant_rb_rating);
        }
    }

    @Override
    public RestaurantListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restaurant, parent, false);
        return new RestaurantListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantListViewHolder holder, final int position) {

        RestaurantList restaurantList = restaurantLists.get(position);

        if (restaurantList.getImages().size() > 0) {

            Glide.with(context)
                    .load(restaurantList.getImages().get(0).getUrl())
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.img_placeholder))
                    .error(ContextCompat.getDrawable(context, R.mipmap.img_placeholder))
                    .into(holder.ivRestaurantImage);
        }

        holder.ivRestaurantImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(context) / 4));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                intent.putExtra(AppConstants.RESTAURANT_ID, restaurantLists.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.tvRestaurantName.setText(restaurantList.getRestaurantName());
        holder.tvMinutes.setText(restaurantList.getDeliveryTime());

        if (restaurantList.getAvgRating() != null && !restaurantList.getAvgRating().isEmpty()) {
            holder.rbRating.setRating(Float.parseFloat(restaurantList.getAvgRating()));
        } else {
            holder.rbRating.setRating(0f);
        }

        holder.tvReviews.setText("Reviews(" + restaurantList.getReviewCount() + ")");

        holder.tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra(AppConstants.RESTAURANT_ID, restaurantLists.get(position).getId());
                context.startActivity(intent);
            }
        });

//        holder.rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//
//                ratingBar.setRating(rating);
//            }
//        });

        holder.flCuisines.removeAllViews();

        String cuisines[] = restaurantList.getCuisineType().split(",");

        for (final String cuisine : cuisines) {

            TextView tvCuisine = new TextView(context);

            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

            int marginTop = (int) context.getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_top);

            layoutParams.setMarginStart((int) context.getResources().getDimension(R.dimen.restaurant_tv_cuisine_margin_start));
            layoutParams.setMargins(0, marginTop, 0, 0);

            tvCuisine.setLayoutParams(layoutParams);
            tvCuisine.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_round_corner_white_outline_black));

            float padding = context.getResources().getDimension(R.dimen.restaurant_tv_cuisine_padding);

            tvCuisine.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
            tvCuisine.setTextColor(ContextCompat.getColor(context, R.color.dark_grey));
            tvCuisine.setTypeface(Typeface.SERIF);
            tvCuisine.setText(cuisine);

            tvCuisine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RestaurantSearchActivity.class);
                    intent.putExtra(AppConstants.CUISINE_SEARCH, cuisine);
                    context.startActivity(intent);
                }
            });

            holder.flCuisines.addView(tvCuisine);
        }
    }

    @Override
    public int getItemCount() {
        return restaurantLists.size();
    }
}