package com.abdul.githubcontributions.api;

public class ApiUtil {
    public static WebserviceApi getServiceClass() {
        return RetrofitFactory.getInstance().create(WebserviceApi.class);
    }
}
