package com.aripratom.favoritemovie.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.aripratom.favoritemovie.utils.DatabaseContract;

import static com.aripratom.favoritemovie.utils.DatabaseContract.getColumnInt;
import static com.aripratom.favoritemovie.utils.DatabaseContract.getColumnString;

public class MovieItems implements Parcelable {
    private int id;
    private String poster;
    private String MovieTitle;
    private String MovieCaption;
    private String MovieReleaseDate;


    public MovieItems(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.MovieColumns._ID);
        this.poster = getColumnString(cursor, DatabaseContract.MovieColumns.POSTER);
        this.MovieTitle = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.MovieCaption = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.MovieReleaseDate = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);

    }



    protected MovieItems(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        MovieTitle = in.readString();
        MovieCaption = in.readString();
        MovieReleaseDate = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPoster() {
        return poster;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public String getMovieCaption() {
        return MovieCaption;
    }

    public String getMovieReleaseDate() {
        return MovieReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(poster);
        dest.writeString(MovieTitle);
        dest.writeString(MovieCaption);
        dest.writeString(MovieReleaseDate);
    }
}
