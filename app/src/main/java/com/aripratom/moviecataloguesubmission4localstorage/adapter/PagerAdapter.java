package com.aripratom.moviecataloguesubmission4localstorage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aripratom.moviecataloguesubmission4localstorage.fragment.FavoriteMovieFragment;
import com.aripratom.moviecataloguesubmission4localstorage.fragment.FavoriteTVFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new FavoriteMovieFragment();
            case 1:
                return new FavoriteTVFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
