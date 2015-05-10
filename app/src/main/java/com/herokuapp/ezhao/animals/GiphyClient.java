package com.herokuapp.ezhao.animals;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GiphyClient {
    private final String API_BASE_URL = "http://api.giphy.com/v1/gifs";
    private final String API_RANDOM_PATH = "random";
    private final String API_KEY = "dc6zaTOxFJmzC";
    private AsyncHttpClient client;

    public GiphyClient() {
        this.client = new AsyncHttpClient();
    }

    private String getUrl(String path) {
        return API_BASE_URL + "/" + path;
    }

    public void getRandom(String tag, JsonHttpResponseHandler handler) {
        String url = getUrl(API_RANDOM_PATH);

        String new_tag = tag.replace(" ", "+");

        RequestParams params = new RequestParams();
        params.add("tag", new_tag);
        params.add("api_key", API_KEY);

        client.get(url, params, handler);
    }
}
