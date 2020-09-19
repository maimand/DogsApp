package com.example.dogsapp.ViewModel;

import com.example.dogsapp.Model.Dog;
import com.example.dogsapp.Model.DogApi;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogApiService {
    private static final String BASE_URL="https://raw.githubusercontent.com/";
    private DogApi api;
    public DogApiService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(DogApi.class);
    }
    public Single<List<Dog>> getAllDogs(){
        return api.getAllDogs();
    }
}
