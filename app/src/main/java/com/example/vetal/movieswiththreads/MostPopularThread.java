package com.example.vetal.movieswiththreads;

import android.content.Context;
import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MostPopularThread extends Thread {

    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;
    private String[] urlArray;
    private Handler handler;
    private DownloadComplitedListener activityy;

    // constructor and what array to init?
    public MostPopularThread(DownloadComplitedListener activity, Context context, int kindOfInit) {
        this.activityy = activity;
        this.context = context;
        handler = new Handler();
        if(kindOfInit == 1) {
            initArrayThisYear();
        }else if(kindOfInit == 2) {
            initArrayEver();
        }
    }



    @Override
    public void run() { // start thread
        for(int i = 0; i < urlArray.length; i++) {
            try {   // make list of popular movies
                URL url = new URL(urlArray[i]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String data = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    data += line;
                }
                convertStringToList(data);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showList();
    }

    public void convertStringToList(String jsonString) {

        try {
            JSONObject mainObject = new JSONObject(jsonString);
            movies.add(new Movie(mainObject));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showList(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                activityy.displayList(movies);
            }
        });
    }

    private void initArrayThisYear(){
         urlArray = new String[]{"http://www.omdbapi.com/?t=Now+You+See+Me+2&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Central+Intelligence&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Deadpool&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Captain+America%3A+Civil+War&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Batman+v+Superman%3A+Dawn+of+Justice&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Kung+Fu+Panda+3&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=X-Men%3A+Apocalypse+&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Sausage+Party&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Independence+Day%3A+Resurgence+&y=&plot=short&r=json",
                 "http://www.omdbapi.com/?t=Jason+Bourne&y=&plot=short&r=json"};
    }


    private void initArrayEver() {

        urlArray = new String[]{"http://www.omdbapi.com/?t=The+Godfather&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=+Raging+Bull+&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=Jurassic+World&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=Avatar&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=Titanic&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=pulp+fiction&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=casablanca&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=Harry+Potter+and+the+Deathly+Hallows+Part+2&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=Apocalypse+Now&y=&plot=short&r=json",
                "http://www.omdbapi.com/?t=Fight+Club&y=&plot=short&r=json"};
    }
}