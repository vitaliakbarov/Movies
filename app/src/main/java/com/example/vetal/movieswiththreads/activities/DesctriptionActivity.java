package com.example.vetal.movieswiththreads.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vetal.movieswiththreads.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DesctriptionActivity extends Activity implements View.OnClickListener {

    private String imageURL;
    private ImageView posterImageView;
    private TextView movieNameTV;
    private TextView yearTV;
    private TextView actorsTV;
    private TextView descriptionTV;
    private TextView currentRating;
    private TextView seeTrailer;
    private LinearLayout layout;
    private Button rateBtn;
    private CheckBox cb = null;
    private String myRate ;
    private DatabaseReference mDatabase;
    private String androidId;
    private String movieName;
    private ProgressDialog progressDialog;

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
        currentRating = (TextView)findViewById(R.id.current_rating_imdb);
        seeTrailer = (TextView)findViewById(R.id.see_trailer);
        seeTrailer.setOnClickListener(this);
        layout = (LinearLayout) findViewById(R.id.check_box_layout);
        rateBtn = (Button)findViewById(R.id.rate_button);

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        for (int i = 1; i <= 10 ;i++){
            CheckBox cb = (CheckBox) layout.findViewWithTag(i + "");
            cb.setOnClickListener(this);
        }


        init();
        checkYourRating();

    }



    private void init()
    {

        imageURL = getIntent().getStringExtra(ResultsActivity.POSTER);
        //
        Picasso.with(this).load(imageURL).into(posterImageView);
        //new ImageDownloadThread(imageURL, posterImageView).start();
        movieNameTV.setText(getIntent().getStringExtra(ResultsActivity.TITLE));
        yearTV.setText(getIntent().getStringExtra(ResultsActivity.YEAR));
        actorsTV.setText(getIntent().getStringExtra(ResultsActivity.ACTORS));
        descriptionTV.setText(getIntent().getStringExtra(ResultsActivity.PLOT));
        //Log.d("OBJ" , "put  " + getIntent().getStringExtra(ResultsActivity.CURRENT_RATING) );
        currentRating.setText("IMDB Rating: " + getIntent().getStringExtra(ResultsActivity.CURRENT_RATING));
        movieName = movieNameTV.getText().toString();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {  // rating stars clicked

        if(view == seeTrailer){
            Intent intent = new Intent(this,SeeTrailer.class);
            intent.putExtra("MOVIE_NAME", movieNameTV.getText()) ;
            startActivity(intent);

        }else {

            int index = Integer.parseInt(view.getTag().toString());
            myRate = view.getTag().toString();
            //Toast.makeText(this, view.getTag().toString(), Toast.LENGTH_SHORT).show();
            for (int i = 1; i <= 10; i++){

                cb = (CheckBox) layout.findViewWithTag(i + "");

                if (i <= index){
                    cb.setChecked(true);
                }else{
                    cb.setChecked(false);
                }

            }
        }



    }

    public void rateClicked(View view) {
        Toast.makeText(DesctriptionActivity.this, "Your raiting was send", Toast.LENGTH_SHORT).show();
        rateBtn.setClickable(false);
             for (int i = 1; i <= 10; i++) {

                cb = (CheckBox) layout.findViewWithTag(i + "");
                cb.setClickable(false);
             }
            sendRateToDB();
        }

    private void sendRateToDB() {


        mDatabase.child("myRates").child(androidId).child(movieNameTV.getText().toString()).setValue(myRate);
    }
    private void checkYourRating() {

        Query myRate = mDatabase.child("myRates").child(androidId).child(movieName);
        myRate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String rate = (String) dataSnapshot.getValue();
                Log.d("RATE","rating" +  dataSnapshot);
                Log.d("RATE","Yourrating" +  rate);

                if(rate != null){
                    rateBtn.setVisibility(View.GONE);
                    int r = Integer.parseInt(rate);
                    for (int i = 1; i <= 10; i++){

                        cb = (CheckBox) layout.findViewWithTag(i + "");
                        cb.setClickable(false);

                        if (i <= r){
                            cb.setChecked(true);

                        }else{
                            cb.setChecked(false);
                        }

                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
