package com.aripratom.moviecataloguesubmission4localstorage.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.aripratom.moviecataloguesubmission4localstorage.data.TVItems;

import java.util.ArrayList;

import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TABLE_FAVORITE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.OVERVIEW_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.POSTER_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.RELEASE_DATE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns.TITLE_TV;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.TVColumns._ID_TV;

public class TVHelper {
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public TVHelper(Context context){
        this.context = context;
    }

    public TVHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<TVItems> getData(){
        Cursor cursor;
        cursor = database.query(TABLE_FAVORITE_TV,null,null,null,null,null,_ID_TV+ " DESC",null);
        cursor.moveToFirst();

        ArrayList<TVItems> arrayList = new ArrayList<>();
        TVItems TVItems;
        if (cursor.getCount()>0){
            do {
                TVItems = new TVItems();
                TVItems.setTvid(cursor.getInt(cursor.getColumnIndexOrThrow(_ID_TV)));
                TVItems.setTvposter(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_TV)));
                TVItems.setTvTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TV)));
                TVItems.setTvReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE_TV)));
                TVItems.setTvCaption(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_TV)));

                arrayList.add(TVItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
    public boolean checkData(int id_tv){
        Cursor cursor;
        cursor = database.rawQuery("select * from "+TABLE_FAVORITE_TV+" where "+_ID_TV+" = "+id_tv+"",null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Cursor queryByIdProvider(String id_tv){
        return database.query(TABLE_FAVORITE_TV,null,_ID_TV + " = ?",new String[]{id_tv},null,null,null,null);
    }
    public Cursor queryProvider(){
        return database.query(TABLE_FAVORITE_TV,null,null,null,null,null,_ID_TV + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(TABLE_FAVORITE_TV,null,values);
    }
    public int updateProvider(String id_tv,ContentValues values){
        return database.update(TABLE_FAVORITE_TV,values,_ID_TV + " = '"+id_tv+"'", null);
    }
    public int deleteProvider(String id_tv){
        return database.delete(TABLE_FAVORITE_TV, _ID_TV + " = '"+id_tv+"'", null);
    }
}

