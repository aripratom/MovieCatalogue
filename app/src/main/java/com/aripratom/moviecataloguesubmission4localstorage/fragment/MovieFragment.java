package com.aripratom.moviecataloguesubmission4localstorage.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.adapter.MovieAdapter;
import com.aripratom.moviecataloguesubmission4localstorage.data.MovieItems;
import com.aripratom.moviecataloguesubmission4localstorage.data.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    SearchView keyword;


    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(){
        return new MovieFragment();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_movie, container, false);

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        keyword = view.findViewById(R.id.search_movie);

        RecyclerView recyclerView = view.findViewById(R.id.rv_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.loading);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.setListMovie();
        movieViewModel.getListMovie().observe(this, new android.arch.lifecycle.Observer<ArrayList<MovieItems>>() {
            @Override
            public void onChanged( ArrayList<MovieItems> movieItems) {
                if (movieItems != null){
                    adapter.setData(movieItems);
                    showLoading(false);
                } else {
                    showLoading(true);
                }
            }
        });

        keyword.onActionViewExpanded();
        keyword.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String pencarian = keyword.getQuery().toString();
                movieViewModel.searchMovie(pencarian);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pencarian = keyword.getQuery().toString();
                movieViewModel.searchMovie(pencarian);

                return false;
            }
        });

        return view;
     }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
