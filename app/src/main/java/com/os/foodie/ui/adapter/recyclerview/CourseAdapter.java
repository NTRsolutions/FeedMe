package com.os.foodie.ui.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.data.network.model.details.Dish;
import com.os.foodie.ui.details.restaurant.RestaurantDetailsMvpPresenter;
import com.os.foodie.ui.details.restaurant.RestaurantDetailsPresenter;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private String restaurantId;

    private int totalQuantity;
    private float totalAmount;

    private ArrayList<Object> objectArrayList;
    private RestaurantDetailsPresenter restaurantDetailsPresenter;

    private final int TITLE = 1;
    private final int CONTENT = 2;

    ForClick forClick;

    String currency = "";

    public CourseAdapter(Activity activity, ArrayList<Object> objectArrayList, String restaurantId, RestaurantDetailsMvpPresenter restaurantDetailsMvpPresenter, String currency) {
        this.activity = activity;
        this.objectArrayList = objectArrayList;

        totalQuantity = 0;
        totalAmount = 0;

        this.restaurantId = restaurantId;
        this.currency = currency;
        this.restaurantDetailsPresenter = (RestaurantDetailsPresenter) restaurantDetailsMvpPresenter;
    }

    public class CourseTitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public CourseTitleViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.recyclerview_course_title_tv_title);
        }
    }

    public class CourseContentViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlCourseQuantity;
        LinearLayout llMain;
        CircleImageView civDishImage;
        TextView tvCourseQuantity, tvVeg, tvCourseName, tvCourseDescription, tvPrice;

        public CourseContentViewHolder(View itemView) {
            super(itemView);


            llMain = (LinearLayout) itemView.findViewById(R.id.recyclerview_course_content_ll_main);
            rlCourseQuantity = (RelativeLayout) itemView.findViewById(R.id.recyclerview_course_content_rl_course_quantity);

            civDishImage = (CircleImageView) itemView.findViewById(R.id.recyclerview_course_content_civ_dish_image);

            tvCourseQuantity = (TextView) itemView.findViewById(R.id.recyclerview_course_content_tv_course_quantity);
            tvVeg = (TextView) itemView.findViewById(R.id.recyclerview_course_content_tv_veg);
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

    public void setupContent(final CourseContentViewHolder holder, final int position) {

        final Dish dish = (Dish) objectArrayList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("itemView", "onClick");
                forClick.onClick(v, position);
            }
        });

        Glide.with(activity)
                .load(dish.getDishImage())
                .error(R.mipmap.img_placeholder)
                .into(holder.civDishImage);

        if (dish.getDishImage() != null && !dish.getDishImage().isEmpty()) {

            holder.civDishImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ImageViewer.Builder(activity, new String[]{dish.getDishImage()})
                            .setStartPosition(position)
                            .show();
                }
            });
        }

        if (dish.getQty() != null && !dish.getQty().isEmpty()) {

            int quantity = Integer.parseInt(dish.getQty());

            if (quantity > 0) {
                holder.rlCourseQuantity.setVisibility(View.VISIBLE);
                holder.tvCourseQuantity.setText(quantity + "");
            } else {
                holder.rlCourseQuantity.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.rlCourseQuantity.setVisibility(View.INVISIBLE);
        }

        if (dish.getVegNonveg().equals(AppConstants.VEG)) {

            holder.tvVeg.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_image_view_green));
        } else {
            holder.tvVeg.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_image_view_orange));
        }

//        holder.tvCourseName.setText(dish.getName());


        if (AppController.get(activity).getAppDataManager().getLanguage().equalsIgnoreCase(AppConstants.LANG_AR)) {
            holder.tvCourseName.setText(dish.getNameArabic());
        } else {
            holder.tvCourseName.setText(dish.getName());
        }

        holder.tvCourseDescription.setText(dish.getDescription());

        String price = dish.getPrice();

        if (price.contains(".00")) {
            price = price.replace(".00", "");
        }

        holder.tvPrice.setText(CommonUtils.dataDecode(currency) + "" + price);
//        try {
//            holder.tvPrice.setText(URLDecoder.decode(currency, "UTF-8") + "" + price);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        holder.llMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Dish dish = (Dish) objectArrayList.get(position);
//
//                if (dish.getQty() != null && !dish.getQty().isEmpty()) {
//
//                    int quantity = Integer.parseInt(dish.getQty());
//
//                    if (quantity >= 0) {
////                        holder.rlCourseQuantity.setVisibility(View.VISIBLE);
////                        holder.tvCourseQuantity.setText(++quantity + "");
//
//                        Log.d("getMessage", ">>quantity>=0");
//                        AddToCartRequest addToCartRequest = new AddToCartRequest();
//
//                        addToCartRequest.setDishId(dish.getDishId());
//                        addToCartRequest.setUserId(AppController.get(activity).getAppDataManager().getCurrentUserId());
//                        addToCartRequest.setRestaurantId(restaurantId);
//                        addToCartRequest.setPrice(dish.getPrice());
//                        addToCartRequest.setQty((Integer.parseInt(dish.getQty()) + 1) + "");
//
//                        restaurantDetailsPresenter.addItemToCart(addToCartRequest);
//                    }
//                } else {
////                    holder.rlCourseQuantity.setVisibility(View.VISIBLE);
////                    holder.tvCourseQuantity.setText("1");
//
//                    Log.d("getMessage", ">>quantity==null");
//
//                    AddToCartRequest addToCartRequest = new AddToCartRequest();
//
//                    addToCartRequest.setDishId(dish.getDishId());
//                    addToCartRequest.setUserId(AppController.get(activity).getAppDataManager().getCurrentUserId());
//                    addToCartRequest.setRestaurantId(restaurantId);
//                    addToCartRequest.setPrice(dish.getPrice());
//                    addToCartRequest.setQty("1");
//
//                    restaurantDetailsPresenter.addItemToCart(addToCartRequest);
//                }
//            }
//        });
    }

    public void calcBasketDetails() {

        totalQuantity = 0;
        totalAmount = 0;

        for (int i = 0; i < objectArrayList.size(); i++) {

            if (objectArrayList.get(i) instanceof Dish) {

                Dish dish = ((Dish) objectArrayList.get(i));

                if (dish.getQty() != null && !dish.getQty().isEmpty() && !dish.getQty().equals("0")) {

                    totalQuantity += Integer.parseInt(dish.getQty());
                    Log.d("getQty", ">>" + dish.getQty());

                    for (int j = 0; j < Integer.parseInt(dish.getQty()); j++) {
                        totalAmount += Float.parseFloat(dish.getPrice());
                        Log.d("getPrice", ">>" + dish.getPrice());
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setForClick(ForClick forClick) {
        this.forClick = forClick;
    }

    public interface ForClick {
        void onClick(View view, int position);
    }
}