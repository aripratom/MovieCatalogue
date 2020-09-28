package com.aripratom.moviecataloguesubmission4localstorage.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORITE = "favorite";
    public static String TABLE_FAVORITE_TV = "favorite_tv";

    public static final class MovieColumns implements BaseColumns{
        public static String POSTER ="poster";
        public static String TITLE ="title";
        public static String RELEASE_DATE ="release_date";
        public static String OVERVIEW ="overview";
    }

    public static final class TVColumns implements BaseColumns{
        public static String _ID_TV ="id_tv";
        public static String POSTER_TV ="poster";
        public static String TITLE_TV ="title";
        public static String RELEASE_DATE_TV ="release_date";
        public static String OVERVIEW_TV ="overview";
    }

    public static final String AUTHORITY = "com.aripratom.moviecataloguesubmission4localstorage";
    public static final String AUTHORITY_TV = "com.aripratom.moviecataloguesubmission4localstorage";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme("content")
            .authority(AUTHORITY_TV)
            .appendPath(TABLE_FAVORITE_TV)
            .build();


    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
