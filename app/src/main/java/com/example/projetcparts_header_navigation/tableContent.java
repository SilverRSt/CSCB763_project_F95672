package com.example.projetcparts_header_navigation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class tableContent extends AppCompatActivity {
    private databaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_content);

//        this.databaseHelper = new databaseHelper(this);
//        this.database = databaseHelper.getWritableDatabase();

        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_data);

        for (int i = 0; i < 2; i++) {
            TableRow row = new TableRow(this);


            TextView one = new TextView(this);
            one.setText(R.string.rowDataTest);

            TextView two = new TextView(this);
            two.setText(R.string.rowDataTest);

            TextView three = new TextView(this);
            three.setText(R.string.rowDataTest);


            row.addView(one);
            row.addView(two);
            row.addView(three);

            tableLayout.addView(row, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent activityToStart;

        if(item.getItemId() == R.id.itemOne) {
            Toast.makeText(this, "You`re in Clothes", Toast.LENGTH_SHORT).show();
            return true;

        } else if(item.getItemId() == R.id.itemThree) {
            activityToStart = new Intent(this, MainActivity.class);
            startActivity(activityToStart);
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        this.database.close();
        this.databaseHelper.close();

        super.onDestroy();
    }
}
