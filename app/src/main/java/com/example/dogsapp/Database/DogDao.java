package com.example.dogsapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dogsapp.Model.Dog;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface DogDao {
    @Query("SELECT * FROM DOGS")
    List<Dog> getAll();


    @Insert
    void insertAll(Dog... dogs);

    @Query("DELETE FROM DOGS")
    void deleteAll();

}
