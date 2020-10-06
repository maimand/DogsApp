package com.example.dogsapp.View;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
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

import static android.content.ContentValues.TAG;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dogsview = view.findViewById(R.id.rv_list);
        dogsview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        doglist = new ArrayList<>();
        new loadDog().execute();
        Log.d(TAG, "onViewCreated: "+ doglist.size());

//        Log.d(TAG, "onViewCreated: "+ adapter.getItemCount());
        pullToRefresh = view.findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiService = new DogApiService();
                DisposableSingleObserver<List<Dog>> disposableSingleObserver = apiService.getAllDogs().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Dog>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Dog> dogs) {
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        database = DogDatabase.getInstance(getContext());
                                        dao = database.dogDao();
                                        if (dogs.size() != dao.getAll().size()) {
                                            dao.deleteAll();
                                            for (Dog d : dogs) {
                                                AsyncTask.execute(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dao.insertAll(d);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            };


                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e){

                                };


            });
                pullToRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        });
        searchView = view.findViewById(R.id.sv_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                adapter.filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return false;
            }
        });
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.background));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.showMenu(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(dogsview);


        dogsview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                adapter.closeMenu();
            }
        });
    }


    class loadDog extends AsyncTask<Void, Void, ArrayList<Dog>> {

        @Override
        protected void onPostExecute(ArrayList<Dog> temp) {
            doglist.addAll(temp);
            adapter = new RecyclerAdapter(doglist, getContext());
            dogsview.setAdapter(adapter);
            Log.d(TAG, "onPostExecute: "+ adapter.getItemCount());
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




