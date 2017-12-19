package com.perusudroid.roomlocal.db;

import android.content.Context;

import com.perusudroid.roomlocal.datasource.IUserDataSource;
import com.perusudroid.roomlocal.datasource.UserDataSource;
import com.perusudroid.roomlocal.model.ViewModelFactory;

/**
 * Created by perusu on 11/11/17.
 */

public class InjectDB {

    public static IUserDataSource provideUserDataSource(Context context) {
        UserDB database = UserDB.getInstance(context);
        return new UserDataSource(database.iuserDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        IUserDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
