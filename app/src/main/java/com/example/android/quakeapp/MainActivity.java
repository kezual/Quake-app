package com.example.android.quakeapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=3.0&limit=20";
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuakeAsyncTask asyncTask = new QuakeAsyncTask();
        asyncTask.execute(USGS_URL);

        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this, new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(adapter);
    }

    public class QuakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if(urls[0].isEmpty() || (urls.length < 1)) {
                return null;
            }

            List<Earthquake> earthquakes = Query.extractEarthquakes(urls[0]);

            return earthquakes;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            adapter.clear();
            adapter.addAll(earthquakes);
        }
    }
}