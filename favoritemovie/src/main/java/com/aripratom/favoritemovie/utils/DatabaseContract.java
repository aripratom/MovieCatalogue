package com.aripratom.favoritemovie.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORITE = "favorite";

    public static final class MovieColumns implements BaseColumns {
        public static String POSTER ="poster";
        public static String TITLE ="title";
        public static String RELEASE_DATE ="release_date";
        public static String OVERVIEW ="overview";
    }

    public static final String AUTHORITY = "com.aripratom.moviecataloguesubmission4localstorage";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();


    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
