package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.os.foodie.R;
import com.os.foodie.data.network.model.home.customer.RestaurantList;
import com.os.foodie.ui.details.restaurant.RestaurantDetailsActivity;
import com.os.foodie.ui.review.ReviewActivity;
import com.os.foodie.ui.search.RestaurantSearchActivity;
import com.os.foodie.utils.AppConstants;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;

public class RestaurantSearchAdapter extends RecyclerView.Adapter<RestaurantSearchAdapter.RestaurantSearchViewHolder> {

    private Context context;
    public ArrayList<RestaurantList> restaurantLists;

    public RestaurantSearchAdapter(Context context, ArrayList<RestaurantList> restaurantLists) {
        this.context = context;
        this.restaurantLists = restaurantLists;
    }

    class RestaurantSearchViewHolder extends RecyclerView.ViewHolder {

        public SelectableRoundedImageView ivRestaurantImage;
        public TextView tvRestaurantName, tvReviews, tvMinutes;
        public FlowLayout flCuisines;
        public RatingBar rbRating;

        public RestaurantSearchViewHolder(View itemView) {
            super(itemView);

            ivRestaurantImage = (SelectableRoundedImageView) itemView.findViewById(R.id.recyclerview_customer_search_sriv_restaurant_image);
            tvRestaurantName = (TextView) itemView.findViewById(R.id.recyclerview_customer_search_tv_restaurant_name);
            tvReviews = (TextView) itemView.findViewById(R.id.recyclerview_customer_search_tv_reviews);
            tvMinutes = (TextView) itemView.findViewById(R.id.recyclerview_customer_search_tv_minutes);
            flCuisines = (FlowLayout) itemView.findViewById(R.id.recyclerview_customer_search_fl_cuisine_types);
            rbRating = (RatingBar) itemView.findViewById(R.id.recyclerview_customer_search_rb_rating);
        }
    }

    @Override
    public RestaurantSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_customer_search, parent, false);
        return new RestaurantSearchAdapter.RestaurantSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantSearchViewHolder holder, final int position) {

        RestaurantList restaurantList = restaurantLists.get(position);

        Glide.with(context)
                .load(restaurantList.getLogo())
                .asBitmap()
                .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
                .into(holder.ivRestaurantImage);

        holder.tvRestaurantName.setText(restaurantList.getRestaurantName());
        holder.tvMinutes.setText(restaurantList.getDeliveryTime());

        holder.flCuisines.removeAllViews();

        String cuisines[] = restaurantList.getCuisineType().split(",");

        for (String cuisine : cuisines) {

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

            holder.flCuisines.addView(tvCuisine);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                intent.putExtra(AppConstants.RESTAURANT_ID, restaurantLists.get(position).getId());
                context.startActivity(intent);
            }
        });

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
    }

    @Override
    public int getItemCount() {
        return restaurantLists.size();
    }
}
