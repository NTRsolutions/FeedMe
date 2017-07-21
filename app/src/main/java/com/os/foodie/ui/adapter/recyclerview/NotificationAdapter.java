package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.utils.Utils;
import com.os.foodie.R;
import com.os.foodie.data.network.model.notification.NotificationListResponse;
import com.os.foodie.ui.notification.NotificationMvpPresenter;
import com.os.foodie.ui.notification.NotificationMvpView;
import com.os.foodie.ui.order.restaurant.detail.OrderHistoryDetailActivity;
import com.os.foodie.utils.AppConstants;
import com.os.foodie.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by monikab on 8/9/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CourseHolder> {

    NotificationMvpPresenter<NotificationMvpView> notificationMvpPresenter;
    private Context mContext;
    List<NotificationListResponse.NotificationList> notificationAry = new ArrayList<>();

    public NotificationAdapter(Context context, List<NotificationListResponse.NotificationList> notificationAry,NotificationMvpPresenter<NotificationMvpView> notificationMvpPresenter) {
        this.mContext = context;
        this.notificationAry = notificationAry;
        this.notificationMvpPresenter=notificationMvpPresenter;
    }


    @Override
    public CourseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_notification_row_selector, null);
        CourseHolder mh = new CourseHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, final int position) {
        holder.front_ll.setTag(position);
        holder.notification_tv.setText(notificationAry.get(position).getNotificationMessage());
        holder.notification_time_tv.setText(TimeFormatUtils.changeTimeFormat(notificationAry.get(position).getNotificationTime(), "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy hh:mm a"));
        if (notificationAry.get(position).getIsRead().equals("0")) {
            holder.back_ll.setVisibility(View.VISIBLE);
        } else {
            holder.back_ll.setVisibility(View.GONE);
        }
        holder.back_ll.setTag(position);
        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if(notificationAry.get(pos).getIsRead().equals("0")) {
                    notificationAry.get(pos).setIsRead("1");
                    notifyItemChanged(pos);
                    notificationMvpPresenter.readNotification(notificationAry.get(pos).getNotificationId());
                }
                Intent i = new Intent(mContext, OrderHistoryDetailActivity.class);
                i.putExtra("order_id", notificationAry.get(pos).getOrderId());
                i.putExtra("showUpdateButton", setStatusButtonOnOrderHistory(notificationAry.get(pos).getNotificationType()));
                mContext.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return notificationAry.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {

        private TextView notification_tv, notification_time_tv;
        LinearLayout back_ll,front_ll;

        public CourseHolder(View view) {
            super(view);
            this.notification_tv = (TextView) view.findViewById(R.id.notification_tv);
            this.notification_time_tv = (TextView) view.findViewById(R.id.notification_time_tv);
            this.back_ll = (LinearLayout) view.findViewById(R.id.back_ll);
            this.front_ll = (LinearLayout) view.findViewById(R.id.front_ll);
        }

    }


    boolean setStatusButtonOnOrderHistory(String notificationType){
        boolean showStatus=false;
        if(notificationMvpPresenter.getDataManager().getCurrentUserType().equalsIgnoreCase(AppConstants.CUSTOMER)){
            showStatus=false;
        }
        else if(notificationType.equalsIgnoreCase("order_received") || notificationType.equalsIgnoreCase("order_reject") || notificationType.equalsIgnoreCase("picked") || notificationType.equalsIgnoreCase("delivered")) {
            showStatus = false;
        }else{
            showStatus = true;
        }
        return  showStatus;
    }


}

