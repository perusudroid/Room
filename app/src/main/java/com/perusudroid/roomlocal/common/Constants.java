package com.perusudroid.roomlocal.common;

/**
 * Created by Guru karthik on 05-12-2016.
 */

public interface Constants {

    interface Common {

    }

    interface InternalHttpCode{
        int SUCCESS_CODE = 200;
        int FAILURE_CODE = 0;
    }

    interface HttpErrorMessage{
        String INTERNAL_SERVER_ERROR = "Our server is under maintenance. We will reslove shortly!";
        String FORBIDDEN = "Seems like you haven't permitted to do this operation!";

    }

    interface NetworkType {
        String WIFI = "Wi-Fi";
        String MOBILE = "Mobile";
    }

    interface BundleKey {
        String BROADCAST_MESSAGE = "broadcastAction";
        String TRANSITION_NAME = "TRANSITION_NAME";
        String DISH_ID = "dishId";
    }

    interface RequestCodes {
        int KEY_REQUEST_CODE_COMPOSE_MAIL = 101;
    }

    interface Transitions{
         String SHARED_ELEMENT = "sharedElement";
    }

    interface BroadCastKey {

    }

    interface SharedPrefKey {

    }

    interface ApiRequestKey{

    }

}
