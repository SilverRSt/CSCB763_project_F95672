package com.example.projetcparts_header_navigation;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APIConnector extends AsyncTask<String, Void, List<String>> {
    private static final String REGEX_JSON = "\"(.*?)\"|:(.*?)[,}]";
    private static final String STRING_URL_WORLD_TIME = "https://worldtimeapi.org/api/timezone/Europe/Sofia";
    private URL url;
    private HttpURLConnection connection;
    private InputStreamReader reader;
    private String json;
    private List<String> apiValues = new ArrayList<>();
    private StringBuilder out = new StringBuilder("Something went wrong");


    @Override
    protected List<String> doInBackground(String... strings) {
        this.json = this.readData();
        this.sortJsonString(this.json);
//        TextView textView = this.mainActivity.get().findViewById(R.id.current_time);
//        textView.setText(this.apiValues.get(3));

        return this.apiValues;
    }

    public String readData() {
//        Thread webThread = new Thread(() -> {
            Log.i("Thread", "Running");

            if(createConnection()) {
                out = new StringBuilder();

                try {
                    BufferedReader bufferReader = new BufferedReader(reader);
                    String line;
                    while ((line = bufferReader.readLine()) != null) {
                        out.append(line).append("/n");
                    }
                    bufferReader.close();
                    this.connection.disconnect();

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
//        });
//
//        webThread.start();
        return this.out.toString();

    }

    private boolean createConnection() {
        try {
            this.url = new URL(STRING_URL_WORLD_TIME);
            this.connection = (HttpURLConnection) this.url.openConnection();
            this.connection.setRequestMethod("GET");
            this.connection.setRequestProperty("Content-length", "0");
            this.connection.setUseCaches(false);
            this.connection.connect();

            int statusCode = this.connection.getResponseCode();

            switch (statusCode) {
                case 200:
                case 201: {
                    InputStream input = this.connection.getInputStream();
                    this.reader = new InputStreamReader(input);
                    return true;
                }
                case 503: {
                    this.out.append("No Internet connection.");
                }
                default: {
                    return false;
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sortJsonString(String json) {
        Pattern pattern = Pattern.compile(REGEX_JSON);
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            this.apiValues.add(matcher.group(2));
        }
    }
}
