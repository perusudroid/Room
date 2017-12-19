package com.perusudroid.roomlocal.view.iview;

import com.perusudroid.roomlocal.dao.UserModel;

import java.util.List;

/**
 * Created by perusu on 7/12/17.
 */

public interface IDetailView extends IView{


    void setDishInfoData(List<UserModel> data);
}
