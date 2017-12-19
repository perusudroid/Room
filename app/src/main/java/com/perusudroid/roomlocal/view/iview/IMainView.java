package com.perusudroid.roomlocal.view.iview;

import com.perusudroid.roomlocal.model.dto.response.Data;

import java.util.List;

/**
 * Created by perusu on 20/11/17.
 */

public interface IMainView extends IView{


    void refreshRecyclerData(List<Data> newData);
}
