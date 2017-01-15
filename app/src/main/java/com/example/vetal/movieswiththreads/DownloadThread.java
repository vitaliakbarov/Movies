package com.example.vetal.movieswiththreads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.logging.LogRecord;


public class DownloadThread extends Thread {

    private String link;
    private Context context;
    private DownloadComplitedListener activity;
    private Handler handler;
    private EditText movieName = null;

    public DownloadThread(){  //constructor

    }

    public DownloadThread(DownloadComplitedListener activity,  String link, Context context)  //constructor
    {
        this.link = link;
        this.activity = activity;
        this.context = context;
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
            while((line = reader.readLine()) != null) { // download data
                data += line;
            }
            convertStringToList(data);
           // Log.d("TAG","data =" + data);

        } catch (MalformedURLException e) {
            Log.d("ERROR", "download thread malfo");
            e.printStackTrace();
            Log.d("ERROR", "download thread prin");
        } catch (IOException e) {
            e.printStackTrace();
            sendError("NO Internet Conection");
        }
    }
    // make list of Movie
    public void convertStringToList(String jsonString)
    {
       final ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject mainObject = new JSONObject(jsonString);
            JSONArray arr = mainObject.getJSONArray("Search");
            for(int i = 0; i < arr.length(); i++)
            {
                movies.add(new Movie(arr.getJSONObject(i)));
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    activity.displayList(movies);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
           sendError("Movies were not found!!!");
        }
    }

    public void sendError(final String messege){
        ((Activity)context).finish();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast= Toast.makeText(context, messege, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 350);
                toast.show();

            }
        });
    }
}
