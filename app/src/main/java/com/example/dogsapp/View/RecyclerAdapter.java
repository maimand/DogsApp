package com.example.dogsapp.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogsapp.Model.Dog;
import com.example.dogsapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Dog> dogs, copieddogs;
    private Context context;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public RecyclerAdapter(ArrayList<Dog> dogs, Context context) {
        this.dogs = dogs;
        this.context = context;
        copieddogs = new ArrayList<>(dogs);
        Log.e("log", dogs.size()+"");

    }

    @Override
    public int getItemViewType(int position) {
        if(dogs.get(position).isShow()  ){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }
    public void showMenu(int position) {
        for(int i=0; i<dogs.size(); i++){
            dogs.get(i).setShow(false);
        }
        dogs.get(position).setShow(true);
        notifyDataSetChanged();
    }
    public boolean isMenuShown() {
        for(int i=0; i<dogs.size(); i++){
            if(dogs.get(i).isShow()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<dogs.size(); i++){
            dogs.get(i).setShow(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
//        MyViewHolder myViewHolder =new MyViewHolder(view);
        View v;
        if(viewType==SHOW_MENU){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu, parent, false);
            return new MenuViewHolder(v);
        }else{
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            return new MyViewHolder(v);
        }
//        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).tv_name.setText(dogs.get(position).getName());
            ((MyViewHolder) holder).tv_bred_for.setText(dogs.get(position).getBred_for());
            ((MyViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(dogs.get(position).getUrl())
                    .fit()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(((MyViewHolder) holder).image, new Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.get()
                                    .load(dogs.get(position).getUrl())
                                    .fit()
                                    .error(R.drawable.ic_android_black_24dp)
                                    .into(((MyViewHolder) holder).image, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            ((MyViewHolder) holder).progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError(Exception e) {

                                        }
                                    });
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.v("Picasso","Could not fetch image");
                            Picasso.get()
                                    .load(dogs.get(position).getUrl())
                                    .fit()
                                    .error(R.drawable.ic_android_black_24dp)
                                    .into(((MyViewHolder) holder).image, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            ((MyViewHolder) holder).progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError(Exception e) {

                                        }
                                    });
                        }
                    });
        }
        if(holder instanceof MenuViewHolder){
            ((MenuViewHolder) holder).menu_name.setText(dogs.get(position).getName());
            ((MenuViewHolder) holder).menu_origin.setText(dogs.get(position).getOrigin());
            ((MenuViewHolder) holder).menu_group.setText(dogs.get(position).getBreed_group());
            ((MenuViewHolder) holder).menu_bred_for.setText(dogs.get(position).getBred_for());
        }


    }



    @Override
    public int getItemCount() {
        return dogs.size();
    }
    public void filter(String text){

        Log.d("debug", String.valueOf(copieddogs.size()));
        if(text.isEmpty()){
            dogs.clear();
            dogs.addAll(copieddogs);


        }else {
            ArrayList<Dog> result = new ArrayList<>();
            text = text.toLowerCase();
            for(Dog dog:dogs ){
                if(dog.getName().toLowerCase().contains(text)){
                    result.add(dog);
                }
            }
            dogs.clear();
            dogs.addAll(result);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_bred_for;
        ImageView image;
        CardView cardView;
        ProgressBar progressBar;
        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_bred_for = view.findViewById((R.id.tv_bred_for));
            image = view.findViewById(R.id.iv_image);
            progressBar = view.findViewById(R.id.progressBar);
            cardView = view.findViewById(R.id.cv_item);
        }
    }
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView menu_name, menu_bred_for, menu_group, menu_origin;
        public MenuViewHolder(View view){
            super(view);
            menu_bred_for = view.findViewById(R.id.menu_bred_for);
            menu_name = view.findViewById(R.id.menu_name);
            menu_group = view.findViewById(R.id.menu_group);
            menu_origin = view.findViewById(R.id.menu_origin);
        }
    }

}
