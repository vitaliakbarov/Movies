package com.example.vetal.movieswiththreads.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.vetal.movieswiththreads.R;

public class SeeTrailer extends AppCompatActivity {
    private String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_trailer);

        movieName = getIntent().getStringExtra("MOVIE_NAME");

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("https://www.youtube.com/results?search_query=" + movieName + " trailer");
        finish();
    }
}
