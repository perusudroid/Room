package com.perusudroid.roomlocal.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.perusudroid.roomlocal.dao.IUserDAO;
import com.perusudroid.roomlocal.dao.UserModel;


/**
 * Created by perusu on 11/11/17.
 */
@Database(entities = {UserModel.class}, version = 5)
public abstract class UserDB extends RoomDatabase {
    private static volatile UserDB INSTANCE;

    public abstract IUserDAO iuserDao();

    public static UserDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDB.class, "Sample.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
