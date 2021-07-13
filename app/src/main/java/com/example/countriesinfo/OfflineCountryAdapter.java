package com.example.countriesinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.countriesinfo.database.AppDatabase;
import com.example.countriesinfo.database.CountryEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class OfflineCountryAdapter extends ArrayAdapter<CountryEntry> implements Serializable {
    Context context;

    public OfflineCountryAdapter(@NonNull Context context, ArrayList<CountryEntry> countryEntries) {
        super(context, 0, countryEntries);
        this.context = context;
    }

    private AppDatabase mDb;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout, parent, false
            );
        }
        final CountryEntry countryEntry = getItem(position);
        TextView txtCountry = listItemView.findViewById(R.id.txtCountry);
        Button btnStore = listItemView.findViewById(R.id.btnStore);
        String country = countryEntry.getName();
        txtCountry.setText(country);
        btnStore.setVisibility(View.INVISIBLE);
        return listItemView;
    }

}
