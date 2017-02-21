package com.example.vetal.movieswiththreads.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.vetal.movieswiththreads.R;
import com.example.vetal.movieswiththreads.classes.MySearches;
import com.example.vetal.movieswiththreads.threadsAndOther.MySearchesAdapter;

import java.util.ArrayList;

import static com.example.vetal.movieswiththreads.activities.MainActivity.GO_TO;
import static com.example.vetal.movieswiththreads.activities.MainActivity.MOVIE_NAME;

public class SeeMySearches extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_searches);
        LinearLayout main = (LinearLayout) findViewById(R.id.activity_see_my_searches);
        main.getBackground().setAlpha(80);
        listView = (ListView)findViewById(R.id.my_searches_list_view);
        listView.setOnItemClickListener(this);

        ArrayList<MySearches> searches = (ArrayList<MySearches>) getIntent().getSerializableExtra("ARRAY");
        MySearchesAdapter adapter = new MySearchesAdapter(this,searches);
        listView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       // goes to result activity with the searched item
        Intent intent = new Intent(this, ResultsActivity.class);
        MySearches searches= (MySearches) adapterView.getItemAtPosition(i);
        intent.putExtra(MOVIE_NAME, searches.getSearchFor());
        intent.putExtra(GO_TO,"3");
        startActivity(intent);
    }
}
