package com.example.currencyconverter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static String BASE_URL = "https://prime.exchangerate-api.com/v5/e5b8c712eb64c2a1b867cdc8/";

    private static Retrofit retrofit;

    public static Retrofit gerClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
