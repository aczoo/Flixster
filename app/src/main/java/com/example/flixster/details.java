package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class details extends AppCompatActivity {
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvDate;
    ImageView ivBackDrop;
    RatingBar rbVoteAverage;
    String videoId = "";
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvTitle = (TextView) findViewById(R.id.mvTitle);
        tvOverview = (TextView) findViewById(R.id.mvOverview);
        tvDate = (TextView) findViewById(R.id.tvDate);
        ivBackDrop = (ImageView) findViewById(R.id.ivBackDrop);
        rbVoteAverage = (RatingBar) findViewById(R.id.ratingBar);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getDescription());
        tvDate.setText(movie.getReleaseDate());
        String temp = movie.getBdPath();
        id = movie.getId();
        Glide.with(details.this).load(temp).transform(new RoundedCornersTransformation(15, 15)).placeholder(R.drawable.flicks_backdrop_placeholder).into(ivBackDrop);
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }

    public void clickTrailer(View v) {
        Intent i = new Intent(details.this, movietrailer.class);
        String url = "https://api.themoviedb.org/3/movie/*/videos?api_key=5a4f96712b96651b6e97151cc6e8e0a8&language=en-US";
        String rs = url.replace("*", "" + id);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(rs, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    videoId = results.getJSONObject(0).getString("key");
                    Log.d("trailer", videoId);
                } catch (JSONException e) {
                    Log.e("trailer", "jsonException: ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("trailer", "onFailure");
                Toast.makeText(details.this, "Can't find video", Toast.LENGTH_SHORT).show();
            }
        });
        if (videoId != "") {
            i.putExtra("id", videoId);
            startActivity(i);
        }

    }
}



