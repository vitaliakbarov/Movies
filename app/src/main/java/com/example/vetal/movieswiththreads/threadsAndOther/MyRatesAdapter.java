package com.example.vetal.movieswiththreads.threadsAndOther;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vetal.movieswiththreads.R;
import com.example.vetal.movieswiththreads.classes.MyRates;

import java.util.ArrayList;

/**
 * Created by vitaliakbarov on 20/02/2017.
 */

public class MyRatesAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<MyRates> list;

    public MyRatesAdapter(Context context, ArrayList<MyRates> list) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyRatesAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new MyRatesAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.rate_item_list, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.rated_item_list_text_view);
            convertView.setTag(viewHolder);

        } else { // recycled
            viewHolder = (MyRatesAdapter.ViewHolder) convertView.getTag();
        }
        // insert new data
        viewHolder.title.setText(list.get(position).getMovieName() + " : " + "Your rate was " + list.get(position).getMyMovieRate() + " stars");


        if (list.isEmpty()) {
            Log.d("ERROR", " empty list");
        }

        return convertView;
    }

    private class ViewHolder {
        TextView title;
    }
}
