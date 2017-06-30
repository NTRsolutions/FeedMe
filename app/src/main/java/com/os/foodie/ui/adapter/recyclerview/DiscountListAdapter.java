package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DiscountListAdapter extends RecyclerView.Adapter<DiscountListAdapter.DiscountListAdapterViewHolder> {

    private Context context;
    private ArrayList<DiscountList> discountLists;
    DiscountListMvpPresenter<DiscountListMvpView> discountListMvpPresenter;

    public DiscountListAdapter(Context context,DiscountListMvpPresenter<DiscountListMvpView> discountListMvpPresenter, ArrayList<DiscountList> discountLists)
    {
        this.context = context;
        this.discountLists = discountLists;
        this.discountListMvpPresenter=discountListMvpPresenter;
    }

    class DiscountListAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView discount_tv, products_names_tv, dis_date_tv, dis_min_amount_tv;
        LinearLayout min_amount_ll;
        public ImageView ivOverflow;


        public DiscountListAdapterViewHolder(View itemView) {
            super(itemView);

            discount_tv = (TextView) itemView.findViewById(R.id.discount_tv);
            products_names_tv = (TextView) itemView.findViewById(R.id.products_names_tv);
            dis_date_tv = (TextView) itemView.findViewById(R.id.dis_date_tv);
            dis_min_amount_tv = (TextView) itemView.findViewById(R.id.dis_min_amount_tv);
            min_amount_ll = (LinearLayout) itemView.findViewById(R.id.min_amount_ll);
            ivOverflow = (ImageView) itemView.findViewById(R.id.recyclerview_restaurant_discount_iv_overflow);

        }
    }

    @Override
    public DiscountListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_discount_list, parent, false);
        return new DiscountListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscountListAdapterViewHolder holder, final int position) {

        final DiscountList discountList = discountLists.get(position);

        holder.discount_tv.setText(discountList.getDiscountPercentage() + "%");

        String names="";
        for(int i=0;i<discountList.getDishes().size();i++)
        {
            names=names+","+discountList.getDishes().get(i).getName();
        }

        if(names.length()>0) {
            names = names.substring(1);
            holder.products_names_tv.setVisibility(View.VISIBLE);
            holder.min_amount_ll.setVisibility(View.GONE);
        }
        else
        {
            holder.products_names_tv.setVisibility(View.GONE);
            holder.min_amount_ll.setVisibility(View.VISIBLE);
        }

        holder.products_names_tv.setText(names);
        holder.dis_date_tv.setText(CommonUtils.ConvertDate(discountList.getStartDate(),discountList.getEndDate()));
        holder.dis_min_amount_tv.setText(discountList.getMinOrderAmount());

        holder.ivOverflow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                final View popupView = layoutInflater.inflate(R.layout.menu_popup_restaurant_menu_item, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        RecyclerView.LayoutParams.WRAP_CONTENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT);

                ImageView ivStatus = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_status);
                ImageView ivEdit = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_edit);
                ImageView ivDelete = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_delete);

                ivStatus.setVisibility(View.GONE);


                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                        Intent intent = new Intent(context, DiscountAddActivity.class);
                        Bundle bundle=new Bundle();
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
                            public void onClick(DialogInterface dialog, int which)
                            {
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

                popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_popup_background));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return discountLists.size();
    }
}
