package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class movietrailer extends YouTubeBaseActivity {
    public static final String url = "https://api.themoviedb.org/3/movie/*/videos?api_key=5a4f96712b96651b6e97151cc6e8e0a8&language=en-US";
    public static String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movietrailer);

        final int movieId = getIntent().getIntExtra("id", 0);
        Log.d("movieid",""+movieId);
        String rs = url.replace("*",""+movieId);
        Log.d("movieid",rs);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(rs, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("trailer", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    videoId= results.getJSONObject(0).getString("key");
                    Log.d("trailer",videoId);
                } catch (JSONException e) {
                    Log.e("trailer", "jsonException: ");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("trailer", "onFailure");

            }
        });
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo("" + videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}

