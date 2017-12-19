package com.perusudroid.roomlocal.model.dto.response;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Awesome Pojo Generator
 */
public class Data {
    private static final String TAG = Data.class.getSimpleName();
    private String dish_description;
    private String dish_pic;
    private Integer dish_likes;
    private Integer dish_discount_cost;
    private String shop_name;
    private String shop_timing;
    private String shop_detail_address;
    private String shop_address;
    private Integer shop_id;
    private Integer dish_id;
    private Integer dish_shop;
    private String dish_type;
    private String dish_expiration;
    private Integer dish_cost;
    private Long shop_mobile;
    private Shop_location shop_location;
    private String dish_name;


    public Data(int dish_id, String dish_expiration, Shop_location shop_location, String dish_name, String shop_name, String shop_address, String shop_detail_address, String dish_pic) {
        this.dish_id = dish_id;
        this.dish_expiration = dish_expiration;
        this.shop_location = shop_location;
        this.dish_name = dish_name;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.shop_detail_address = shop_detail_address;
        this.dish_pic = dish_pic;
    }


    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public Integer getDish_shop() {
        return dish_shop;
    }

    public void setDish_shop(Integer dish_shop) {
        this.dish_shop = dish_shop;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }

    public void setDish_description(String dish_description) {
        this.dish_description = dish_description;
    }

    public String getDish_description() {
        return dish_description;
    }

    public void setDish_pic(String dish_pic) {
        this.dish_pic = dish_pic;
    }

    public String getDish_pic() {
        return dish_pic;
    }


    @BindingAdapter({"bind:dish_pic"})
    public static void loadImage(ImageView view, String imageUrl) {
        Log.d(TAG, "loadImage: "+ imageUrl);
        Picasso.with(view.getContext())
                .load("http://192.168.43.248/RoomApi/"+imageUrl)
                .into(view);
    }

    public void setDish_likes(Integer dish_likes) {
        this.dish_likes = dish_likes;
    }

    public Integer getDish_likes() {
        return dish_likes;
    }

    public void setDish_discount_cost(Integer dish_discount_cost) {
        this.dish_discount_cost = dish_discount_cost;
    }

    public Integer getDish_discount_cost() {
        return dish_discount_cost;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_timing(String shop_timing) {
        this.shop_timing = shop_timing;
    }

    public String getShop_timing() {
        return shop_timing;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setDish_expiration(String dish_expiration) {
        this.dish_expiration = dish_expiration;
    }

    public String getDish_expiration() {
        return dish_expiration;
    }

    public void setDish_cost(Integer dish_cost) {
        this.dish_cost = dish_cost;
    }

    public Integer getDish_cost() {
        return dish_cost;
    }

    public void setShop_mobile(Long shop_mobile) {
        this.shop_mobile = shop_mobile;
    }

    public Long getShop_mobile() {
        return shop_mobile;
    }

    public void setShop_location(Shop_location shop_location) {
        this.shop_location = shop_location;
    }

    public Shop_location getShop_location() {
        return shop_location;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_name() {
        return dish_name;
    }
}