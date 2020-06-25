package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;
import com.example.flixster.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.movielayout, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mvTitle;
        TextView mvOver;
        ImageView mvPoster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mvTitle = itemView.findViewById(R.id.mvTitle);
            mvOver = itemView.findViewById(R.id.mvOver);
            mvPoster = itemView.findViewById(R.id.mvPoster);


        }

        public void bind(Movie movie) {
            mvTitle.setText(movie.getTitle());
            mvOver.setText(movie.getDescription());
            String url;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                url = movie.getBdPath();
                Glide.with(context).load(url).placeholder(R.drawable.flicks_backdrop_placeholder).into(mvPoster);

            } else {
                url = movie.getPosterPath();
                Glide.with(context).load(url).placeholder(R.drawable.flicks_movie_placeholder).into(mvPoster);
            }
            // no longer necessary given the placeholder
            // if (url == "") {
            //   mvPoster.setImageResource(R.drawable.ic_action_name);
            //}
        }
    }
}
