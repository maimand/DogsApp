package com.example.dogsapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dogsapp.Model.Dog;
@Database( entities = Dog.class, version = 1)
public abstract class DogDatabase extends RoomDatabase{

        public abstract DogDao dogDao();
        private static DogDatabase instance;

        public static DogDatabase getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, DogDatabase.class, "database-name").build();
            }
            return instance;
        }
}

