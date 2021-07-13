package com.example.countriesinfo.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Blob;
import java.util.ArrayList;

@Entity(tableName = "country_table")
public class CountryEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @Ignore
    public CountryEntry(String name) {
        this.name = name;
    }

    public CountryEntry(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
