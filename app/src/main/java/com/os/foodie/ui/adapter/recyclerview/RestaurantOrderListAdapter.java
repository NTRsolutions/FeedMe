package com.os.foodie.ui.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.orderlist.show.OrderList;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListFragment;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpPresenter;
import com.os.foodie.ui.order.restaurant.list.RestaurantOrderListMvpView;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.DialogUtils;
import com.os.foodie.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Locale;

public class RestaurantOrderListAdapter extends RecyclerView.Adapter<RestaurantOrderListAdapter.RestaurantOrderListViewHolder> {

    private Context context;
    private String currency;
    private ArrayList<OrderList> orderLists;
    private RestaurantOrderListFragment restaurantOrderListFragment;
    private RestaurantOrderListMvpPresenter<RestaurantOrderListMvpView> restaurantOrderListMvpPresenter;
//    private String deliveryTime;

    public RestaurantOrderListAdapter(Context context, RestaurantOrderListFragment restaurantOrderListFragment, ArrayList<OrderList> orderLists, RestaurantOrderListMvpPresenter<RestaurantOrderListMvpView> restaurantOrderListMvpPresenter/*, String deliveryTime*/) {
        this.context = context;
        this.orderLists = orderLists;
        this.restaurantOrderListFragment = restaurantOrderListFragment;
        this.restaurantOrderListMvpPresenter = restaurantOrderListMvpPresenter;
//        this.deliveryTime = deliveryTime;
    }

    class RestaurantOrderListViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderId/*, tvItemName*/, tvName, tvOrderType, tvDeliveryTime, tvDiscount, tvPrice;
        public ImageView ivAccept, ivReject;

        public RestaurantOrderListViewHolder(View itemView) {
            super(itemView);

            tvOrderId = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_order_id);
//            tvItemName = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_item_name);
            tvName = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_order_tv_name);
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

        holder.tvOrderId.setText(context.getString(R.string.order_id) + " " + order.getOrderId());
//        holder.tvOrderId.setText(order.getOrderId());

//        holder.tvItemName.setText(order.getDishName());

        holder.tvName.setText(context.getString(R.string.order_customer_name) + " " + order.getName());

//        holder.tvDeliveryTime.setText(context.getString(R.string.order_history_order_on_tag) + " " + order.getOrderDelieveryDate() + " " + context.getString(R.string.order_history_at_tag) + " " + order.getOrderDelieveryTime().replace(":00", ""));
        holder.tvDeliveryTime.setText(context.getString(R.string.order_history_order_on_tag_2) + " " + order.getOrderDelieveryDate() + " " + context.getString(R.string.order_history_at_tag) + " " + order.getOrderDelieveryTime().replace(":00", ""));

//        holder.tvDeliveryTime.setText("Ordered on: " + order.getOrderDelieveryDate() + " at " + order.getOrderDelieveryTime().replace(":00", ""));
        holder.tvOrderType.setText(order.getOrderType());

        String totalDiscount = "" + String.format(Locale.ENGLISH, "%.2f", (Float.parseFloat(order.getTotalDiscount()) / Float.parseFloat(order.getTotalAmount()) * 100));
        holder.tvDiscount.setText(totalDiscount + "%");
        holder.tvDiscount.setHeight(ScreenUtils.dpToPx(context, 60));
        holder.tvDiscount.setWidth(ScreenUtils.dpToPx(context, 60));
        holder.tvDiscount.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_image_view_orange));

//        holder.tvDiscount.setText(order.getDiscount() + "%");
        holder.tvPrice.setText(currency + " " + order.getTotalAmount());

        holder.ivAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        restaurantOrderListMvpPresenter.acceptRejectOrder(orderLists.get(position).getOrderId(), AppConstants.ORDER_UNDER_PREPARATION, position);
                    }
                };

                DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                DialogUtils.showAlert(context,
                        R.string.alert_dialog_title_accept_order, R.string.alert_dialog_text_accept_order,
                        context.getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
                        context.getResources().getString(R.string.alert_dialog_bt_no), negativeButton);
            }
        });

        holder.ivReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        restaurantOrderListMvpPresenter.acceptRejectOrder(orderLists.get(position).getOrderId(), AppConstants.ORDER_DECLINE, position);
                    }
                };

                DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                DialogUtils.showAlert(context,
                        R.string.alert_dialog_title_reject_order, R.string.alert_dialog_text_reject_order,
                        context.getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
                        context.getResources().getString(R.string.alert_dialog_bt_no), negativeButton);

            }
        });

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = (int) v.getTag();
                Intent i = new Intent(context, OrderHistoryDetailActivity.class);
                i.putExtra("order_id", orderLists.get(pos).getOrderId());
                i.putExtra("showUpdateButton", false);
//                context.startActivity(i);
                /*((Activity) context)*/
                restaurantOrderListFragment.startActivityForResult(i, 20);
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
