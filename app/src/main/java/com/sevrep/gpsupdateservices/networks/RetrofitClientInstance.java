package com.sevrep.gpsupdateservices.networks;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by : Ivan Peter Semilla on 18/10/2021
 */
public class RetrofitClientInstance {

    Context mContext;
    private static Retrofit retrofit = null;
    public static String http_Server_Url = "/api/";

    public RetrofitClientInstance(Context mContext) {
        this.mContext = mContext;
    }

    public String getHttp_Server_Url(String ipPort) {
        return "http://" + ipPort + http_Server_Url;
    }

    public Retrofit getRetrofitInstance(String ipPort) {
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
            return chain.proceed(request);
        }).build();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(getHttp_Server_Url(ipPort))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}