package com.example.dogsapp.View;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dogsapp.Database.DogDao;
import com.example.dogsapp.Database.DogDatabase;
import com.example.dogsapp.Model.Dog;
import com.example.dogsapp.R;
import com.example.dogsapp.ViewModel.DogApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class listFragment extends Fragment {
    private RecyclerView dogsview;
    private ArrayList<Dog> doglist;
    private RecyclerAdapter adapter;
    private DogDatabase database;
    private DogDao dao;
    private DogApiService apiService;
    private SwipeRefreshLayout pullToRefresh;
    private SearchView searchView;
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
        doglist = new ArrayList<>();
        new loadDog().execute();
        adapter = new RecyclerAdapter(doglist, getContext());
        dogsview.setAdapter(adapter);
        pullToRefresh = view.findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        database = DogDatabase.getInstance(getContext());
                        dao =database.dogDao();
                        dao.deleteAll();
                        apiService = new DogApiService();
                        apiService.getAllDogs().subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<List<Dog>>() {
                                    @Override
                                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Dog> dogs) {
                                        for(Dog d:dogs){
                                            AsyncTask.execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dao.insertAll(d);
                                                }
                                            });

                                        }

                                    }
                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                    }
                                });
                    }
                });
                pullToRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        });
        searchView = view.findViewById(R.id.sv_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return true;
            }
        });
    }

    class loadDog extends AsyncTask<Void, Void, ArrayList<Dog>> {

        @Override
        protected void onPostExecute(ArrayList<Dog> temp) {
            doglist.addAll(temp);
        }

        @Override
        protected ArrayList<Dog> doInBackground(Void... voids) {
            database = DogDatabase.getInstance(getContext());
            dao = database.dogDao();
            ArrayList<Dog> temp = new ArrayList<>(dao.getAll());
            return temp;
        }
    }

}




