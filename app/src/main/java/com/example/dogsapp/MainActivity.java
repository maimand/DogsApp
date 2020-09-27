package com.example.dogsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dogsapp.Database.DogDao;
import com.example.dogsapp.Database.DogDatabase;
import com.example.dogsapp.Model.Dog;
import com.example.dogsapp.View.RecyclerAdapter;
import com.example.dogsapp.ViewModel.DogApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private DogApiService apiService;
    private DogDatabase database;
    private DogDao dogDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = new DogApiService();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                database = DogDatabase.getInstance(getApplicationContext());
                dogDao =database.dogDao();
                if(dogDao.getAll().size() == 0){
                    apiService.getAllDogs().subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<List<Dog>>() {
                                @Override
                                public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Dog> dogs) {
                                    for(Dog d:dogs){
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                dogDao.insertAll(d);
                                            }
                                        });

                                    }
                                }
                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                }
                            });

                }
            }
        });


    }
}
