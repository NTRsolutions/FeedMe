package com.os.foodie.model;

public class TempModelRestaurantOrder {

    String orderId, itemName, deliveryTime, orderType, discount, price;

    public TempModelRestaurantOrder() {
    }

    public TempModelRestaurantOrder(String orderId, String itemName, String deliveryTime, String orderType, String discount, String price) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.deliveryTime = deliveryTime;
        this.orderType = orderType;
        this.discount = discount;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
