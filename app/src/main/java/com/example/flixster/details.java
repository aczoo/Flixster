package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
public class details extends AppCompatActivity {
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvDate;
    ImageView ivBackDrop;
    RatingBar rbVoteAverage;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvTitle = (TextView) findViewById(R.id.mvTitle);
        tvOverview = (TextView) findViewById(R.id.mvOverview);
        tvDate = (TextView) findViewById(R.id.tvDate);
        ivBackDrop= (ImageView) findViewById(R.id.ivBackDrop);
        rbVoteAverage = (RatingBar) findViewById(R.id.ratingBar);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getDescription());
        tvDate.setText(movie.getReleaseDate());
        String temp =movie.getBdPath();
        id=movie.getId();
        Glide.with(details.this).load(temp).transform(new RoundedCornersTransformation(15, 15)).placeholder(R.drawable.flicks_backdrop_placeholder).into(ivBackDrop);
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
    public void clickTrailer(View v)
    {
        Intent i = new Intent(details.this, movietrailer.class);
        i.putExtra("id", id);
        startActivity(i);

    }
}



