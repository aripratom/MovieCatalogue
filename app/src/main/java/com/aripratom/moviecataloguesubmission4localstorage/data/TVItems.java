package com.aripratom.moviecataloguesubmission4localstorage.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract;

import org.json.JSONObject;

import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.getColumnInt;
import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.getColumnString;


public class TVItems implements Parcelable {
    private int tvid;
    private String tvposter;
    private String tvTitle;
    private String tvCaption;
    private String tvReleaseDate;

    public TVItems(){
    }

    public TVItems(Cursor cursor){
        this.tvid = getColumnInt(cursor, DatabaseContract.TVColumns._ID_TV);
        this.tvposter = getColumnString(cursor, DatabaseContract.TVColumns.POSTER_TV);
        this.tvTitle = getColumnString(cursor, DatabaseContract.TVColumns.TITLE_TV);
        this.tvCaption = getColumnString(cursor, DatabaseContract.TVColumns.OVERVIEW_TV);
        this.tvReleaseDate = getColumnString(cursor, DatabaseContract.TVColumns.RELEASE_DATE_TV);

    }

    public TVItems(JSONObject object){
        try {
            int id = object.getInt("id");
            String poster = object.getString("poster_path");
            String title = object.getString("original_name");
            String caption = object.getString("overview");
            String release = object.getString("first_air_date");

            this.tvid = id;
            this.tvposter = poster ;
            this.tvTitle = title ;
            this.tvCaption = caption ;
            this.tvReleaseDate = release ;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected TVItems(Parcel in) {
        tvid = in.readInt();
        tvposter = in.readString();
        tvTitle = in.readString();
        tvCaption = in.readString();
        tvReleaseDate = in.readString();
    }

    public static final Creator<TVItems> CREATOR = new Creator<TVItems>() {
        @Override
        public TVItems createFromParcel(Parcel in) {
            return new TVItems(in);
        }

        @Override
        public TVItems[] newArray(int size) {
            return new TVItems[size];
        }
    };

    public int getTvid() {
        return tvid;
    }

    public void setTvid(int tvid) {
        this.tvid = tvid;
    }

    public String getTvposter() {
        return tvposter;
    }

    public void setTvposter(String tvposter) {
        this.tvposter = tvposter;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvCaption() {
        return tvCaption;
    }

    public void setTvCaption(String tvCaption) {
        this.tvCaption = tvCaption;
    }

    public String getTvReleaseDate() {
        return tvReleaseDate;
    }

    public void setTvReleaseDate(String tvReleaseDate) {
        this.tvReleaseDate = tvReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tvid);
        dest.writeString(tvposter);
        dest.writeString(tvTitle);
        dest.writeString(tvCaption);
        dest.writeString(tvReleaseDate);
    }
}

