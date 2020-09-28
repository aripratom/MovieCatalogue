package com.aripratom.moviecataloguesubmission4localstorage.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static android.provider.BaseColumns._ID;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.OVERVIEW;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.POSTER;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.TITLE;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TABLE_FAVORITE;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TABLE_FAVORITE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.OVERVIEW_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.POSTER_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.RELEASE_DATE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.TITLE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns._ID_TV;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "db_movie";
    public static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_FAVORITE = "create table "+TABLE_FAVORITE+ "("+
            _ID +" integer primary key autoincrement, "+
            POSTER +" text not null, "+
            TITLE +" text not null, "+
            RELEASE_DATE +" text not null, "+
            OVERVIEW +" text not null );"
            ;


    public static String CREATE_TABLE_FAVORITE_TV = "create table "+TABLE_FAVORITE_TV+ "("+
            _ID_TV +" integer primary key autoincrement, "+
            POSTER_TV+" text not null, "+
            TITLE_TV +" text not null, "+
            RELEASE_DATE_TV +" text not null, "+
            OVERVIEW_TV +" text not null );"
            ;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_FAVORITE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITE_TV);
        onCreate(db);

    }
}
