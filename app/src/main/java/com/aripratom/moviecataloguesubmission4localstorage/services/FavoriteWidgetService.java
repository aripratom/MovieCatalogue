package com.aripratom.moviecataloguesubmission4localstorage.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.aripratom.moviecataloguesubmission4localstorage.widgets.StackRemoteViewsFactory;

public class FavoriteWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
