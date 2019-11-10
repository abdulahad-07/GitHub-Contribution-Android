package com.abdul.githubcontributions.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebserviceApi {

    @GET
    Call<ResponseBody> getData(@Url String url);
}
