package com.happyface.helpers;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitModel {

    public static ApiInterface getApi(Context context) {
        return getAPI(context);
    }

    public static ApiInterface getApi() {
        return getAPI(null);
    }

    private static ApiInterface getAPI(Context context) {
        Retrofit retrofit;
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        builder.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.addHeader("lang", StaticMembers.getLanguage(context)).build();
            if (context != null && PrefManager.getInstance(context).getAPIToken() != null &&
                    !PrefManager.getInstance(context).getAPIToken().isEmpty())
                requestBuilder.addHeader("Authorization", "Bearer " + PrefManager.getInstance(context).getAPIToken()).build();
            return chain.proceed(requestBuilder.build());
        });
        retrofit = new Retrofit.Builder().baseUrl(StaticMembers.domain).client(builder.build()).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(ApiInterface.class);
    }
}
