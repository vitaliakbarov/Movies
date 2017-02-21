package com.example.vetal.movieswiththreads.classes;

import java.io.Serializable;

/**
 * Created by vitaliakbarov on 10/02/2017.
 */

public class MyRates implements Serializable {

    private String movieName;
    private String myMovieRate;

    public MyRates(){

    }

    public MyRates(String movieName, String myMovieRate){

        this.movieName = movieName;
        this.myMovieRate = myMovieRate;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMyMovieRate() {
        return myMovieRate;
    }

    public void setMyMovieRate(String myMovieRate) {
        this.myMovieRate = myMovieRate;
    }
}
