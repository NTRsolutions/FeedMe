package com.os.foodie.ui.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.data.network.model.cart.list.CartList;
import com.os.foodie.ui.mybasket.MyBasketActivity;
import com.os.foodie.utils.AppConstants;

import java.util.ArrayList;

public class MyBasketListAdapter extends RecyclerView.Adapter<MyBasketListAdapter.MyBasketListViewHolder> {

    private Activity activity;
    private ArrayList<CartList> cartLists;

    public MyBasketListAdapter(Activity activity, ArrayList<CartList> cartLists) {
        this.activity = activity;
        this.cartLists = cartLists;
    }

    public class MyBasketListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivRestaurantLogo;
        TextView tvRestaurantName, tvViewBasket;

        public MyBasketListViewHolder(View itemView) {
            super(itemView);

            ivRestaurantLogo = (ImageView) itemView.findViewById(R.id.recyclerview_my_basket_list_item_iv_restaurant_logo);
            tvRestaurantName = (TextView) itemView.findViewById(R.id.recyclerview_my_basket_list_item_tv_restaurant_name);
            tvViewBasket = (TextView) itemView.findViewById(R.id.recyclerview_my_basket_list_item_tv_view_basket);
        }
    }

    @Override
    public MyBasketListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_basket_list_item, parent, false);
        return new MyBasketListViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(final MyBasketListViewHolder holder, final int position) {

        CartList cartList = cartLists.get(position);

        Glide.with(activity)
                .load(cartList.getLogo())
                .into(holder.ivRestaurantLogo);

        holder.tvRestaurantName.setText(cartList.getRestaurantName());

        holder.tvViewBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, MyBasketActivity.class);
                intent.putExtra(AppConstants.RESTAURANT_ID, cartLists.get(position).getRestaurantId());
                activity.startActivityForResult(intent, 10);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartLists.size();
    }
}
