package com.aripratom.moviecataloguesubmission4localstorage.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.activity.DetailActivity;
import com.aripratom.moviecataloguesubmission4localstorage.data.MovieItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieItems> listMovies = new ArrayList<>();

    public void setData(ArrayList<MovieItems> items){
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_view, viewGroup,false);
       return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(listMovies.get(position));

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle, movieCaption,movieRelease;
        RelativeLayout relative;

        MovieViewHolder(@NonNull View itemView){
            super(itemView);
            moviePoster = itemView.findViewById(R.id.img_item_photo);
            movieTitle = itemView.findViewById(R.id.tv_item_name);
            movieCaption = itemView.findViewById(R.id.tv_item_caption);
            movieRelease = itemView.findViewById(R.id.tv_item_release_date);
            relative = itemView.findViewById(R.id.relative);

        }

        void bind(final MovieItems movieItems){
            String imgUrl = "https://image.tmdb.org/t/p/w185/";
            Glide.with(moviePoster)
                .load(imgUrl+movieItems.getPoster())
                    .apply(new RequestOptions().override(358, 485))
                  .into(moviePoster);

            movieTitle.setText(movieItems.getMovieTitle());
            movieCaption.setText(movieItems.getMovieCaption());
            movieRelease.setText(movieItems.getMovieReleaseDate());

            final MovieItems items = (MovieItems) listMovies.get(getPosition());
            relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra("movie", items);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
