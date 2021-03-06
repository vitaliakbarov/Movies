package com.example.vetal.movieswiththreads.threadsAndOther;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vetal.movieswiththreads.R;
import com.example.vetal.movieswiththreads.classes.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// helps to work with my personal list item
public class MoviesAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Movie> movies;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.list_item, movies);
        this.context = context;
        this.movies = movies;
    }

    private class ViewHolder {
        ImageView poster;
        TextView title;
        TextView year;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        String imageUrl;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_list_text_view);
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.list_item_image_view);
            viewHolder.year = (TextView) convertView.findViewById(R.id.list_item_year_text_view);

            convertView.setTag(viewHolder);

        } else { // recycled
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // insert new data
        viewHolder.title.setText(movies.get(position).getTitle());
        viewHolder.year.setText(movies.get(position).getYear());

        Picasso.with(context).load(movies.get(position).getPoster()).resize(50,50).centerCrop().into(viewHolder.poster);
        //new ImageDownloadThread(movies.get(position).getPoster(),viewHolder.poster).start();

        if(movies.isEmpty())
        {
            Log.d("ERROR", " empty list");
        }
        return convertView;
    }
}
