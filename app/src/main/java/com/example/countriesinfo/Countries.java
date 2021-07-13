package com.example.countriesinfo;

import java.io.Serializable;
import java.util.ArrayList;

public class Countries implements Serializable {
    private String name;
    private String capital;
    private String region;
    private String subregion;
    private double  population;
    private String flag;
    private ArrayList<String> borders;
    private ArrayList<String> languages;

    public ArrayList<String> getBorders() {
        return borders;
    }

    public void setBorders(ArrayList<String> borders) {
        this.borders = borders;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Countries() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }
}
