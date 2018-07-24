package com.example.administrator.dssproject.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.dssproject.API.ApiData;
import com.example.administrator.dssproject.DataBase.Box;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoxFragment extends Fragment {

    private EditText mEditText;
    private Button mButton;

    MainActivity context;

    public BoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box, container, false);
        mEditText = view.findViewById(R.id.editText);
        mButton = view.findViewById(R.id.btnAddBoxId);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxIdString = mEditText.getText().toString();
                int boxId = Integer.parseInt(boxIdString);
                Box box = new Box(boxId);
                MainActivity.myAppDatabase.boxDAO().addBox(box);
                Toast.makeText(getActivity(), "Add successfully", Toast.LENGTH_SHORT).show();
                ApiData.getDataFromAPI(container.getContext(), MainActivity.myAppDatabase.boxDAO().getBoxId());
            }
        });

        return view;
    }

}
