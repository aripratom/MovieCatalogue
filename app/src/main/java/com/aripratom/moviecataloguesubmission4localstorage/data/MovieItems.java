package com.aripratom.moviecataloguesubmission4localstorage.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract;

import org.json.JSONObject;

import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.getColumnInt;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.getColumnString;

public class MovieItems implements Parcelable {
    private int id;
    private String poster;
    private String MovieTitle;
    private String MovieCaption;
    private String MovieReleaseDate;

    public MovieItems(){
    }

    public MovieItems(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.MovieColumns._ID);
        this.poster = getColumnString(cursor, DatabaseContract.MovieColumns.POSTER);
        this.MovieTitle = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.MovieCaption = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.MovieReleaseDate = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);

    }

    public MovieItems(JSONObject object){
        try {
            int id = object.getInt("id");
            String poster = object.getString("poster_path");
            String title = object.getString("title");
            String caption = object.getString("overview");
            String release = object.getString("release_date");

            this.id = id;
            this.poster = poster ;
            this.MovieTitle = title ;
            this.MovieCaption = caption ;
            this.MovieReleaseDate = release ;
        } catch (Exception e){
            e.printStackTrace();
        }
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

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        MovieTitle = movieTitle;
    }

    public String getMovieCaption() {
        return MovieCaption;
    }

    public void setMovieCaption(String movieCaption) {
        MovieCaption = movieCaption;
    }

    public String getMovieReleaseDate() {
        return MovieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        MovieReleaseDate = movieReleaseDate;
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
