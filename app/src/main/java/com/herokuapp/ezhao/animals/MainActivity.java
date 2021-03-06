package com.herokuapp.ezhao.animals;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends FragmentActivity {
    @InjectView(R.id.givGiphy) GifImageView givGiphy;
    @InjectView(R.id.pbLoading) ProgressBar pbLoading;
    GiphyClient giphyClient;
    final String CUTE_ANIMAL = "cute animals";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        giphyClient = new GiphyClient();
        nextImage();
    }

    private void setImage(String url) {
        new GifDownloadTask(givGiphy, pbLoading).execute(url);
    }

    @OnClick(R.id.givGiphy)
    public void nextImage() {
        pbLoading.setVisibility(ProgressBar.VISIBLE);
        giphyClient.getRandom(CUTE_ANIMAL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    String imageUrl = data.getString("image_url");
                    setImage(imageUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
