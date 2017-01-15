package com.example.vetal.movieswiththreads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class ImageDownloadThread extends Thread{
    private String link;
    private ImageView imageView;
    private Handler handler;

    public ImageDownloadThread(String link, ImageView imageView) // constructor
    {
        this.link = link;
        this.imageView = imageView;
        handler = new Handler();
    }

    @Override
    public void run() {  // start thread
        try {  // connect url
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(in); // download the image
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("ERROR", "image download malf");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR", "image download io");
        }
    }
}
