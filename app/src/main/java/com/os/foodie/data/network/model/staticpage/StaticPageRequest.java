package com.os.foodie.data.network.model.staticpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abhinava on 6/30/2017.
 */


public class StaticPageRequest
{

    public StaticPageRequest(String slug)
    {
        this.slug=slug;
    }

    @SerializedName("slug")
    @Expose
    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

}