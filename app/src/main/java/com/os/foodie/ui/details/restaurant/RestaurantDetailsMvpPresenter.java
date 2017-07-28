package com.os.foodie.ui.details.restaurant;

import com.os.foodie.data.network.model.cart.add.AddToCartRequest;
import com.os.foodie.ui.base.MvpPresenter;

public interface RestaurantDetailsMvpPresenter<V extends RestaurantDetailsMvpView> extends MvpPresenter<V> {

    void getRestaurantDetails(String restaurantId);

    void doLikeDislike(String restaurantId);

    void addItemToCart(int position, AddToCartRequest addToCartRequest);

    void removeFromMyBasket(String userId, String itemId, String restaurantId, int position);

    void updateMyBasket(String userId, String restaurantId, String itemId, String quantity, String price, int position);

//    void clearBasket();

    String getCustomerRestaurantId();

    void setCustomerRestaurantId(String restaurantId);

    void dispose();
}