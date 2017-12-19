package com.perusudroid.roomlocal.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.perusudroid.roomlocal.BR;

/**
 * Created by perusu on 5/11/17.
 */

public class ObservableUser extends BaseObservable implements Parcelable {

    private String dish_id;
    private String dish_shop;
    private String shop_id;
    private String dish_type;
    private String dish_expiration;
    private String shop_location;
    private String shop_name;
    private String shop_address;
    private String dish_name;


    public ObservableUser() {
    }


    protected ObservableUser(Parcel in) {
        dish_id = in.readString();
        dish_shop = in.readString();
        shop_id = in.readString();
        dish_type = in.readString();
        dish_expiration = in.readString();
        shop_location = in.readString();
        shop_name = in.readString();
        shop_address = in.readString();
        dish_name = in.readString();
    }

    public static final Creator<ObservableUser> CREATOR = new Creator<ObservableUser>() {
        @Override
        public ObservableUser createFromParcel(Parcel in) {
            return new ObservableUser(in);
        }

        @Override
        public ObservableUser[] newArray(int size) {
            return new ObservableUser[size];
        }
    };

    public String getDish_id() {
        return dish_id;
    }

    public String getDish_shop() {
        return dish_shop;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getDish_type() {
        return dish_type;
    }


    public String getDish_expiration() {
        return dish_expiration;
    }

    public String getShop_location() {
        return shop_location;
    }

    public String getShop_name() {
        return shop_name;
    }


    public String getDish_name() {
        return dish_name;
    }

    public String getShop_address() {
        return shop_address;
    }

  /*  public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
        notifyPropertyChanged(BR.dish_id);
    }

    public void setDish_shop(String dish_shop) {
        this.dish_shop = dish_shop;
        notifyPropertyChanged(BR.dish_shop);
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
        notifyPropertyChanged(BR.shop_id);
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
        notifyPropertyChanged(BR.dish_type);
    }

    public void setDish_expiration(String dish_expiration) {
        this.dish_expiration = dish_expiration;
        notifyPropertyChanged(BR.dish_expiration);
    }


    public void setShop_location(String shop_location) {
        this.shop_location = shop_location;
        notifyPropertyChanged(BR.shop_location);
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
        notifyPropertyChanged(BR.shop_name);
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
        notifyPropertyChanged(BR.shop_address);
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
        notifyPropertyChanged(BR.dish_name);
    }
*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dish_id);
        parcel.writeString(dish_shop);
        parcel.writeString(shop_id);
        parcel.writeString(dish_type);
        parcel.writeString(dish_expiration);
        parcel.writeString(shop_location);
        parcel.writeString(shop_name);
        parcel.writeString(shop_address);
        parcel.writeString(dish_name);
    }
}
