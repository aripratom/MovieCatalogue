package com.aripratom.moviecataloguesubmission4localstorage.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract;
import com.aripratom.moviecataloguesubmission4localstorage.utils.MovieHelper;
import com.aripratom.moviecataloguesubmission4localstorage.utils.TVHelper;

import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.AUTHORITY;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.AUTHORITY_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.CONTENT_URI;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    public static final int TV = 3;
    public static final int TV_ID =4;

    private static final UriMatcher sUriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE+ "/#", MOVIE_ID);

        sUriMatcher.addURI(AUTHORITY_TV, DatabaseContract.TABLE_FAVORITE_TV, TV);
        sUriMatcher.addURI(AUTHORITY_TV, DatabaseContract.TABLE_FAVORITE_TV+ "/#", TV_ID);

    }

    private MovieHelper movieHelper;
    private TVHelper tvHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        tvHelper = new TVHelper(getContext());
        tvHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        long added;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(values);
                break;
            case TV:
                added = tvHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/"+added);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            case TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0){
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return updated;
    }
}
