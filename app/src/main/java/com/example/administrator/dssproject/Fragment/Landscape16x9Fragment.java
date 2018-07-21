package com.example.administrator.dssproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.dssproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Landscape16x9Fragment extends Fragment {


    public Landscape16x9Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landscape16x9, container, false);
    }

}
