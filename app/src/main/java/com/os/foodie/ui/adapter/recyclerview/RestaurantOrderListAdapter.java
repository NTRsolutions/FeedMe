package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.orderlist.show.OrderList;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpPresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpView;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class RestaurantOrderListAdapter extends RecyclerView.Adapter<RestaurantOrderListAdapter.RestaurantOrderListViewHolder> {

    private Context context;
    private ArrayList<OrderList> orderLists;
    private RestaurantOrderListMvpPresenter<RestaurantOrderListMvpView> restaurantOrderListMvpPresenter;
//    private String deliveryTime;

    public RestaurantOrderListAdapter(Context context, ArrayList<OrderList> orderLists, RestaurantOrderListMvpPresenter<RestaurantOrderListMvpView> restaurantOrderListMvpPresenter/*, String deliveryTime*/) {
        this.context = context;
        this.orderLists = orderLists;
        this.restaurantOrderListMvpPresenter = restaurantOrderListMvpPresenter;
//        this.deliveryTime = deliveryTime;
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
        holder.tvDeliveryTime.setText(order.getDeliveryTime()+" min.");
        holder.tvOrderType.setText(order.getOrderType());
        holder.tvDiscount.setText(order.getDiscount() + "%");
        holder.tvPrice.setText("$" + order.getTotalAmount());

        holder.ivAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantOrderListMvpPresenter.acceptRejectOrder(orderLists.get(position).getOrderId(), AppConstants.ORDER_UNDER_PREPARATION, position);
            }
        });

        holder.ivReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantOrderListMvpPresenter.acceptRejectOrder(orderLists.get(position).getOrderId(), AppConstants.ORDER_DECLINE, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderLists.size();
    }
}
