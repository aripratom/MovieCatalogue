package com.aripratom.favoritemovie.activity;

import android.content.DialogInterface;
import android.database.Cursor;
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

import com.aripratom.favoritemovie.R;
import com.aripratom.favoritemovie.data.MovieItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.aripratom.favoritemovie.utils.DatabaseContract.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {
    ImageView poster;
    TextView title, caption, release;

    private MovieItems movieItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        poster = findViewById(R.id.img_item_photo);
        title = findViewById(R.id.tv_item_name);
        release = findViewById(R.id.tv_item_release_date);
        caption = findViewById(R.id.tv_item_caption);

        movieItems = getIntent().getParcelableExtra("movie");

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null){

                if(cursor.moveToFirst()) movieItems = new MovieItems(cursor);
                cursor.close();
            }
        }

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + movieItems.getPoster())
                .apply(new RequestOptions().override(358, 485))
                .into(poster);

        title.setText(movieItems.getMovieTitle());
        release.setText(movieItems.getMovieReleaseDate());
        caption.setText(movieItems.getMovieCaption());


        final int movieId = movieItems.getId();
        final FloatingActionButton fab_fav = findViewById(R.id.fab_favorit);
        fab_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogMovie(movieId,fab_fav, v);
            }
        });
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }


}

