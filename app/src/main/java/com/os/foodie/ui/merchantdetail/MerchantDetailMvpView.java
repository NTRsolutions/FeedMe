package com.os.foodie.ui.merchantdetail;

import com.os.foodie.data.network.model.merchantdetails.get.GetMerchantDetailResponse;
import com.os.foodie.ui.base.MvpView;

public interface MerchantDetailMvpView extends MvpView {

    void setMerchantDetail(GetMerchantDetailResponse merchantDetailResponse);

    void onMerchantDetailUpdationSuccess();

    void onMerchantDetailUpdationFail();
}