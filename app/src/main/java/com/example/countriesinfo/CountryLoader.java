package com.example.countriesinfo;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.util.List;

public class CountryLoader extends AsyncTaskLoader<List<Countries>> {
    private static final String LOG_TAG = CountryLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public CountryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Countries> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        List<Countries> earthquakes = null;
        try {
            earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakes;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
