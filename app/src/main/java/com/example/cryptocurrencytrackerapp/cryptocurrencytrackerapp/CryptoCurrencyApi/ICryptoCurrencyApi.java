package com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.CryptoCurrencyApi;

import com.example.cryptocurrencytrackerapp.cryptocurrencytrackerapp.Model.CryptoCurrencyModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICryptoCurrencyApi {



    //@GET("https://api.coinmarketcap.com/v1/ticker/{name}/")
    @GET("https://api.coinmarketcap.com/v1/ticker/")
    Call<List<CryptoCurrencyModel>> getAll();

    @GET("https://api.coinmarketcap.com/v1/ticker/{name}/")
    Call<List<CryptoCurrencyModel>> getSearch(@Path("name") String name);




}
