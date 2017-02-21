package com.example.vetal.movieswiththreads.threadsAndOther;

import com.example.vetal.movieswiththreads.classes.Movie;

import java.util.ArrayList;

public interface DownloadComplitedListener {  // interface to display the listView with the results
                                                // class that has this methood must to use the methood

    public void displayList(ArrayList<Movie> moviesList);
}
