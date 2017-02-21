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
import com.example.vetal.movieswiththreads.classes.MySearches;

import java.util.ArrayList;

/**
 * Created by vitaliakbarov on 20/02/2017.
 */

public class MySearchesAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<MySearches> list;

    public MySearchesAdapter(Context context, ArrayList<MySearches> list) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
    }


    private class ViewHolder {
        TextView title;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MySearchesAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new MySearchesAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.searched_item_list, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.searched_item_list_text_view);
            convertView.setTag(viewHolder);

        } else { // recycled
            viewHolder = (MySearchesAdapter.ViewHolder) convertView.getTag();
        }
        // insert new data
        viewHolder.title.setText(list.get(position).getSearchFor());




        if(list.isEmpty())
        {
            Log.d("ERROR", " empty list");
        }

        return convertView;
    }
}
