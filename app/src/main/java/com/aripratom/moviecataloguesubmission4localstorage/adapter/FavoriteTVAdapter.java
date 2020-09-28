package com.aripratom.moviecataloguesubmission4localstorage.adapter;

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

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.activity.DetailActivity;
import com.aripratom.moviecataloguesubmission4localstorage.data.TVItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteTVAdapter extends RecyclerView.Adapter<FavoriteTVAdapter.FavoriteViewHolder> {
    private Cursor listTV;
    private Context context;

    public FavoriteTVAdapter(Cursor listTV, Context context){
        this.context = context;
        this.listTV = listTV;
    }

    public void setListTV(Cursor listTV) {
        this.listTV = listTV;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new FavoriteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FavoriteTVAdapter.FavoriteViewHolder favoriteViewHolder, int position) {
        String imgUrl = "https://image.tmdb.org/t/p/w185/";
        final TVItems items = getItem(position);
        Glide.with(context)
                .load(imgUrl+items.getTvposter())
                .apply(new RequestOptions().override(358, 485))
                .into(favoriteViewHolder.favoritePoster);
        favoriteViewHolder.favoriteTitle.setText(items.getTvTitle());
        favoriteViewHolder.favoriteRelease.setText(items.getTvReleaseDate());
        favoriteViewHolder.favoriteCaption.setText(items.getTvCaption());
        favoriteViewHolder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra("tv", items);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listTV == null) return 0;
        return listTV.getCount();
    }

    private TVItems getItem(int position){
        if (!listTV.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new TVItems(listTV);
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

