package com.example.projetcparts_header_navigation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

/**
 * Fragment for poping up after add item has been clicked from the TableContent activity
 */
public class AddItemFragment extends Fragment {
    private DatabaseHelper database;

    public AddItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_layout, container, false);
        this.database = new DatabaseHelper(getActivity());

        //button for closing the fragment
        Button closeFragment = view.findViewById(R.id.back_id);
        closeFragment.setOnClickListener(v ->
                getFragmentManager()
                        .beginTransaction()
                        .remove(AddItemFragment.this).commit());

        //button for submit form -> get data from user and pass it onto database to insert as new item
        Button submitForm = view.findViewById(R.id.create_id);
        submitForm.setOnClickListener(v -> {
            EditText itemName = (EditText) getView().findViewById(R.id.item_name_id);
            String name = itemName.getText().toString();

            EditText itemBrand = (EditText)getView().findViewById(R.id.item_brand_id);
            String brand = itemBrand.getText().toString();

            EditText itemQuantity = (EditText)getView().findViewById(R.id.item_quantity_id);
            int quantity = Integer.parseInt(itemQuantity.getText().toString());

            database.addItem(name, brand, quantity);

            Intent intent = new Intent(getActivity(), TableContent.class);
            startActivity(intent);
        });


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
