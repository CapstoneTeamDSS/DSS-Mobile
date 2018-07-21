package com.example.administrator.dssproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        boolean checkUpdate = false;
        if(!checkUpdate){
            int layoutId = 2;
            getLayout(layoutId);
        }else{
            return view;
        }

        return view;
    }



    public void getLayout(int layoutId) {
//        switch (layoutId) {
//            case 1:
//                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Portriat9x16Fragment()).
//                        addToBackStack(null).commit();
//                break;
//            case 2:
//                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Landscape16x9Fragment()).
//                        addToBackStack(null).commit();
//                break;
//            default:
//
//        }


    }

}
