package com.example.countriesinfo;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static final ArrayList<String> eachCountry = new ArrayList<>();
    public static final ArrayList<String> eachCapital = new ArrayList<>();
    private QueryUtils(){}

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200 || urlConnection.getResponseCode() == 301) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Countries> extractFeatureFromJSON(String countryJSON) throws JSONException {
        if (TextUtils.isEmpty(countryJSON)) {
            return null;
        }
        List<Countries> countries = new ArrayList<>();
        JSONArray array = new JSONArray((countryJSON));
        try{
            for(int i = 0; i < array.length(); i++){
                JSONObject tempObject = array.getJSONObject((i));
                Countries newC = new Countries();
                newC.setName(tempObject.getString("name"));
                newC.setCapital(tempObject.getString("capital"));
                newC.setRegion(tempObject.getString("region"));
                newC.setSubregion(tempObject.getString("subregion"));
                newC.setFlag(tempObject.getString("flag"));
                newC.setPopulation(tempObject.getDouble("population"));
                ArrayList<String> tempList=new ArrayList<>();

                JSONArray tempArr=tempObject.getJSONArray("borders");
                for(int i1=0;i1<tempArr.length();i1++)
                {
                    tempList.add(tempArr.getString(i1));
                }
                newC.setBorders(tempList);

                tempList=new ArrayList<>();
                tempArr=tempObject.getJSONArray("languages");
                for (int i1=0;i1<tempArr.length();i1++)
                {
                    JSONObject obj=tempArr.getJSONObject(i1);
                    tempList.add(obj.getString("name"));
                }
                newC.setLanguages(tempList);
                countries.add(newC);
                eachCountry.add(newC.getName());
                eachCapital.add(newC.getCapital());
            }
        }catch(JSONException e){
            Log.e("QueryUtils", "Problem parsing the countries JSON results", e);
        }
        return countries;



    }
    public static List<Countries> fetchEarthquakeData(String requestUrl) throws JSONException {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Countries> countries = extractFeatureFromJSON(jsonResponse);
        return countries;
    }


}
