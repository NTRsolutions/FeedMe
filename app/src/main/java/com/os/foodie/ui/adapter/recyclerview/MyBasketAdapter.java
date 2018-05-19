package com.os.foodie.ui.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.os.foodie.R;
import com.os.foodie.application.AppController;
import com.os.foodie.data.network.model.cart.view.CartList;
import com.os.foodie.data.network.model.coursetype.list.Course;
import com.os.foodie.ui.mybasket.MyBasketMvpPresenter;
import com.os.foodie.ui.mybasket.MyBasketMvpView;
import com.os.foodie.utils.AppConstants;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBasketAdapter extends RecyclerView.Adapter<MyBasketAdapter.MyBasketViewHolder> {

    private Context context;
    private Activity activity;
    private String currency;
    private ArrayList<CartList> cartLists;

    private ForClick forClick;

    public MyBasketAdapter(Activity activity, ArrayList<CartList> cartLists) {
        this.activity = activity;
        this.context = activity;
        this.cartLists = cartLists;
    }

    class MyBasketViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView civDishImage;
        public TextView tvItemName, tvItemQuantity, tvAmount;

        public MyBasketViewHolder(View itemView) {
            super(itemView);

            civDishImage = (CircleImageView) itemView.findViewById(R.id.recyclerview_mybasket_item_civ_dish_image);

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
    public void onBindViewHolder(MyBasketViewHolder holder, final int position) {

        CartList cartList = cartLists.get(position);

        if (forClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("itemView", "onClick");
                    forClick.onClick(v, position);
                }
            });
        }

        Glide.with(context)
                .load(cartList.getDishImage())
//                .placeholder(R.mipmap.img_placeholder)
                .error(R.mipmap.img_placeholder)
                .into(holder.civDishImage);

        if (cartList.getDishImage() != null && !cartList.getDishImage().isEmpty()) {

            holder.civDishImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ImageViewer.Builder(context, new String[]{cartLists.get(position).getDishImage()})
                            .setStartPosition(position)
                            .show();
                }
            });
        }

        if (AppController.get(activity).getAppDataManager().getLanguage().equalsIgnoreCase(AppConstants.LANG_AR)) {
            holder.tvItemName.setText(cartList.getNameArabic());
        } else {
            holder.tvItemName.setText(cartList.getName());
        }

        holder.tvItemQuantity.setText("QTY: " + cartList.getQty());

        float price = Float.parseFloat(cartList.getPrice());
        int quantity = Integer.parseInt(cartList.getQty());

        float totalAmount = 0;
        totalAmount += price * quantity;

        holder.tvAmount.setText(currency + totalAmount);
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setForClick(ForClick forClick) {
        this.forClick = forClick;
    }

    public interface ForClick {
        void onClick(View view, int position);
    }
}