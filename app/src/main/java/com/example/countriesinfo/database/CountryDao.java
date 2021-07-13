package com.example.countriesinfo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(CountryEntry countryEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(CountryEntry countryEntry);

    @Delete
    void deleteTask(CountryEntry countryEntry);

    @Query("SELECT * from country_table")
    List<CountryEntry> loadAllCountries();

    @Query("DELETE FROM country_table")
    void deleteAll();


}
