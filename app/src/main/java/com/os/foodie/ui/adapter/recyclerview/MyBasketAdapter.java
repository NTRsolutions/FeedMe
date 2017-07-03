package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.coursetype.list.Course;

import java.util.ArrayList;

public class MyBasketAdapter extends RecyclerView.Adapter<MyBasketAdapter.MyBasketViewHolder> {

    private Context context;
    private ArrayList<CartList> cartLists;

    public MyBasketAdapter(Context context, ArrayList<CartList> cartLists) {
        this.context = context;
        this.cartLists = cartLists;
    }

    class MyBasketViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItemName, tvItemQuantity, tvAmount;

        public MyBasketViewHolder(View itemView) {
            super(itemView);

            tvItemName = (TextView) itemView.findViewById(R.id.recyclerview_mybasket_item_tv_item_name);
            tvItemQuantity = (TextView) itemView.findViewById(R.id.recyclerview_mybasket_item_tv_item_quantity);
            tvAmount = (TextView) itemView.findViewById(R.id.recyclerview_mybasket_item_tv_amount);
        }
    }

    @Override
    public MyBasketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mybasket_item, parent, false);
        return new MyBasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyBasketViewHolder holder, int position) {

        CartList cartList = cartLists.get(position);

        holder.tvItemName.setText(cartList.getName());
        holder.tvItemQuantity.setText("QTY: " + cartList.getQty());

        float price = Float.parseFloat(cartList.getPrice());
        int quantity = Integer.parseInt(cartList.getQty());

        float totalAmount = 0;
        totalAmount += price * quantity;

        holder.tvAmount.setText("$" + totalAmount);
    }

    @Override
    public int getItemCount() {
        return cartLists.size();
    }

    public void removeItem(int position) {
        cartLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartLists.size());
    }
}