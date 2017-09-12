package com.example.android.quakeapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public final class Query {

    private Query() {
    }

    public static ArrayList<Earthquake> extractEarthquakes(String requestUrl) {

        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        String jsonResponse = fetchData(requestUrl);
        try {
            JSONObject jObject = new JSONObject(jsonResponse);
            JSONArray jArray = jObject.getJSONArray("features");
            for (int i=0; i<jArray.length(); i++) {
                JSONObject jObjectArray = jArray.getJSONObject(i);
                JSONObject jsonObjectProperties = jObjectArray.getJSONObject("properties");
                double JsonMagDouble = jsonObjectProperties.getDouble("mag");
                String JsonPlaceString = jsonObjectProperties.getString("place");
                long JsonTimeLong = jsonObjectProperties.getLong("time");
                String JsonUrlString = jsonObjectProperties.getString("url");

                earthquakes.add(new Earthquake(JsonMagDouble, JsonPlaceString, JsonTimeLong, JsonUrlString));
            }

        } catch (JSONException e) {
            Log.e("Query", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }

    public static String fetchData(String requestUrl){
        URL url = null;
        String jsonResponse = "";
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e("Query", "Problem with URL format", e);
        }
        if(url != null) {
            try {
                jsonResponse = httpRequest(url);
            } catch (IOException e) {
                Log.e("Query", "Problem making httpRequest", e);
            }
        }
        return jsonResponse;
    }

    private static String httpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream in = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                in = urlConnection.getInputStream();
                jsonResponse = readStream(in);
            }
        } catch (IOException e) {
            Log.e("Query", "Problem making httpRequest connection", e);
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(in != null){
                in.close();
            }
        }
        return jsonResponse;
    }

    private static String readStream(InputStream in) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            Log.e("Query", "Problem with readStream", e);
        }

        String result = sb.toString();
        return result;
    }
}
