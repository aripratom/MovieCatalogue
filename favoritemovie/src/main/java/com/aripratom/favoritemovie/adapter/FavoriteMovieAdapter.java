package com.aripratom.favoritemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aripratom.favoritemovie.R;
import com.aripratom.favoritemovie.activity.DetailActivity;
import com.aripratom.favoritemovie.data.MovieItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder> {
    private Cursor listMovie;
    private Context context;

    public FavoriteMovieAdapter(Cursor listMovie, Context context){
        this.context = context;
        this.listMovie = listMovie;
    }

    public void setListMovie(Cursor listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new FavoriteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder favoriteViewHolder, int position) {
        String imgUrl = "https://image.tmdb.org/t/p/w185/";
        final MovieItems items = getItem(position);
        Glide.with(context)
                .load(imgUrl+items.getPoster())
                .apply(new RequestOptions().override(358, 485))
                .into(favoriteViewHolder.favoritePoster);
        favoriteViewHolder.favoriteTitle.setText(items.getMovieTitle());
        favoriteViewHolder.favoriteRelease.setText(items.getMovieReleaseDate());
        favoriteViewHolder.favoriteCaption.setText(items.getMovieCaption());
        favoriteViewHolder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra("movie", items);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listMovie == null) return 0;
        return listMovie.getCount();
    }

    private MovieItems getItem(int position){
        if (!listMovie.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItems(listMovie);
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder{
        ImageView favoritePoster;
        TextView favoriteTitle, favoriteCaption, favoriteRelease;
        RelativeLayout relative;

        FavoriteViewHolder(View itemView){
            super(itemView);
            favoritePoster = itemView.findViewById(R.id.img_item_photo);
            favoriteTitle = itemView.findViewById(R.id.tv_item_name);
            favoriteCaption = itemView.findViewById(R.id.tv_item_caption);
            favoriteRelease = itemView.findViewById(R.id.tv_item_release_date);
            relative = itemView.findViewById(R.id.relative);

        }
    }
}
