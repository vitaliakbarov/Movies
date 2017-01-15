package com.example.vetal.movieswiththreads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ResultsActivity extends Activity implements DownloadComplitedListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private DownloadThread thread;
    private DescriptionMovieThread descriptionThread;
    private MoviesAdapter adapter;
    private String omdbId;
    private Movie movie;
    private ArrayList<Movie> movies = new ArrayList<>();
    private Intent intent;

    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    public static final String YEAR = "year";
    public static final String PLOT = "plot";
    public static final String ACTORS = "actors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        listView = (ListView)findViewById(R.id.listView) ;
        RelativeLayout result = (RelativeLayout)findViewById(R.id.result_activity);
        result.getBackground().setAlpha(90);
        listView.setOnItemClickListener(this);

        String movieName = getIntent().getStringExtra(MainActivity.MOVIE_NAME);
        String  goTo = getIntent().getStringExtra(MainActivity.GO_TO);

        if(goTo.contentEquals("1")) { // most popular this year pressed
            MostPopularThread mostPopularThread = new MostPopularThread(this,this,1);
            mostPopularThread.start();
        }

        else if (goTo.contentEquals("2")){ // most popular ever pressed

            MostPopularThread mostPopularThread = new MostPopularThread(this,this,2);
            mostPopularThread.start();
        }

        else if(goTo.contentEquals("3")){ // search pressed
            String link = "http://www.omdbapi.com/?s=" + movieName + "&r=json";

            thread = new DownloadThread(this, link, this);
            thread.start();
        }
    }
    // displays the list item
    public void displayList(ArrayList<Movie> moviesList){
        adapter = new MoviesAdapter(this,moviesList);
        listView.setAdapter(adapter);

    }
    // clicked on one of the movie item
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        intent = new Intent(this,DesctriptionActivity.class);
        omdbId = null;
        movie = (Movie) adapterView.getItemAtPosition(position);
        omdbId = movie.getId();
        String link = "http://www.omdbapi.com/?i="+omdbId+"&plot=full&r=json";
        descriptionThread = new DescriptionMovieThread(this,link);
        descriptionThread.start();
        try {
            descriptionThread.join();  // wait to finish the thread
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("ERROR", "result activity int");
        }
        insertData();
    }

    private void insertData(){  //insert all data to DescriptionActivity
        intent.putExtra(POSTER, movie.getPoster());
        intent.putExtra(TITLE, movie.getTitle());
        intent.putExtra(YEAR, movie.getYear());
        intent.putExtra(ACTORS, descriptionThread.getActors());
        intent.putExtra(PLOT, descriptionThread.getPlot());
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
