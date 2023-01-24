package com.example.projetcparts_header_navigation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.projetcparts_header_navigation.database.DatabaseHelper;
import com.example.projetcparts_header_navigation.entity.Cloth;
import com.example.projetcparts_header_navigation.fragments.AddItemFragment;

import java.util.List;

/**
 * Table content Activity
 */
public class TableContent extends AppCompatActivity {
    private Fragment addItemFragment = new AddItemFragment();
    private Button addButton; //open fragment -> form for adding items
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private final ViewGroup.LayoutParams tableRowDataParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_content);
        this.setUpAddButton();

        //create database
        this.databaseHelper = new DatabaseHelper(this);
        //get database for reading and writing
        this.database = this.databaseHelper.getWritableDatabase();

        //populate database with pre-made/default values
        //this.databaseHelper.populateTable();

        //set database size
        this.databaseHelper.setSize();

        TableLayout tableLayout = findViewById(R.id.table_data);
        this.generateRows(tableLayout);
    }

    /**
     * Set up add buttons behavior when clicked.
     * Set behaviour of button to show fragment for adding new item to database.
     */
    private void setUpAddButton() {
        this.addButton = findViewById(R.id.add_id);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_id, addItemFragment).commit();
            }
        });
    }

    /**
     * Generate the rows of the table to be shown.
     * @param tableLayout
     *          table layout
     */
    public void generateRows(TableLayout tableLayout) {
        //get list of all rows from database
        List<Cloth> cloths = this.databaseHelper.getAllClothes();

        for (Cloth cloth : cloths) {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.tableContent));
            row.setPadding(13, 13, 13, 13);

            TextView type = new TextView(this);
            type.setText(cloth.getType());
            type.setLayoutParams(this.tableRowDataParams);

            TextView brand = new TextView(this);
            brand.setText(cloth.getBrand());
            brand.setLayoutParams(this.tableRowDataParams);

            TextView quantity = new TextView(this);
            quantity.setText(String.valueOf(cloth.getQuantity()));
            quantity.setLayoutParams(this.tableRowDataParams);

            row.addView(type);
            row.addView(brand);
            row.addView(quantity);

            tableLayout.addView(row, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

    /**
     * Destroy database connection
     */
    @Override
    protected void onDestroy() {
        this.database.close();
        this.databaseHelper.close();
        super.onDestroy();
    }
}
