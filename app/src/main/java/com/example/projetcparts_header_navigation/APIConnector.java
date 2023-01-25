package com.example.projetcparts_header_navigation;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Connector for world time API
 */
public class APIConnector extends AsyncTask<String, Void, List<String>> {
    private static final String REGEX_JSON = "\"(.*?)\"|:(.*?)[,}]";
    private static final String STRING_URL_WORLD_TIME = "https://worldtimeapi.org/api/timezone/Europe/Sofia";
    private HttpURLConnection connection;
    private InputStreamReader reader;
    private List<String> apiValues = new ArrayList<>();
    private StringBuilder output = new StringBuilder("Something went wrong!");

    @Override
    protected List<String> doInBackground(String... strings) {
        String json = this.readData();
        this.sortJsonString(json);
        return this.apiValues;
    }

    /**
     * Read json and append in StringBuilder.
     * @return
     *      json String
     */
    public String readData() {
        Log.i("Thread", "Running");

        if(createConnection()) {
            this.output = new StringBuilder();

            try {
                BufferedReader bufferReader = new BufferedReader(this.reader);
                String line;
                while ((line = bufferReader.readLine()) != null) {
                    this.output.append(line).append("/n");
                }
                bufferReader.close();
                this.connection.disconnect();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        return this.output.toString();
    }

    /**
     * Create connection.
     * @return true if connection successful, false if not.
     */
    private boolean createConnection() {
        try {
            URL url = new URL(STRING_URL_WORLD_TIME);
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setRequestMethod("GET");
            this.connection.connect();

            int statusCode = this.connection.getResponseCode();

            switch (statusCode) {
                case 200: {
                    InputStream input = this.connection.getInputStream();
                    this.reader = new InputStreamReader(input);
                    return true;
                }
                case 404: {
                    this.output.append("Invalid URL!");
                    return false;
                }
                case 503: {
                    this.output.append("No Internet connection!");
                    return false;
                }
                default: {
                    return false;
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Split json String into parts.
     * @param json
     *          json from API
     */
    private void sortJsonString(String json) {
        Pattern pattern = Pattern.compile(REGEX_JSON);
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            this.apiValues.add(matcher.group(2));
        }
    }

    public String getOutput() {
        return output.toString();
    }
}
