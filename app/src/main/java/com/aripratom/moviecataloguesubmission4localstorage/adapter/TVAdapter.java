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
import com.aripratom.moviecataloguesubmission4localstorage.data.TVItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVViewHolder> {
    private ArrayList<TVItems> listTVs = new ArrayList<>();

    public void setData(ArrayList<TVItems> items){
        listTVs.clear();
        listTVs.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_view, viewGroup,false);
        return new TVViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TVAdapter.TVViewHolder tvViewHolder, int position) {
        tvViewHolder.bind(listTVs.get(position));

    }

    @Override
    public int getItemCount() {
        return listTVs.size();
    }

    class TVViewHolder extends RecyclerView.ViewHolder {
        ImageView tvPoster;
        TextView tvTitle, tvCaption,tvRelease;
        RelativeLayout relative;

        TVViewHolder(@NonNull View itemView){
            super(itemView);
            tvPoster = itemView.findViewById(R.id.img_item_photo);
            tvTitle = itemView.findViewById(R.id.tv_item_name);
            tvCaption = itemView.findViewById(R.id.tv_item_caption);
            tvRelease = itemView.findViewById(R.id.tv_item_release_date);
            relative = itemView.findViewById(R.id.relative);

        }

        void bind(final TVItems tvItems){
            String imgUrl = "https://image.tmdb.org/t/p/w185/";
            Glide.with(tvPoster)
                    .load(imgUrl+tvItems.getTvposter())
                    .apply(new RequestOptions().override(358, 485))
                    .into(tvPoster);

            tvTitle.setText(tvItems.getTvTitle());
            tvCaption.setText(tvItems.getTvCaption());
            tvRelease.setText(tvItems.getTvReleaseDate());

            final TVItems items = (TVItems) listTVs.get(getPosition());
            relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra("tv", items);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
