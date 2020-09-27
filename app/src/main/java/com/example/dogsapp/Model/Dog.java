package com.example.dogsapp.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "DOGS")
public class Dog implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private  int id;
    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    private  String bred_for;
    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    private  String breed_group;
    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    private  String life_span;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private  String name;
    @ColumnInfo(name = "origin")
    @SerializedName("origin")
    private  String origin;
    @ColumnInfo(name = "temperament")
    @SerializedName("temperament")
    private  String temperament;
    @ColumnInfo(name = "url")
    @SerializedName("url")
    private  String url;

    public Dog(String bred_for, String breed_group, int id, String life_span, String name, String origin, String temperament, String url) {
        this.bred_for = bred_for;
        this.breed_group = breed_group;
        this.id = id;
        this.life_span = life_span;
        this.name = name;
        this.origin = origin;
        this.temperament = temperament;
        this.url = url;
    }

    public String getBred_for() {
        return bred_for;
    }

    public void setBred_for(String bred_for) {
        this.bred_for = bred_for;
    }

    public String getBreed_group() {
        return breed_group;
    }

    public void setBreed_group(String breed_group) {
        this.breed_group = breed_group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLife_span() {
        return life_span;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}


// {
//         "bred_for": "Small rodent hunting, lapdog",
//         "breed_group": "Toy",
//         "height": {
//         "imperial": "9 - 11.5",
//         "metric": "23 - 29"
//         },
//         "id": 1,
//         "life_span": "10 - 12 years",
//         "name": "Affenpinscher",
//         "origin": "Germany, France",
//         "temperament": "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving",
//         "weight": {
//         "imperial": "6 - 13",
//         "metric": "3 - 6"
//         },
//         "url": "https://raw.githubusercontent.com/DevTides/DogsApi/master/1.jpg"
//         },