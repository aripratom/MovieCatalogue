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
import com.aripratom.moviecataloguesubmission4localstorage.adapter.TVAdapter;
import com.aripratom.moviecataloguesubmission4localstorage.data.TVItems;
import com.aripratom.moviecataloguesubmission4localstorage.data.TVViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {
    private TVAdapter adapter;
    private ProgressBar progressBar;
    private TVViewModel tvViewModel;
    SearchView keyword;



    public TVFragment() {
        // Required empty public constructor
    }

    public static TVFragment newInstance() {
        return new TVFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        adapter = new TVAdapter();
        adapter.notifyDataSetChanged();

        keyword = view.findViewById(R.id.search_tv);

        RecyclerView recyclerView = view.findViewById(R.id.rv_category_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.loading);
        tvViewModel = ViewModelProviders.of(this).get(TVViewModel.class);
        tvViewModel.setListTVs();
        tvViewModel.getListTV().observe(this, new android.arch.lifecycle.Observer<ArrayList<TVItems>>() {
            @Override
            public void onChanged( ArrayList<TVItems> tvItems) {
                if (tvItems != null){
                    adapter.setData(tvItems);
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
                tvViewModel.searchTV(pencarian);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pencarian = keyword.getQuery().toString();
                tvViewModel.searchTV(pencarian);

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
