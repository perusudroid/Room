package com.perusudroid.roomlocal.model.dto.response;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class DishInfoResponse {
    private List<Data> data;
    private Boolean success;
    private String message;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}