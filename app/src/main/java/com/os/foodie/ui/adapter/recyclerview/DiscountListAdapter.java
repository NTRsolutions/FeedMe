package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.discount.list.DiscountList;
import com.os.foodie.ui.discount.add.DiscountAddActivity;
import com.os.foodie.ui.discount.list.DiscountListMvpPresenter;
import com.os.foodie.ui.discount.list.DiscountListMvpView;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.CommonUtils;
import com.os.foodie.utils.DialogUtils;
import com.os.foodie.utils.ScreenUtils;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DiscountListAdapter extends RecyclerView.Adapter<DiscountListAdapter.DiscountListAdapterViewHolder> {

    private Context context;
    private ArrayList<DiscountList> discountLists;
    DiscountListMvpPresenter<DiscountListMvpView> discountListMvpPresenter;

    public DiscountListAdapter(Context context, DiscountListMvpPresenter<DiscountListMvpView> discountListMvpPresenter, ArrayList<DiscountList> discountLists) {
        this.context = context;
        this.discountLists = discountLists;
        this.discountListMvpPresenter = discountListMvpPresenter;
    }

    class DiscountListAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDiscount, tvProductsNames, tvDiscountDate, tvDiscountMinimumAmount;
        public LinearLayout llMinAmount;
        public ImageView ivOverflow;

        public DiscountListAdapterViewHolder(View itemView) {
            super(itemView);

            tvDiscount = (TextView) itemView.findViewById(R.id.recyclerview_discount_list_tv_discount);
            tvProductsNames = (TextView) itemView.findViewById(R.id.recyclerview_discount_list_tv_products_names);
            tvDiscountDate = (TextView) itemView.findViewById(R.id.recyclerview_discount_list_tv_discount_date);
            tvDiscountMinimumAmount = (TextView) itemView.findViewById(R.id.recyclerview_discount_list_tv_discount_min_amount);

            llMinAmount = (LinearLayout) itemView.findViewById(R.id.min_amount_ll);

            ivOverflow = (ImageView) itemView.findViewById(R.id.recyclerview_discount_list_iv_overflow);
        }
    }

    @Override
    public DiscountListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_discount_list, parent, false);
        return new DiscountListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DiscountListAdapterViewHolder holder, final int position) {

        final DiscountList discountList = discountLists.get(position);

        holder.tvDiscount.setText(discountList.getDiscountPercentage() + "%");

        String names = "";

        for (int i = 0; i < discountList.getDishes().size(); i++) {
            names = names + "," + discountList.getDishes().get(i).getName();
        }

        if (names.length() > 0) {

            names = names.substring(1);
            holder.tvProductsNames.setVisibility(View.VISIBLE);
            holder.llMinAmount.setVisibility(View.GONE);

        } else {

            holder.tvProductsNames.setVisibility(View.GONE);
            holder.llMinAmount.setVisibility(View.VISIBLE);
        }

        holder.tvProductsNames.setText(names);
        holder.tvDiscountDate.setText(CommonUtils.ConvertDate(discountList.getStartDate(), discountList.getEndDate()));
        holder.tvDiscountMinimumAmount.setText(discountListMvpPresenter.getCurrency() + " " + discountList.getMinOrderAmount());

        holder.ivOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                int vY = (int) v.getY();

                int itemViewY = (int) holder.itemView.getY();
                int itemViewMeasuredHeight = (int) holder.itemView.getMeasuredHeight();

                int vX = (int) v.getX();

                int itemViewX = (int) holder.itemView.getX();
                int itemViewMeasuredWidth = (int) holder.itemView.getMeasuredWidth();

                int screenHeight = ScreenUtils.getScreenHeight(context);
                int clickPosition = (int) (itemViewY + vY);
                int limit = screenHeight - (itemViewMeasuredHeight * 3);

                Log.d("getScreenHeight", ">>" + ScreenUtils.getScreenHeight(context));
                Log.d("getMeasuredHeight", ">>" + holder.itemView.getMeasuredHeight());

                Log.d("v.clickPosition", ">>" + clickPosition);
                Log.d("v.clickPosition X", ">>" + (itemViewX + vX));
                Log.d("v.limit", ">>" + limit);
//
//                Log.d("getScreenHeight", ">>" + ScreenUtils.getScreenHeight(context));
//                Log.d("getMeasuredHeight", ">>" + holder.itemView.getMeasuredHeight());
                Log.d("getMeasuredWidth", ">>" + holder.itemView.getMeasuredWidth());
//                Log.d("itemView.getY", ">>" + holder.itemView.getY());
                Log.d("itemView.getX", ">>" + holder.itemView.getX());
                Log.d("v.getX", ">>" + v.getX());
//                Log.d("v.getY", ">>" + v.getY());

                final View popupView;
                final PopupWindow popupWindow;

                if (clickPosition > limit) {

                    popupView = layoutInflater.inflate(R.layout.menu_popup_restaurant_menu_item_top, null);
                    popupWindow = new PopupWindow(
                            popupView,
                            RecyclerView.LayoutParams.WRAP_CONTENT,
                            RecyclerView.LayoutParams.WRAP_CONTENT);

                    popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_popup_background_top));

                } else {

                    popupView = layoutInflater.inflate(R.layout.menu_popup_restaurant_menu_item_bottom, null);
                    popupWindow = new PopupWindow(
                            popupView,
                            RecyclerView.LayoutParams.WRAP_CONTENT,
                            RecyclerView.LayoutParams.WRAP_CONTENT);

                    popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_popup_background_bottom));
                }

                ImageView ivStatus = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_status);
                ImageView ivEdit = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_edit);
                ImageView ivDelete = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_delete);

                ivStatus.setVisibility(View.GONE);

                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                        Intent intent = new Intent(context, DiscountAddActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(AppConstants.DISCOUNT_EDIT_DATA, discountList);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });

                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                popupWindow.dismiss();
                                discountListMvpPresenter.deleteDiscountList(discountList.getDiscountId());
                            }
                        };

                        DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                popupWindow.dismiss();
                            }
                        };

                        DialogUtils.showAlert(context, R.mipmap.ic_delete, R.string.alert_dialog_title_delete_dish, R.string.alert_dialog_text_delete_dish,
                                context.getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
                                context.getResources().getString(R.string.alert_dialog_bt_no), negativeButton);
                    }
                });

                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
//                popupWindow.showAsDropDown(v);

                if (clickPosition > limit) {


                    if (context.getResources().getBoolean(R.bool.is_rtl)) {

                        Log.d("X", "Bottom RTL>>" + -(itemViewMeasuredWidth - (itemViewX + vX)));
                        Log.d("Y", "Bottom RTL>>" + (itemViewY + vY));

                        popupWindow.showAtLocation(v, Gravity.TOP, -(itemViewMeasuredWidth/* - (itemViewX + vX)*/), (int) (itemViewY + vY));

                    } else {

                        Log.d("X", "Bottom >>" + (itemViewMeasuredWidth - (itemViewX + vX)));
                        Log.d("Y", "Bottom >>" + (itemViewY + vY));

                        popupWindow.showAtLocation(v, Gravity.TOP, (int) (itemViewMeasuredWidth), (int) (itemViewY + vY));
                    }

                } else {

                    if (context.getResources().getBoolean(R.bool.is_rtl)) {

                        Log.d("X", "RTL>>" + (itemViewMeasuredWidth - (itemViewX + vX)));
                        Log.d("vY", ">>" + vY);
                        Log.d("itemViewY", ">>" + itemViewY);
                        Log.d("Y", "RTL>>" + (vY - (itemViewY)));

                        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, -(itemViewMeasuredWidth/* - (itemViewX + vX)*/), clickPosition + itemViewMeasuredHeight + vY /*+(itemViewY + vY)*/);

                    } else {

                        Log.d("X", ">>" + 0);
                        Log.d("Y", ">>" + (itemViewY + vY));

                        popupWindow.showAsDropDown(v);
                    }
                }

//                if (clickPosition > limit) {
//
//                    popupWindow.showAtLocation(v, Gravity.TOP, (int) (itemViewMeasuredWidth/* - (itemViewX + vX)*/), (int) (itemViewY + vY));
//
//                } else {
//
//                    popupWindow.showAsDropDown(v);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return discountLists.size();
    }
}