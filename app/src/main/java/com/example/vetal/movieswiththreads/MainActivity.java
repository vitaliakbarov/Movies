package com.example.vetal.movieswiththreads;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {


    private Button searchButton;
    private EditText  editTextSearched;
    private TextView mostPopularThisYear;
    private TextView mostPopularEver;
    public static final String MOVIE_NAME = "movie";
    public static final String GO_TO = "goTo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    // init all we need
    private void init() {
        LinearLayout main = (LinearLayout) findViewById(R.id.main_activity);
        main.getBackground().setAlpha(90);
        searchButton = (Button)findViewById(R.id.search_button_main_layout);
        editTextSearched = (EditText)findViewById(R.id.edit_text_main_layout);
        searchButton.setOnClickListener(this);
        mostPopularThisYear = (TextView)findViewById(R.id.mostPopularThisYearTV);
        mostPopularThisYear.setOnClickListener(this);
        mostPopularEver = (TextView)findViewById(R.id.mostPopularEverTV);
        mostPopularEver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        checkClicked(view);

    }

    // look for what we clicked
    private void checkClicked(View view) {
        if(view == mostPopularThisYear){  // most popular this year clicked

            Intent intent = new Intent(this,ResultsActivity.class);
            intent.putExtra(GO_TO,"1");
            startActivity(intent);
        }

        if(view == mostPopularEver){  // most popular ever clicked
            Intent intent = new Intent(this,ResultsActivity.class);
            intent.putExtra(GO_TO,"2");
            startActivity(intent);
        }

        if(view == searchButton) {  // search clicked
            String movieName = editTextSearched.getText().toString().trim();

            if(movieName.contains(" ")){  // apply space
                movieName =  movieName.replace(' ','+');
            }

            if(validate(movieName)) {
                Intent intent = new Intent(this, ResultsActivity.class);
                intent.putExtra(MOVIE_NAME, movieName);
                intent.putExtra(GO_TO,"3");
                startActivity(intent);
                if( intent.getStringExtra("ERROR") == "ss"){
                    Toast.makeText(MainActivity.this, "No Movies", Toast.LENGTH_SHORT).show();
                }

            }else {

            }

        }

    }

    private boolean validate(String str)
    {
        if(str.isEmpty()){ // validate empty string
            editTextSearched.setHint("Enter again");
            editTextSearched.setError("Text filed is empty!!!");
            return false;
        }
        return true;
    }

}
