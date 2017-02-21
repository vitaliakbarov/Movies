package com.example.vetal.movieswiththreads.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.vetal.movieswiththreads.R;
import com.example.vetal.movieswiththreads.classes.MyRates;
import com.example.vetal.movieswiththreads.threadsAndOther.MyRatesAdapter;

import java.util.ArrayList;

public class SeeMyRatings extends AppCompatActivity{

    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_ratings);
        LinearLayout main = (LinearLayout) findViewById(R.id.activity_see_my_ratings);
        main.getBackground().setAlpha(80);

        listView = (ListView)findViewById(R.id.my_ratings_list_view);

        ArrayList<MyRates> searches = (ArrayList<MyRates>) getIntent().getSerializableExtra("ARRAY");
//        String[] names = new String[searches.size()];
//        String[] rates = new String[searches.size()];
//
//        String[] fullTitle = new String[searches.size()];

        MyRatesAdapter adapter = new MyRatesAdapter(this,searches);
        listView.setAdapter(adapter);

    }
}
