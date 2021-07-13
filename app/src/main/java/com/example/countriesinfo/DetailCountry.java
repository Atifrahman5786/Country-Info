package com.example.countriesinfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailCountry extends AppCompatActivity implements Serializable {
    Countries c;
    TextView name, capital, region, subregion, language, concurrency, borders, population;
    ImageView flag;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_country);

        c = (Countries) getIntent().getSerializableExtra("Country");

        name = findViewById(R.id.name_text_view_detail);
        capital=  findViewById(R.id.capital_text_view_detail);
        region = findViewById(R.id.region_text_view_detail);
        subregion = findViewById(R.id.subregion_text_view_detail);
        language = findViewById(R.id.language_text_view_detail);

        borders = findViewById(R.id.border_text_view_detail);
        population = findViewById(R.id.population_text_view_detail);

        flag = findViewById(R.id.flag_image_view_detail);

        name.setText(c.getName());
        capital.setText(c.getCapital());
        region.setText((c.getRegion()));
        subregion.setText(c.getSubregion());
        population.setText(String.valueOf(c.getPopulation()));

        ImageUtils.fetchSvg(getApplicationContext(), c.getFlag(), flag);

        String temp="";
        for(String s:c.getBorders())
            temp+=s+", ";
        if(temp.length()==0)
            borders.setText(c.getBorders().toString());
        else {
            temp = temp.substring(0, temp.length() - 2);
            borders.setText(temp);
        }
        temp="";
        for(String s:c.getLanguages())
            temp+=s+", ";
        temp=temp.substring(0,temp.length()-2);
        language.setText(temp);

    }
}
