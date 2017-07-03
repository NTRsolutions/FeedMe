package com.os.foodie.data.network.model.order.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.os.foodie.data.network.model.cart.view.CartList;

import java.util.List;

/**
 * Created by monikab on 6/29/2017.
 */
public class OrderHistoryDetail {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class OrderDetail {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("order_delievery_date")
        @Expose
        private String orderDelieveryDate;
        @SerializedName("order_delievery_time")
        @Expose
        private String orderDelieveryTime;
        @SerializedName("payment_method")
        @Expose
        private String paymentMethod;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("delivery_type")
        @Expose
        private String deliveryType;
        @SerializedName("discount")
        @Expose
        private Float discount;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getOrderDelieveryDate() {
            return orderDelieveryDate;
        }

        public void setOrderDelieveryDate(String orderDelieveryDate) {
            this.orderDelieveryDate = orderDelieveryDate;
        }

        public String getOrderDelieveryTime() {
            return orderDelieveryTime;
        }

        public void setOrderDelieveryTime(String orderDelieveryTime) {
            this.orderDelieveryTime = orderDelieveryTime;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public Float getDiscount() {
            return discount;
        }

        public void setDiscount(Float discount) {
            this.discount = discount;
        }

    }


    public class Response {

        @SerializedName("order_detail")
        @Expose
        private OrderDetail orderDetail;
        @SerializedName("item_list")
        @Expose
        private List<CartList> itemList = null;
        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("contact_person_name")
        @Expose
        private Object contactPersonName;
        @SerializedName("delivery_time")
        @Expose
        private String deliveryTime;
        @SerializedName("delivery_type")
        @Expose
        private String deliveryType;
        @SerializedName("delivery_charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("min_order_amount")
        @Expose
        private String minOrderAmount;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("order_count")
        @Expose
        private Integer orderCount;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;


        @SerializedName("mobile_number")
        @Expose
        private String mobileNumber1;
        public String getMobileNumber1() {
            return mobileNumber1;
        }

        public void setMobileNumber(String mobileNumber1) {
            this.mobileNumber1 = mobileNumber1;
        }

        public OrderDetail getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(OrderDetail orderDetail) {
            this.orderDetail = orderDetail;
        }

        public List<CartList> getItemList() {
            return itemList;
        }

        public void setItemList(List<CartList> itemList) {
            this.itemList = itemList;
        }

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public Object getContactPersonName() {
            return contactPersonName;
        }

        public void setContactPersonName(Object contactPersonName) {
            this.contactPersonName = contactPersonName;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public String getMinOrderAmount() {
            return minOrderAmount;
        }

        public void setMinOrderAmount(String minOrderAmount) {
            this.minOrderAmount = minOrderAmount;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Integer getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(Integer orderCount) {
            this.orderCount = orderCount;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }


    public class UserDetail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("mobile_number")
        @Expose
        private String mobileNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

    }
}