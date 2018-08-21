package com.pavers.candidatetest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {


    public Retrofit databaseServer() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://13.80.107.110:3000/api/v1/")
                .build();
    }


    public Retrofit imageServer() {

        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://randomuser.me/api/")
                .build();
    }

}
