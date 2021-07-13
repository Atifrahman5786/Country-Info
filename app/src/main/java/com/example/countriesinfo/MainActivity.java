package com.example.countriesinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.countriesinfo.database.AppDatabase;
import com.example.countriesinfo.database.CountryEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Countries>>, Serializable {

    private static final String COUNTRIES_REQUEST_URL = "https://restcountries.eu/rest/v2/region/asia";
    private static final int COUNTRY_LOADER_ID = 1;
    private TextView mEmptyStateView;
    private ProgressBar progressBar;

    private CountryAdapter mAdapter;
    private AppDatabase mDb;
    private Button btnStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = AppDatabase.getInstance(getApplicationContext());

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(COUNTRY_LOADER_ID, null, this);

        mAdapter = new CountryAdapter(this, new ArrayList<Countries>());

        ListView countryListView = findViewById(R.id.list);
        countryListView.setAdapter(mAdapter);

        countryListView.setEmptyView(mEmptyStateView);
        progressBar = findViewById(R.id.loading_spinner);
        mEmptyStateView = findViewById(R.id.emptyElement);
        mEmptyStateView.setVisibility(View.INVISIBLE);
        btnStore = countryListView.findViewById(R.id.btnStore);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo == null){
            mEmptyStateView.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.saved_countries:
                Intent intent = new Intent(getApplicationContext(), OfflineActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader<List<Countries>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(COUNTRIES_REQUEST_URL);

        return new CountryLoader(this, baseUri.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Countries>> loader, List<Countries> data) {
        progressBar.setVisibility(View.GONE);


        if(data != null && !data.isEmpty()){
            mEmptyStateView.setVisibility(View.INVISIBLE);
            mAdapter.clear();
            mAdapter.addAll(data);
        }

    }


    @Override
    public void onLoaderReset(Loader<List<Countries>> loader) {
        mAdapter.clear();
    }
}