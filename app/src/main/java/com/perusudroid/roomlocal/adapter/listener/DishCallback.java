package com.perusudroid.roomlocal.adapter.listener;

import android.view.View;
import android.widget.ImageView;

import com.perusudroid.roomlocal.model.dto.response.Data;

/**
 * Created by perusu on 29/11/17.
 */

/*
    Dishcallback is used to interact with the adapter directly from layout
 */

public interface DishCallback {


    void onClick(Data data); // onClick pass data class

    void onClick(String dish_id, View imageView); // onClick pass data and with the view where click occured

    void onLikeClicked(View iView); // onClick pass view only

    void sampleClick(); // sampleClick with no params

}
