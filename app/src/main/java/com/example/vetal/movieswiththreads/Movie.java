package com.example.vetal.movieswiththreads;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

    private String title;
    private String poster;
    private String year;
    private String id;
    private String plot;
    private String actors;
    private String link;

    public Movie(){ //constructor

    }

    public Movie(JSONObject object)  //constructor with object
    {
        try {
            title = object.getString("Title");
            year = object.getString("Year");
            poster = object.getString("Poster");
            id = object.getString("imdbID");
            plot = object.getString("Plot");
            actors = object.getString("Actors");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // getters and setters
    @Override
    public String toString() {
        return title + actors;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPoster() {
        return poster;
    }

    public String getId() {
        return id;
    }

    public String getPlot() {
        return plot;
    }

    public String getActors() {
        return actors;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
