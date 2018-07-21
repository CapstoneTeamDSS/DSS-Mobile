package com.example.administrator.dssproject.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.dssproject.DataBase.Box;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoxFragment extends Fragment {

    private Button btnAdd;
    private EditText txtBoxId;

    public BoxFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_box, container, false);

        /*txtBoxId = view.findViewById(R.id.txtBoxId);
        btnAdd = view.findViewById(R.id.btnAddBox);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxIdSring = txtBoxId.getText().toString();
                int boxId = Integer.parseInt(boxIdSring);
                Box box = new Box(boxId);
                MainActivity.myAppDatabase.boxDAO().addBox(box);
                MainActivity.fragmentManager.beginTransaction().remove(BoxFragment.this);
            }
        });
*/
        return view;
    }

}
