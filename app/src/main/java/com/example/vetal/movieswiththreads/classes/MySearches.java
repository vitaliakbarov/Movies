package com.example.vetal.movieswiththreads.classes;

import java.io.Serializable;

/**
 * Created by vitaliakbarov on 10/02/2017.
 */

public class MySearches implements Serializable {

    private String searchFor;

    public MySearches(){

    }
    public MySearches(String searchFor){
        this.searchFor = searchFor;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }
}
