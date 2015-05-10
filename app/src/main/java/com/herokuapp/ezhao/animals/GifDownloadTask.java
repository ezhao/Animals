package com.herokuapp.ezhao.animals;

import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GifDownloadTask extends AsyncTask<String, Void, GifDrawable> {
    GifImageView gifImageView;

    public GifDownloadTask(GifImageView gifImageView) {
        this.gifImageView = gifImageView;
    }

    protected GifDrawable doInBackground(String... addresses) {
        GifDrawable gifDrawable = null;
        try {
            URL url = new URL(addresses[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] rawGifBytes = buffer.toByteArray();
            gifDrawable = new GifDrawable(rawGifBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gifDrawable;
    }

    // Fires after the task is completed, displaying the bitmap into the ImageView
    @Override
    protected void onPostExecute(GifDrawable result) {
        // Set bitmap image for the result
        gifImageView.setImageDrawable(result);
    }
}
