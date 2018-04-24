package com.example.abhishek.jsondemo;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    public class JsonDownloader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String inputLine;
                StringBuilder builder = new StringBuilder();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    builder.append(inputLine);
                }

                result = builder.toString();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                String weatherInfo = jsonObject.getString("current_observation");

                JSONObject jsonObject_1 = new JSONObject(weatherInfo);

                String weatherInfo_1 = jsonObject_1.getString("display_location");

                String temperature_string = jsonObject_1.getString("temperature_string");

                Log.i("Current", weatherInfo);

                Log.i("Observation", weatherInfo_1);

                Log.i("temperature_string", temperature_string);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonDownloader task = new JsonDownloader();
        task.execute("http://api.wunderground.com/api/91e14f3b0fc356f6/conditions/q/delhi,india.json");

//        Log.i("jsonData", jsonData);
    }
}
