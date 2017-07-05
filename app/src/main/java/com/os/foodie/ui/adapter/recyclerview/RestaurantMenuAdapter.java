package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.menu.show.restaurant.Dish;
import com.os.foodie.ui.menu.add.RestaurantMenuAddUpdateDishActivity;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpPresenter;
import com.os.foodie.ui.menu.show.fragment.RestaurantMenuMvpView;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.DialogUtils;
import com.os.foodie.utils.ScreenUtils;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.RestaurantMenuViewHolder> {

    private Context context;
    private ArrayList<Dish> dishArrayList;

    private LinearLayoutManager layoutManager;

    private RestaurantMenuMvpPresenter<RestaurantMenuMvpView> restaurantMenuMvpPresenter;

    public RestaurantMenuAdapter(Context context, RestaurantMenuMvpPresenter<RestaurantMenuMvpView> restaurantMenuMvpPresenter, ArrayList<Dish> dishArrayList, LinearLayoutManager layoutManager) {
        this.context = context;
        this.dishArrayList = dishArrayList;
        this.restaurantMenuMvpPresenter = restaurantMenuMvpPresenter;
        this.layoutManager = layoutManager;
    }

    class RestaurantMenuViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDishName, tvVegNonVeg, tvPrice, tvDescription;
        public ImageView ivOverflow;

        public RestaurantMenuViewHolder(View itemView) {
            super(itemView);

            tvDishName = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_menu_tv_dish_name);
            tvVegNonVeg = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_menu_tv_veg_nonveg);
            tvDescription = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_menu_tv_description);
            tvPrice = (TextView) itemView.findViewById(R.id.recyclerview_restaurant_menu_tv_price);
            ivOverflow = (ImageView) itemView.findViewById(R.id.recyclerview_restaurant_menu_iv_overflow);
        }
    }

    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restaurant_menu, parent, false);
        return new RestaurantMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RestaurantMenuViewHolder holder, final int position) {

        final Dish dish = dishArrayList.get(position);

        holder.tvDishName.setText(dish.getName());
        holder.tvDescription.setText(dish.getDescription());

        if (dish.getPrice().contains(".00")) {
            holder.tvPrice.setText("$" + dish.getPrice().replace(".00", ""));
        } else {
            holder.tvPrice.setText("$" + dish.getPrice());
        }

        if (dish.getVegNonveg().equalsIgnoreCase(AppConstants.VEG)) {
            holder.tvVegNonVeg.setText("Veg");
        } else {
            holder.tvVegNonVeg.setText("Non-Veg");
        }

        holder.ivOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                int vX = (int) v.getX();

                int itemViewY = (int) holder.itemView.getY();
                int itemViewMeasuredHeight = (int) holder.itemView.getMeasuredHeight();

                int vY = (int) v.getY();

                int itemViewX = (int) holder.itemView.getX();
                int itemViewMeasuredWidth = (int) holder.itemView.getMeasuredWidth();

                int screenHeight = ScreenUtils.getScreenHeight(context);
                int clickPosition = (int) (itemViewY + vY);
                int limit = screenHeight - (itemViewMeasuredHeight * 3);

//                Log.d("getScreenHeight", ">>" + ScreenUtils.getScreenHeight(context));
//                Log.d("getMeasuredHeight", ">>" + holder.itemView.getMeasuredHeight());
//
//                Log.d("v.clickPosition", ">>" + clickPosition);
//                Log.d("v.limit", ">>" + limit);
//
////                Log.d("getScreenHeight", ">>" + ScreenUtils.getScreenHeight(context));
////                Log.d("getMeasuredHeight", ">>" + holder.itemView.getMeasuredHeight());
//                Log.d("getMeasuredWidth", ">>" + holder.itemView.getMeasuredWidth());
////                Log.d("itemView.getY", ">>" + holder.itemView.getY());
//                Log.d("itemView.getX", ">>" + holder.itemView.getX());
//                Log.d("v.getX", ">>" + v.getX());
////                Log.d("v.getY", ">>" + v.getY());

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

//                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
//                btnDismiss.setOnClickListener(new Button.OnClickListener(){
//
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }});

//                popupWindow.showAsDropDown(v, 50, -30);

                ImageView ivStatus = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_status);
                ImageView ivEdit = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_edit);
                ImageView ivDelete = (ImageView) popupView.findViewById(R.id.menu_popup_restaurant_menu_item_iv_delete);

                if (dishArrayList.get(position).getAvail().equals("0")) {

//                    ivStatus.setSelected(true);
                    ivStatus.setColorFilter(ContextCompat.getColor(context, R.color.light_grey2));
                } else {

//                    ivStatus.setSelected(false);
                    ivStatus.setColorFilter(ContextCompat.getColor(context, R.color.green));
                }

                ivStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                popupWindow.dismiss();

                                if (dishArrayList.get(position).getAvail().equals("1")) {
                                    dishArrayList.get(position).setAvail("0");
                                } else {
                                    dishArrayList.get(position).setAvail("1");
                                }

                                restaurantMenuMvpPresenter.changeStatusRestaurantMenuList(dishArrayList.get(position));
                            }
                        };

                        DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                popupWindow.dismiss();
                            }
                        };


                        if (dishArrayList.get(position).getAvail().equals("1")) {
                            DialogUtils.showAlert(context, R.string.alert_dialog_title_deactivate_dish, R.string.alert_dialog_text_deactivate_dish,
                                    context.getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
                                    context.getResources().getString(R.string.alert_dialog_bt_no), negativeButton);

                        } else {
                            DialogUtils.showAlert(context, R.string.alert_dialog_title_activate_dish, R.string.alert_dialog_text_activate_dish,
                                    context.getResources().getString(R.string.alert_dialog_bt_yes), positiveButton,
                                    context.getResources().getString(R.string.alert_dialog_bt_no), negativeButton);
                        }
                    }
                });

                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
//                        restaurantMenuMvpPresenter.deleteRestaurantMenuList(dishArrayList.get(position));

                        Intent intent = new Intent(context, RestaurantMenuAddUpdateDishActivity.class);
                        intent.putExtra(AppConstants.EDIT_DISH, dishArrayList.get(position));
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
                                restaurantMenuMvpPresenter.deleteRestaurantMenuList(dishArrayList.get(position));
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

                if (clickPosition > limit) {

                    popupWindow.showAtLocation(v, Gravity.TOP, (int) (itemViewMeasuredWidth - (itemViewX + vX)), (int) (itemViewY + vY));

                } else {

                    popupWindow.showAsDropDown(v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishArrayList.size();
    }
}