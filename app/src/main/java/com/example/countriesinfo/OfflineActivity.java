package com.example.countriesinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.countriesinfo.database.AppDatabase;
import com.example.countriesinfo.database.CountryEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OfflineActivity extends AppCompatActivity implements Serializable {
    private AppDatabase mDb;
    private OfflineCountryAdapter mAdapter;
    private Button btnStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        mDb = AppDatabase.getInstance(getApplicationContext());

        mAdapter = new OfflineCountryAdapter(this, new ArrayList<CountryEntry>());

        ListView countryListView = findViewById(R.id.list_offline);
        countryListView.setAdapter(mAdapter);
        showSavedCountries();
        btnStore = countryListView.findViewById(R.id.btnStore);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_offline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all_menu:
                mDb.countryDao().deleteAll();
                mAdapter.clear();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showSavedCountries() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<CountryEntry> countryEntries = mDb.countryDao().loadAllCountries();
                // We will be able to simplify this once we learn more
                // about Android Architecture Components
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        mAdapter.addAll(countryEntries);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        showSavedCountries();
    }
}