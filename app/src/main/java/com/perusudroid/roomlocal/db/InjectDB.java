package com.perusudroid.roomlocal.db;

import android.content.Context;

import com.perusudroid.roomlocal.datasource.DishDataSource;
import com.perusudroid.roomlocal.datasource.IDishDataSource;
import com.perusudroid.roomlocal.model.ViewModelFactory;

/**
 * Created by perusu on 11/11/17.
 */

public class InjectDB {

    public static IDishDataSource provideUserDataSource(Context context) {
        DishDB database = DishDB.getInstance(context);
        return new DishDataSource(database.iuserDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        IDishDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
