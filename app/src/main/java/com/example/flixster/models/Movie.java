package com.example.flixster.models;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String bdPath;
    String posterPath;
    String title;
    String description;
    String releaseDate;
    Double voteAverage;
    int id;

    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        bdPath= jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        description = jsonObject.getString("overview");
        releaseDate = jsonObject.getString("release_date");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");

    }
    public static List<Movie> fromJsonArray(JSONArray movieJson) throws JSONException {
        List<Movie> l=new ArrayList<Movie>();
        for(int i = 0; i<movieJson.length();i++){
            l.add(new Movie(movieJson.getJSONObject(i)));
        }
        return l;
    }
    public String getPosterPath() {
        if (posterPath=="null"){
            return "";
        }
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);

    }
    public String getBdPath() {
        if (bdPath=="null"){
            return "";
        }
        return String.format("https://image.tmdb.org/t/p/w342/%s",bdPath);
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public Double getVoteAverage() {
        return voteAverage;
    }
    public int getId() {
        return id;
    }


}

