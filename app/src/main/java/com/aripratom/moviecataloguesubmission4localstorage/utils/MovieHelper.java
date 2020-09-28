package com.aripratom.moviecataloguesubmission4localstorage.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.aripratom.moviecataloguesubmission4localstorage.data.MovieItems;

import java.util.ArrayList;


import static android.provider.BaseColumns._ID;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.OVERVIEW;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.POSTER;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.MovieColumns.TITLE;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TABLE_FAVORITE;

public class MovieHelper {

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context){
        this.context = context;
    }

    public MovieHelper open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<MovieItems> getData(){
        Cursor cursor;
        cursor = database.query(TABLE_FAVORITE,null,null,null,null,null,_ID+ " DESC",null);
        cursor.moveToFirst();

        ArrayList<MovieItems> arrayList = new ArrayList<>();
        MovieItems MovieItems;
        if (cursor.getCount()>0){
            do {
                MovieItems = new MovieItems();
                MovieItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                MovieItems.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                MovieItems.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                MovieItems.setMovieReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                MovieItems.setMovieCaption(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

                arrayList.add(MovieItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
    public boolean checkData(int id){
        Cursor cursor;
        cursor = database.rawQuery("select * from "+TABLE_FAVORITE+" where "+_ID+" = "+id+"",null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Cursor queryByIdProvider(String id){
        return database.query(TABLE_FAVORITE,null,_ID + " = ?",new String[]{id},null,null,null,null);
    }
    public Cursor queryProvider(){
        return database.query(TABLE_FAVORITE,null,null,null,null,null,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(TABLE_FAVORITE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(TABLE_FAVORITE,values,_ID + " = '"+id+"'", null);
    }
    public int deleteProvider(String id){
        return database.delete(TABLE_FAVORITE, _ID + " = '"+id+"'", null);
    }
}
