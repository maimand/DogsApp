package com.example.dogsapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dogsapp.MainActivity;
import com.example.dogsapp.Model.Dog;
import com.example.dogsapp.R;
import com.example.dogsapp.ViewModel.DogApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class listFragment extends Fragment {
    private RecyclerView dogsview;
    private ArrayList<Dog> doglist;
    RecyclerAdapter adapter;
    private DogApiService apiService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dogsview = view.findViewById(R.id.rv_list);
        dogsview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        apiService = new DogApiService();
        doglist = new ArrayList<>();
        apiService.getAllDogs().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Dog>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Dog> dogs) {
                        Log.d("debug", "success" );
//                        doglist = new ArrayList<>();
                        doglist.addAll(dogs);
//                        for(Dog d: doglist){
//                            Log.d("debug", d.getName());
//                        }
                        adapter = new RecyclerAdapter(doglist, getContext());

                        dogsview.setAdapter(adapter);



                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    }
                });

//        for(Dog d: doglist){
//            Log.d("debug", d.getName());
//        }

//        adapter = new RecyclerAdapter(doglist, getContext());
//
//        dogsview.setAdapter(adapter);
    }
}