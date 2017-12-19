package com.perusudroid.roomlocal.adapter.listener;

import android.view.View;
import android.widget.ImageView;

import com.perusudroid.roomlocal.model.dto.response.Data;

/**
 * Created by perusu on 29/11/17.
 */

public interface DishCallback {

    void onClick(Data data);

    void onClick(String dish_id, View imageView);

    void onLikeClicked(View iView);

    void sampleClick();

}
