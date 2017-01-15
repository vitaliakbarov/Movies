package com.example.vetal.movieswiththreads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DescriptionMovieThread extends Thread {

    private String link;
    private String actors;
    private String plot;
    private Movie movie;
    private Context context;
    private Handler handler;
    private DownloadThread downloadThread = new DownloadThread();

    public DescriptionMovieThread(Context context, String link)  //constructor
    {
        this.context = context;
        this.link = link;
        handler = new Handler();
    }

    @Override
    public void run() {  // start thread
        try {  // connect to url
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String data = "";
            String line;
            while((line = reader.readLine()) != null) {  // download the data
                data += line;
            }
            JSONObject object = new JSONObject(data);
            // init movie object
            movie = new Movie(object);
            plot = movie.getPlot();
            actors = movie.getActors();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("ERROR", "descrioption");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR", "descrioption2");

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast toast= Toast.makeText(context,"No INTERNET", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 350);
                    toast.show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("ERROR", "descrioption3");
            downloadThread.sendError("Movie was not found!!!");
        }
    }
    // getters
    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }
}
