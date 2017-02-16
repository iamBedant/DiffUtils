package com.rajsuvariya.diffutilstest.API;

import com.rajsuvariya.diffutilstest.Model.ContactApiResult;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kuliza-319 on 16/2/17.
 */

public interface MyAPI {

    @GET("diffutiltestdata")
    Call<ContactApiResult> getResult();
}
