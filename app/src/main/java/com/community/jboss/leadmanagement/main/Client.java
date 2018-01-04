package com.community.jboss.leadmanagement.main;

import com.community.jboss.leadmanagement.main.model.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jatin on 04-01-2018.
 */

public interface Client {

    @FormUrlEncoded
    @POST("api/newcall")
    Call<Result> newCall(@Field("call") String call);

    @FormUrlEncoded
    @POST("api/endcall")
    Call<Result> endCall(@Field("Message") String message);
}
