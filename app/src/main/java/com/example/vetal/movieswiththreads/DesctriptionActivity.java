package com.example.vetal.movieswiththreads;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DesctriptionActivity extends Activity implements View.OnClickListener {

    private String imageURL;
    private ImageView posterImageView;
    private TextView movieNameTV;
    private TextView yearTV;
    private TextView actorsTV;
    private TextView descriptionTV;
    private LinearLayout layout;
    private Button rateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {  // load and init the page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desctription);

        ScrollView main = (ScrollView) findViewById(R.id.scrollView1);
        main.getBackground().setAlpha(90);

        posterImageView = (ImageView)findViewById(R.id.description_activity_poster);
        movieNameTV = (TextView)findViewById(R.id.description_activity_movie_name);
        yearTV = (TextView)findViewById(R.id.description_activity_year);
        actorsTV = (TextView)findViewById(R.id.description_activity_actors_text_view);
        descriptionTV = (TextView)findViewById(R.id.description_activity_movie_description_text_view);
        layout = (LinearLayout) findViewById(R.id.check_box_layout);
        rateBtn = (Button)findViewById(R.id.rate_button);


        for (int i = 1; i <= 5 ;i++){
            CheckBox cb = (CheckBox) layout.findViewWithTag(i + "");
            cb.setOnClickListener(this);
        }


        init();
    }

    private void init()
    {

        imageURL = getIntent().getStringExtra(ResultsActivity.POSTER);
        new ImageDownloadThread(imageURL, posterImageView).start();
        movieNameTV.setText(getIntent().getStringExtra(ResultsActivity.TITLE));
        yearTV.setText(getIntent().getStringExtra(ResultsActivity.YEAR));
        actorsTV.setText(getIntent().getStringExtra(ResultsActivity.ACTORS));
        descriptionTV.setText(getIntent().getStringExtra(ResultsActivity.PLOT));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {  // rating stars clicked

        CheckBox cb = null;
        int index = Integer.parseInt(view.getTag().toString());
        for (int i = 1; i <= 5; i++){

            cb = (CheckBox) layout.findViewWithTag(i + "");
            if (i <= index){
                cb.setChecked(true);
            }else{
                cb.setChecked(false);
            }
            cb.setClickable(false);
        }

    }

    public void rateClicked(View view) {
        Toast.makeText(DesctriptionActivity.this, "Your raiting was send", Toast.LENGTH_SHORT).show();
        rateBtn.setClickable(false);
    }
}
