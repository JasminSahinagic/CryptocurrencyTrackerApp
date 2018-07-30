package com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.CryptoCurrencyApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CryptoCurrencyApiClient {

    public static final String BASE_URL = "https://api.coinmarketcap.com/v1/ticker/";
    public  static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
