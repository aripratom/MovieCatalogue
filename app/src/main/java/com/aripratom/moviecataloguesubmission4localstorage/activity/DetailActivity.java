package com.aripratom.moviecataloguesubmission4localstorage.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.data.MovieItems;
import com.aripratom.moviecataloguesubmission4localstorage.data.TVItems;
import com.aripratom.moviecataloguesubmission4localstorage.utils.MovieHelper;
import com.aripratom.moviecataloguesubmission4localstorage.utils.TVHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.CONTENT_URI;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.CONTENT_URI_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.OVERVIEW;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.POSTER;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.TITLE;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.OVERVIEW_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.POSTER_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.RELEASE_DATE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.TITLE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns._ID_TV;

public class DetailActivity extends AppCompatActivity {
    ImageView poster;
    TextView title, caption, release;

    public Context context;
    public MovieHelper helper;
    public TVHelper tvHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        poster = findViewById(R.id.img_item_photo);
        title = findViewById(R.id.tv_item_name);
        release = findViewById(R.id.tv_item_release_date);
        caption = findViewById(R.id.tv_item_caption);

        final MovieItems movieItems = getIntent().getParcelableExtra("movie");
        final TVItems tvItems = getIntent().getParcelableExtra("tv");

        helper = new MovieHelper(this);
        helper.open();
        tvHelper = new TVHelper(this);
        tvHelper.open();


        if (movieItems != null) {

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieItems.getPoster())
                    .apply(new RequestOptions().override(358, 485))
                    .into(poster);

            title.setText(movieItems.getMovieTitle());
            release.setText(movieItems.getMovieReleaseDate());
            caption.setText(movieItems.getMovieCaption());

            final FloatingActionButton fab_fav = findViewById(R.id.fab_favorit);


            if(helper.checkData(movieItems.getId())){
                fab_fav.setImageResource(R.drawable.star_on);
            }

            fab_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContentValues values = new ContentValues();
                    values.put(_ID,movieItems.getId());
                    values.put(POSTER,movieItems.getPoster());
                    values.put(TITLE,movieItems.getMovieTitle());
                    values.put(RELEASE_DATE,movieItems.getMovieReleaseDate());
                    values.put(OVERVIEW,movieItems.getMovieCaption());

                    if(helper.checkData(movieItems.getId()) == false) {
                        getContentResolver().insert(CONTENT_URI,values);
                        fab_fav.setImageResource(R.drawable.star_on);
                        Snackbar.make(v, getResources().getString(R.string.film_data_ditambahkan),Snackbar.LENGTH_SHORT).show();
                    } else {
                        showAlertDialogMovie(movieItems.getId(), fab_fav, v);
                    }
                }
            });

        }

        if (tvItems != null) {
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + tvItems.getTvposter())
                    .apply(new RequestOptions().override(358, 485))
                    .into(poster);

            title.setText(tvItems.getTvTitle());
            release.setText(tvItems.getTvReleaseDate());
            caption.setText(tvItems.getTvCaption());

            final FloatingActionButton fab_fav_tv = findViewById(R.id.fab_favorit);
            if(tvHelper.checkData(tvItems.getTvid())){
                fab_fav_tv.setImageResource(R.drawable.star_on);
            }

            fab_fav_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContentValues values = new ContentValues();
                    values.put(_ID_TV,tvItems.getTvid());
                    values.put(POSTER_TV,tvItems.getTvposter());
                    values.put(TITLE_TV,tvItems.getTvTitle());
                    values.put(RELEASE_DATE_TV,tvItems.getTvReleaseDate());
                    values.put(OVERVIEW_TV,tvItems.getTvCaption());

                    if(tvHelper.checkData(tvItems.getTvid()) == false) {
                        getContentResolver().insert(CONTENT_URI_TV,values);
                        fab_fav_tv.setImageResource(R.drawable.star_on);
                        Snackbar.make(v, getResources().getString(R.string.tv_data_ditambahkan),Snackbar.LENGTH_SHORT).show();
                    } else {
                        showAlertDialogTV(tvItems.getTvid(), fab_fav_tv, v);
                    }
                }
            });
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
        tvHelper.close();
    }

    private void showAlertDialogMovie(final int movieID, final FloatingActionButton fab_fav, final View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(getResources().getString(R.string.hapus));
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.film_pesan_hapus))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.ya), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movieID),null,null);
                        fab_fav.setImageResource(R.drawable.star_off);
                        Snackbar.make(v, getResources().getString(R.string.film_data_dihapus),Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.tidak), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showAlertDialogTV(final int tvID, final FloatingActionButton fab_fav_tv, final View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(getResources().getString(R.string.hapus));
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.tv_pesan_hapus))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.ya), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getContentResolver().delete(Uri.parse(CONTENT_URI_TV + "/" + tvID),null,null);
                        fab_fav_tv.setImageResource(R.drawable.star_off);
                        Snackbar.make(v, getResources().getString(R.string.tv_data_dihapus),Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.tidak), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}



