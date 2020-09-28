package com.aripratom.moviecataloguesubmission4localstorage.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.data.MovieItems;
import com.aripratom.moviecataloguesubmission4localstorage.utils.MovieHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.aripratom.moviecataloguesubmission4localstorage.utils.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<MovieItems> movieItemsList;
    private Context mContext;
    private int mAppWidgetId;
    private MovieHelper movieHelper;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        movieHelper = new MovieHelper(mContext);
        movieHelper.open();
        movieItemsList = new ArrayList<>();
        movieItemsList.addAll(movieHelper.getData());

        if (cursor != null){
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
          return  movieItemsList.size();
        }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext).asBitmap().load("https://image.tmdb.org/t/p/w185/"+movieItemsList.get(position).getPoster()).submit().get();
        } catch (Exception e){
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.image_View, bmp);
        remoteViews.setTextViewText(R.id.tv_item_name, movieItemsList.get(position).getMovieTitle());

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.image_View, fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
