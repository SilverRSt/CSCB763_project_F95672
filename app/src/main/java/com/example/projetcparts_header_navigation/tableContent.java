package com.example.projetcparts_header_navigation;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.content.ContextCompat;

public class tableContent extends AppCompatActivity {
    private databaseHelper databaseHelper;
    private SQLiteDatabase database;
    private final ViewGroup.LayoutParams tableRowDataParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    private static final String TEST_DATA = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_content);
        this.databaseHelper = new databaseHelper(this);
        //this.database = databaseHelper.getWritableDatabase();

        int tableSize = 0;

        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_data);
        this.generateRows(tableLayout, 3);

    }

    public void generateRows(TableLayout tableLayout, final int tableSize) {
        for (int i = 0; i < tableSize; i++) {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableContent));
            row.setPadding(13, 13, 13, 13);


            TextView one = new TextView(this);
            one.setText(TEST_DATA);
            one.setLayoutParams(this.tableRowDataParams);

            TextView two = new TextView(this);
            two.setText(TEST_DATA);
            two.setLayoutParams(tableRowDataParams);

            TextView three = new TextView(this);
            three.setText(TEST_DATA);
            three.setLayoutParams(tableRowDataParams);


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
