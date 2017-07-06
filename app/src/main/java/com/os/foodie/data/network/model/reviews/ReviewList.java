
package com.os.foodie.data.network.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewList {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("time")
    @Expose
    private String time;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReviewList() {
    }

    /**
     * 
     * @param time
     * @param name
     * @param userId
     * @param rating
     * @param review
     */
    public ReviewList(String userId, String name, String rating, String review, String time) {
        super();
        this.userId = userId;
        this.name = name;
        this.rating = rating;
        this.review = review;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
