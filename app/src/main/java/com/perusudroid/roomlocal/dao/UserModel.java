package com.perusudroid.roomlocal.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by perusu on 11/11/17.
 */

@Entity(tableName = "users")
public class UserModel {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "dish_id")
    private int dish_id;
    @ColumnInfo(name = "dish_shop")
    private int dish_shop;
    @ColumnInfo(name = "dish_pic")
    private String dish_pic;
    @ColumnInfo(name = "shop_id")
    private int shop_id;
    @ColumnInfo(name = "dish_type")
    private String dish_type;
    @ColumnInfo(name = "dish_expiration")
    private String dish_expiration;
    @ColumnInfo(name = "lat")
    private String lat;
    @ColumnInfo(name = "lng")
    private String lng;
    @ColumnInfo(name = "shop_name")
    private String shop_mobile;
    @ColumnInfo(name = "shop_mobile")
    private String shop_timing;
    @ColumnInfo(name = "shop_timing")
    private String shop_name;
    @ColumnInfo(name = "shop_address")
    private String shop_address;
    @ColumnInfo(name = "shop_detail_address")
    private String shop_detail_address;
    @ColumnInfo(name = "dish_name")
    private String dish_name;
    @ColumnInfo(name = "dish_description")
    private String dish_description;

    public UserModel() {
    }

    public UserModel(@NonNull int _id, int dish_shop, int shop_id, String dish_type, String dish_expiration,
                     String lat,String lng, String shop_name, String shop_address, String dish_name,
                     String shop_mobile, String shop_timing, String shop_detail_address, String dish_description, String dish_pic) {
        this.dish_id = _id;
        this.dish_shop = dish_shop;
        this.shop_id = shop_id;
        this.dish_type = dish_type;
        this.dish_type = dish_type;
        this.dish_expiration = dish_expiration;
        this.lat = lat;
        this.lng = lng;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.dish_name = dish_name;
        this.shop_mobile = shop_mobile;
        this.shop_timing = shop_timing;
        this.shop_detail_address = shop_detail_address;
        this.dish_description = dish_description;
        this.dish_pic = dish_pic;
    }

    public String getDish_pic() {
        return dish_pic;
    }

    public void setDish_pic(String dish_pic) {
        this.dish_pic = dish_pic;
    }

    public String getDish_description() {
        return dish_description;
    }

    public void setDish_description(String dish_description) {
        this.dish_description = dish_description;
    }

    public String getShop_detail_address() {
        return shop_detail_address;
    }

    public void setShop_detail_address(String shop_detail_address) {
        this.shop_detail_address = shop_detail_address;
    }

    public String getShop_mobile() {
        return shop_mobile;
    }

    public void setShop_mobile(String shop_mobile) {
        this.shop_mobile = shop_mobile;
    }

    public String getShop_timing() {
        return shop_timing;
    }

    public void setShop_timing(String shop_timing) {
        this.shop_timing = shop_timing;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @NonNull
    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(@NonNull int dish_id) {
        this.dish_id = dish_id;
    }

    public int getDish_shop() {
        return dish_shop;
    }

    public void setDish_shop(int dish_shop) {
        this.dish_shop = dish_shop;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }

    public String getDish_expiration() {
        return dish_expiration;
    }

    public void setDish_expiration(String dish_expiration) {
        this.dish_expiration = dish_expiration;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }
}
