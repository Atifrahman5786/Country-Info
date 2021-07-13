package com.example.countriesinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
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

public class CountryAdapter extends ArrayAdapter<Countries>implements Serializable {
    private static final int DEFAULT_TASK_ID = -1;
    Context context;
    public CountryAdapter(@NonNull Context context, ArrayList<Countries> countries) {
        super(context, 0, countries);
        this.context = context;
    }
    private AppDatabase mDb;



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        mDb = AppDatabase.getInstance(context);
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout, parent, false
            );
        }
        final Countries currentCountries = getItem(position);
        TextView txtCountry = listItemView.findViewById(R.id.txtCountry);
        Button btnStore = listItemView.findViewById(R.id.btnStore);
        String country = currentCountries.getName();
        txtCountry.setText(country);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=  new Intent(context, DetailCountry.class);
                intent.putExtra("Country",currentCountries);
                context.startActivity(intent);
            }
        });

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryEntry entry = new CountryEntry(currentCountries.getName());
                mDb.countryDao().insertTask(entry);



            }
        });

        return listItemView;
    }

}
