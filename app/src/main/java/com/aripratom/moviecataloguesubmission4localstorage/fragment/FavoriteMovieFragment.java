package com.aripratom.moviecataloguesubmission4localstorage.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.adapter.FavoriteMovieAdapter;

import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoriteMovieAdapter adapter;
    private View view;
    private Cursor listMovie;
    private RelativeLayout relativeLayout;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        relativeLayout = view.findViewById(R.id.relative_fav_movie);
        recyclerView = view.findViewById(R.id.rv_category_fav_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoriteMovieAdapter(listMovie, getActivity());
        adapter.setListMovie(listMovie);
        recyclerView.setAdapter(adapter);


        return view;
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
            return getActivity().getApplicationContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        public void onPostExecute(Cursor movie) {
            super.onPostExecute(movie);

            listMovie = movie;
            adapter.setListMovie(listMovie);
            adapter.notifyDataSetChanged();
        }
    }


    public interface OnFragmentInteractionListener {
    }
}
