package com.perusudroid.roomlocal.model.dto.response;

/**
 * Awesome Pojo Generator
 */
public class Shop_location {
    private String lng;
    private String lat;

    public Shop_location(String lng, String lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }
}