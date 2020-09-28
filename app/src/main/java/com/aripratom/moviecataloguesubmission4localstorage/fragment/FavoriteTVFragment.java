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
import com.aripratom.moviecataloguesubmission4localstorage.adapter.FavoriteTVAdapter;

import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVFragment extends Fragment {
    private RecyclerView rv_tv;
    private FavoriteTVAdapter adapter;
    private View view;
    private RelativeLayout relativeLayout;
    private Cursor listTV;

    private static final String API_URL = "https://api.themoviedb.org/3/discover/tv?api_key=22d5873ab964dff0f6fd17cc80278f29&language=en-US";



    public FavoriteTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        relativeLayout = view.findViewById(R.id.relative_fav_tv);
        rv_tv = view.findViewById(R.id.rv_category_fav_tv);
        rv_tv.setHasFixedSize(true);
        rv_tv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoriteTVAdapter(listTV, getActivity());
        adapter.setListTV(listTV);
        rv_tv.setAdapter(adapter);
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
            return getActivity().getApplicationContext().getContentResolver().query(CONTENT_URI_TV,null,null,null,null);
        }

        @Override
        public void onPostExecute(Cursor tv) {
            super.onPostExecute(tv);

            listTV = tv;
            adapter.setListTV(listTV);
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
