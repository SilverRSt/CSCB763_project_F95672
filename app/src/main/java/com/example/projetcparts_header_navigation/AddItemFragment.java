package com.example.projetcparts_header_navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class AddItemFragment extends Fragment {
    public AddItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_layout, container, false);

        Button closeFragment = view.findViewById(R.id.back_id);
//        closeFragment.setOnClickListener(v -> getActivity().onBackPressed()); //returns to main activity not prev activity!!!
        closeFragment.setOnClickListener(v ->
                getFragmentManager()
                        .beginTransaction()
                        .remove(AddItemFragment.this).commit());

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
