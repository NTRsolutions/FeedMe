package com.os.foodie.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.os.foodie.R;
import com.os.foodie.data.network.model.payment.getall.Card;
import com.os.foodie.ui.payment.show.PaymentMethodMvpPresenter;
import com.os.foodie.ui.payment.show.PaymentMethodMvpView;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder> {

    private Context context;
    private List<Card> cardArrayList = new ArrayList<>();
    private int selectedPosition = 0;

    private PaymentMethodMvpPresenter<PaymentMethodMvpView> paymentMethodMvpPresenter;

    public PaymentMethodAdapter(Context context, List<Card> cardArrayList, PaymentMethodMvpPresenter<PaymentMethodMvpView> paymentMethodMvpPresenter) {
        this.context = context;
        this.cardArrayList = cardArrayList;
        this.paymentMethodMvpPresenter = paymentMethodMvpPresenter;
    }

    public class PaymentMethodViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCardNumber, tvCardType;
        public ImageButton btRemoveCard;
//        public RadioButton rbSelector;
//        ImageView ivCardType;

        public PaymentMethodViewHolder(View view) {
            super(view);
            this.tvCardNumber = (TextView) view.findViewById(R.id.recyclerview_payment_method_tv_card_number);
            this.tvCardType = (TextView) view.findViewById(R.id.recyclerview_payment_method_tv_card_type);
//            this.rbSelector = (RadioButton) view.findViewById(R.id.recyclerview_payment_method_rb_select);
//            this.ivCardType = (ImageView) view.findViewById(R.id.recyclerview_payment_card_iv_card_type);
            this.btRemoveCard = (ImageButton) view.findViewById(R.id.recyclerview_payment_method_ib_remove_card);
        }
    }

    @Override
    public PaymentMethodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_payment_method, parent, false);
        return new PaymentMethodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PaymentMethodViewHolder holder, final int position) {

        final Card card = cardArrayList.get(position);

        holder.tvCardType.setText(card.getCardType());
        holder.tvCardNumber.setText(card.getCreditCardNumber());

        holder.btRemoveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodMvpPresenter.deletePaymentCard(card.getCardId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardArrayList.size();
    }
}