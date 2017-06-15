package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.os.foodie.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CourseHolder> {

    private Context mContext;
    private List<String> cardList = new ArrayList<>();
    private int selectedPosition = 0;
    private String mTag = "";

    public CardAdapter(Context context, List<String> cardList, String tag) {
        this.mContext = context;
        this.cardList = cardList;
        mTag = tag;
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_payment_card, null);
        CourseHolder mh = new CourseHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, final int position) {
//      /*  holder.rbSelector.setTag(position);
//        holder.btRemoveCard.setTag(position);
//        holder.tvCardNumber.setText(cardList.get(position).getNumber());
//        holder.tvCardType.setText(cardList.get(position).getType());
//        if(mTag.equals("payment")){
//
//            holder.btRemoveCard.setVisibility(View.GONE);
//        }else{
//
//            holder.btRemoveCard.setVisibility(View.VISIBLE);
//        }
//
//
//
//        holder.btRemoveCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = (int) v.getTag();
//
//            }
//        });
//
//        if (cardList.get(position).getDefault() == 1) {
//            selectedPosition = position;
//            holder.rbSelector.setChecked(true);
//        } else {
//            holder.rbSelector.setChecked(false);
//        }
//
//        holder.rbSelector.setTag(position);
//        holder.rbSelector.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                cardList.get(selectedPosition).setDefault(0);
//                selectedPosition = (Integer) view.getTag();
//                cardList.get(selectedPosition).setDefault(1);
//                notifyDataSetChanged();
//
//                if(mTag.equals("manage")) {
//                    callSetCardDefaultWebservice(cardList.get(selectedPosition).getId());
//                }
//                else{
//
//                }
//
//            }
//        });
//*/

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class CourseHolder extends RecyclerView.ViewHolder {

        private TextView tvCardNumber, tvCardType;
        ImageButton btRemoveCard;
        RadioButton rbSelector;
        ImageView ivCardType;

        public CourseHolder(View view) {
            super(view);
            this.tvCardNumber = (TextView) view.findViewById(R.id.recyclerview_payment_card_tv_card_number);
            this.tvCardType = (TextView) view.findViewById(R.id.recyclerview_payment_card_tv_card_type);
            this.rbSelector = (RadioButton) view.findViewById(R.id.recyclerview_payment_card_rb_select);
            this.ivCardType = (ImageView) view.findViewById(R.id.recyclerview_payment_card_iv_card_type);
            this.btRemoveCard = (ImageButton) view.findViewById(R.id.recyclerview_payment_card_ib_remove_card);
        }
    }

///*
//    public void callSetCardDefaultWebservice(String id) {
//        if (Utils.isNetworkAvailable(mContext)) {
//            Utils.showProgress(mContext);
//            Call<ResponseBody> call = null;
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("id", id);
//                jsonObject.put("user_id", MySharedPreferences.getUser_Id(mContext));
//                MyApiEndpointInterface endpointInterface = PikPakApplication.getInstance().getRequestQueue().create(MyApiEndpointInterface.class);
//                call = endpointInterface.setDefaultCardRequest(new ConvertJsonToMap().jsonToMap(jsonObject));
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Utils.hideProgress();
//                        if (response.code() == 200) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Utils.hideProgress();
//
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            Toast.makeText(mContext, "No internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }
//*/
}