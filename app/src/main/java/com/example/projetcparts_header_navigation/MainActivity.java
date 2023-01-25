package com.example.projetcparts_header_navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Main activity - main/home page
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Map<Integer, String> WEEKDAYS;
    static {
        WEEKDAYS = new HashMap<>();
        WEEKDAYS.put(1, "Monday");
        WEEKDAYS.put(2, "Tuesday");
        WEEKDAYS.put(3, "Wednesday");
        WEEKDAYS.put(4, "Thursday");
        WEEKDAYS.put(5, "Friday");
        WEEKDAYS.put(6, "Saturday");
        WEEKDAYS.put(7, "Sunday");
    }

    private ImageButton button;
    private APIConnector connector = new APIConnector();
    private List<String> apiValues = new ArrayList<>();

    private String date;
    private String weekday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = findViewById(R.id.musicButton);
        TextView currentTime = findViewById(R.id.current_time);
        TextView currentWeekday = findViewById(R.id.current_weekday);
        this.button.setOnClickListener(this);

        try {
            this.apiValues = this.connector.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            this.separateJsonData();
            //this.currentTime.setText(this.apiValues.get(5));
            currentTime.setText(this.date);
            currentWeekday.setText(this.weekday);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Separate data from json list into only needed parts.
     */
    private void separateJsonData() {
        String fullDate = this.apiValues.get(5);
        this.date = fullDate.split("T")[0].substring(1);
        int weekNumber = Integer.parseInt(this.apiValues.get(29));
        this.weekday = WEEKDAYS.get(weekNumber);
    }


    /**
     * Check if the service (MusicPlayer) is currently running
     *
     * @return true if running, false if not running
     */
    private boolean isMusicServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MusicService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Handle logic for music button.
     * On first click start music from then every other stop then start based on if the service is running already.
     */
    @Override
    public void onClick(View view) {
        if(view == this.button && !this.isMusicServiceRunning()) {
            startService(new Intent(this, MusicService.class));

        } else if(view == this.button && this.isMusicServiceRunning()){
            stopService(new Intent(this, MusicService.class));
        }
    }


    /**
     * Create the option menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Handle on selection from menu where to take or display based on option chosen from MenuItem.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent activityToStart;

        if(item.getItemId() == R.id.itemOne) {
            activityToStart = new Intent(this, TableContent.class);
            startActivity(activityToStart);
            return true;

        } else if(item.getItemId() == R.id.itemThree) {
            Toast.makeText(this, "You`re in home paige", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}