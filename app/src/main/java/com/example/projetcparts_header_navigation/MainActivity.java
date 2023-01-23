package com.example.projetcparts_header_navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Main activity - main/home page
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = findViewById(R.id.musicButton);
        this.button.setOnClickListener(this);
    }

    /**
     * Check if the service (MusicPlayer) is currently running
     * @param serviceClass
     *          the service to be check if its running
     * @return
     *          true if running, false if not running
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
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
        if(view == this.button && !this.isMyServiceRunning(MusicService.class)) {
            startService(new Intent(this, MusicService.class));

        } else if(view == this.button && this.isMyServiceRunning(MusicService.class)){
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