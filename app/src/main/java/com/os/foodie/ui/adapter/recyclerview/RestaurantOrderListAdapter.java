package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.model.TempModelRestaurantOrder;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpPresenter;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpView;

import java.util.ArrayList;

public class RestaurantOrderListAdapter extends RecyclerView.Adapter<RestaurantOrderListAdapter.RestaurantOrderListViewHolder> {

    private Context context;
    private ArrayList<TempModelRestaurantOrder> tempModelRestaurantOrders;

    public RestaurantOrderListAdapter(Context context, ArrayList<TempModelRestaurantOrder> tempModelRestaurantOrders) {
        this.context = context;
        this.tempModelRestaurantOrders = tempModelRestaurantOrders;
    }

    class RestaurantOrderListViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderId, tvItemName, tvOrderType, tvDeliveryTime, tvDiscount, tvPrice;
        public ImageView ivAccept, ivReject;

        public RestaurantOrderListViewHolder(View itemView) {
            super(itemView);

            tvOrderId = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_order_id);
            tvItemName = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_item_name);
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
    public void onBindViewHolder(RestaurantOrderListViewHolder holder, int position) {

        TempModelRestaurantOrder tempModelRestaurantOrder = tempModelRestaurantOrders.get(position);

        holder.tvOrderId.setText(tempModelRestaurantOrder.getOrderId());
        holder.tvItemName.setText(tempModelRestaurantOrder.getItemName());
        holder.tvDeliveryTime.setText(tempModelRestaurantOrder.getDeliveryTime());
        holder.tvOrderType.setText(tempModelRestaurantOrder.getOrderType());
        holder.tvDiscount.setText(tempModelRestaurantOrder.getDiscount());
        holder.tvPrice.setText(tempModelRestaurantOrder.getPrice());
    }

    @Override
    public int getItemCount() {
        return tempModelRestaurantOrders.size();
    }
}
