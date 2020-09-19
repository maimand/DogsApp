package com.example.dogsapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dog implements Serializable {
    @SerializedName("bred_for")
    private  String bred_for;
    @SerializedName("breed_group")
    private  String breed_group;
    @SerializedName("id")
    private  int id;
    @SerializedName("life_span")
    private  String life_span;
    @SerializedName("name")
    private  String name;
    @SerializedName("origin")
    private  String origin;
    @SerializedName("temperament")
    private  String temperament;
    @SerializedName("url")
    private  String url;
    @SerializedName("height")
    private Metric height;
    @SerializedName("weight")
    private Metric weight;

    public Dog(String bred_for, String breed_group, int id, String life_span, String name, String origin, String temperament, String url, Metric height, Metric weight) {
        this.bred_for = bred_for;
        this.breed_group = breed_group;
        this.id = id;
        this.life_span = life_span;
        this.name = name;
        this.origin = origin;
        this.temperament = temperament;
        this.url = url;
        this.height = height;
        this.weight = weight;
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

    public Metric getHeight() {
        return height;
    }

    public void setHeight(Metric height) {
        this.height = height;
    }

    public Metric getWeight() {
        return weight;
    }

    public void setWeight(Metric weight) {
        this.weight = weight;
    }



    public class Metric{
        private String imprerial;
        private String metric;

        public String getImprerial() {
            return imprerial;
        }

        public String getMetric() {
            return metric;
        }
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