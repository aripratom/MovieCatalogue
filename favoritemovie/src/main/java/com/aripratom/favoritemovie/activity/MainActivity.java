package com.aripratom.favoritemovie.activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aripratom.favoritemovie.R;
import com.aripratom.favoritemovie.adapter.FavoriteMovieAdapter;

import static com.aripratom.favoritemovie.utils.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private FavoriteMovieAdapter adapter;
    RecyclerView rv;
    private Cursor listMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_category);
        adapter = new FavoriteMovieAdapter(listMovie, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteMovieAdapter(listMovie, this);
        adapter.setListMovie(listMovie);
        rv.setAdapter(adapter);


    }


    @Override
    public void onResume() {
        super.onResume();
        new loadData().execute();
    }
    public class loadData extends AsyncTask<Void, Void, Cursor> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        public void onPostExecute(Cursor movie) {
            super.onPostExecute(movie);

            listMovie = movie;
            adapter.setListMovie(listMovie);
            adapter.notifyDataSetChanged();
        }
    }

}
