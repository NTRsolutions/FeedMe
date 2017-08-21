package com.os.foodie.ui.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.AppDataManager;
import com.os.foodie.data.network.AppApiHelpter;
import com.os.foodie.data.network.model.orderlist.show.OrderList;
import com.os.foodie.data.prefs.AppPreferencesHelper;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.ui.order.restaurant.history.RestaurantOrderHistoryFragment;
import com.os.foodie.ui.order.restaurant.history.RestaurantOrderHistoryMvpPresenter;
import com.os.foodie.ui.order.restaurant.history.RestaurantOrderHistoryMvpView;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RestaurantOrderHistoryAdapter extends RecyclerView.Adapter<RestaurantOrderHistoryAdapter.RestaurantOrderListViewHolder> {

    private Context context;
    private String currency = "";
    private ArrayList<OrderList> orderLists;
    private RestaurantOrderHistoryFragment restaurantOrderHistoryFragment;

    private RestaurantOrderHistoryMvpPresenter<RestaurantOrderHistoryMvpView> restaurantOrderhistoryMvpPresenter;
//    private String deliveryTime;

    public RestaurantOrderHistoryAdapter(Context context, RestaurantOrderHistoryFragment restaurantOrderHistoryFragment, ArrayList<OrderList> orderLists, RestaurantOrderHistoryMvpPresenter<RestaurantOrderHistoryMvpView> restaurantOrderhistoryMvpPresenter) {
        this.context = context;
        this.orderLists = orderLists;
        this.restaurantOrderHistoryFragment = restaurantOrderHistoryFragment;
        this.restaurantOrderhistoryMvpPresenter = restaurantOrderhistoryMvpPresenter;
    }

    class RestaurantOrderListViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderId/*, tvItemName*/, tvOrderType, tvDeliveryTime, tvDiscount, tvPrice;
        public ImageView ivAccept, ivReject;

        public RestaurantOrderListViewHolder(View itemView) {
            super(itemView);

            tvOrderId = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_order_id);
//            tvItemName = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_item_name);
            tvDeliveryTime = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_delivery_time);
            tvOrderType = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_order_type);
            tvDiscount = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_discount);
            tvPrice = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_price);

            ivAccept = (ImageView) itemView.findViewById(R.id.recyclerview_restaurant_order_iv_accept_order);
            ivReject = (ImageView) itemView.findViewById(R.id.recyclerview_restaurant_order_iv_reject_order);
        }
    }

    @Override
    public RestaurantOrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restaurant_order, parent, false);
        return new RestaurantOrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantOrderListViewHolder holder, final int position) {

        final OrderList order = orderLists.get(position);

        holder.tvOrderId.setText(order.getOrderId());
//        holder.tvItemName.setText(order.getDishName());
        holder.tvDeliveryTime.setText("Ordered on " + order.getOrderDelieveryDate() + " at " + order.getOrderDelieveryTime().replace(":00", ""));
//        holder.tvDeliveryTime.setText(order.getDeliveryTime()+" min.");
        holder.tvOrderType.setText(order.getOrderType());

//        String totalDiscount = "" + String.format("%.2f", (order.getTotalDiscount() / Float.parseFloat(order.getTotalAmount()) * 100));
//
//        holder.tvDiscount.setText(totalDiscount + "%");

        if (order.getOrderStatus().equalsIgnoreCase(context.getResources().getStringArray(R.array.pick_order_status)[2]) || order.getOrderStatus().equalsIgnoreCase(context.getResources().getStringArray(R.array.delivery_order_status)[3])) {
            holder.tvDiscount.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_round_corner_green));
            holder.tvDiscount.setText(order.getOrderStatus().toLowerCase());
        } else {
            holder.tvDiscount.setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_round_corner_orange));
            holder.tvDiscount.setText(order.getOrderStatus().toLowerCase());
        }

//        holder.tvDiscount.setText(order.getDiscount() + "%");

        Log.d("getCurrency", ">>" + order.getCurrency());

        if (order.getCurrency() != null && !order.getCurrency().isEmpty()) {

            holder.tvPrice.setText(CommonUtils.dataDecode(order.getCurrency()) + " " + order.getTotalAmount());
//
////            try {
////                holder.tvPrice.setText(URLDecoder.decode(order.getCurrency(), "UTF-8") + " " + order.getTotalAmount());
////
////            } catch (UnsupportedEncodingException e) {
////                e.printStackTrace();
////            }
//
        } else {

            holder.tvPrice.setText(CommonUtils.dataDecode(currency) + " " + order.getTotalAmount());
        }

        holder.ivAccept.setVisibility(View.GONE);
        holder.ivReject.setVisibility(View.GONE);
        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = (int) v.getTag();
                Intent i = new Intent(context, OrderHistoryDetailActivity.class);
                i.putExtra("order_id", orderLists.get(pos).getOrderId());
                if (restaurantOrderhistoryMvpPresenter.getCurrentUserType().equals(AppConstants.RESTAURANT))
                    i.putExtra("showUpdateButton", true);
                else
                    i.putExtra("showUpdateButton", false);
                restaurantOrderHistoryFragment.startActivityForResult(i, 10);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
