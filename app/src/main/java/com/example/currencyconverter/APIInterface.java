package com.example.currencyconverter;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("latest/{base}")
    Call<JsonObject> getExchangeRates(@Path("base") String base);

}
