package com.perusudroid.roomlocal.model;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by perusu on 13/5/17.
 */

public interface ApiInterface {

    @GET("index.php?apicall=getDishes")
    Observable<Response<ResponseBody>> getDishes();

}
