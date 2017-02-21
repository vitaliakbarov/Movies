package com.example.vetal.movieswiththreads.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vetal.movieswiththreads.R;
import com.example.vetal.movieswiththreads.classes.MyRates;
import com.example.vetal.movieswiththreads.classes.MySearches;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAppActivities extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String androidId;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private TextView numberOfRates;
    private TextView numberOfSearches;
    private TextView seeMySearches;
    private TextView seeMyRates;
    private ArrayList<MyRates> rates = new ArrayList<>();
    private ArrayList<MySearches> searches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_app_activities);

        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_my_app_activities);
        layout.getBackground().setAlpha(90);
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        numberOfRates = (TextView)findViewById(R.id.rate_text_view);
        numberOfSearches = (TextView)findViewById(R.id.searched_text_view);
        seeMySearches = (TextView)findViewById(R.id.see_my_searches);
        seeMySearches.setOnClickListener(this);
        seeMyRates = (TextView)findViewById(R.id.see_my_rates);
        seeMyRates.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        getData();



    }

    private void getData() {
        Query myRate = mDatabase.child("myRates").child(androidId);
        myRate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Log.d("SNAP", String.valueOf(dataSnapshot));
                for (DataSnapshot userSnap: dataSnapshot.getChildren()){
                    String movieName = (String)userSnap.getKey();
                    String movieRating = (String)userSnap.getValue();
                    rates.add(new MyRates(movieName,movieRating));
                    Log.d("SNAP", movieName.toString());
                }

                for(int i = 0; i < rates.size(); i++){
                    Log.d("SNAP", rates.get(i).getMovieName() +  " + " + rates.get(i).getMyMovieRate());

                }
                numberOfRates.setText("You rate " + rates.size() + " movies");

                checkSearched();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void checkSearched(){
        Query mySearches = mDatabase.child("mySearches").child(androidId);
        mySearches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    String searchedTitle = (String)userSnapshot.getKey();
                    searches.add(new MySearches(searchedTitle));
                }
                numberOfSearches.setText("You search for " + searches.size() + " movies");

                for(int i = 0; i < searches.size(); i++){
                    Log.d("SNAP","searched " +  searches.get(i).getSearchFor() );

                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == seeMySearches){
            Intent intent = new Intent(this,SeeMySearches.class);
            intent.putExtra("ARRAY", searches);
            startActivity(intent);
        }
        if (view == seeMyRates){
            Intent intent = new Intent(this,SeeMyRatings.class);
            intent.putExtra("ARRAY", rates);
            startActivity(intent);
        }

    }
}