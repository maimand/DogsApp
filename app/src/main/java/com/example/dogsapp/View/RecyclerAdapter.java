package com.example.dogsapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogsapp.Model.Dog;
import com.example.dogsapp.R;
import com.example.dogsapp.ViewModel.ImageDownload;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private static ArrayList<Dog> dogs;
    private Context context;

    public RecyclerAdapter(ArrayList<Dog> dogs, Context context) {
        this.dogs = dogs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        MyViewHolder myViewHolder =new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(dogs.get(position).getName());
        holder.tv_bred_for.setText(dogs.get(position).getBred_for());
        new ImageDownload(holder.image).execute(dogs.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_bred_for;
        ImageView image;
        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_bred_for = view.findViewById((R.id.tv_bred_for));
            image = view.findViewById(R.id.iv_image);

        }
    }
}
