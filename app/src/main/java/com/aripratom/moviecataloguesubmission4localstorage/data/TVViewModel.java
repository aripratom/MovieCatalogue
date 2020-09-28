package com.aripratom.moviecataloguesubmission4localstorage.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TVViewModel extends ViewModel {
    private static final String API_KEY = "22d5873ab964dff0f6fd17cc80278f29";
    private MutableLiveData<ArrayList<TVItems>> listTVs = new MutableLiveData<>();

    public LiveData<ArrayList<TVItems>> getListTV() {
        return listTVs;
    }

    public void setListTVs() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVItems> listTVItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TVItems tvItems = new TVItems(tv);
                        listTVItems.add(tvItems);
                    }
                    listTVs.postValue(listTVItems);
                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("OnFailure", error.getMessage());

            }
        });
    }

    public void searchTV(String keyword){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVItems> listTVItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+keyword;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TVItems tvItems = new TVItems(tv);
                        listTVItems.add(tvItems);
                    }
                    listTVs.postValue(listTVItems);
                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("OnFailure", error.getMessage());

            }
        });
    }
}