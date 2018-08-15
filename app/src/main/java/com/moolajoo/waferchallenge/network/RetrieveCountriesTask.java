package com.moolajoo.waferchallenge.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RetrieveCountriesTask extends AsyncTask<String, Void, List<JSONObject>> {

    private final String LOG_TAG = RetrieveCountriesTask.class.getSimpleName();
    private OnTaskCompleted listener;

    public RetrieveCountriesTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    private List<JSONObject> getDataFromJson(String apiJSON)
            throws JSONException {


        JSONArray countriesJSONArray = new JSONArray(apiJSON);

        Log.d(LOG_TAG, "getting data");
        List<JSONObject> countriesList = new ArrayList<JSONObject>();

        for (int i = 0; i < countriesJSONArray.length(); i++) {
            countriesList.add(countriesJSONArray.getJSONObject(i));
        }

        return countriesList;

    }

    @Override
    protected List<JSONObject> doInBackground(String... params) {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String countryJsonStr = null;


        try {
            // Construct the URL
            final String BASE_URL =
                    "https://restcountries.eu/rest/v2/all/";


            URL url = new URL(BASE_URL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            countryJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getDataFromJson(countryJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(List<JSONObject> countries) {
        Log.d(LOG_TAG, "on post");
        listener.onTaskCompleted(countries);
    }
}