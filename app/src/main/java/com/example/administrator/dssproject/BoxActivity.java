package com.example.administrator.dssproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.dssproject.DataBase.Box;

public class BoxActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

        mEditText = findViewById(R.id.editText);
        mButton = findViewById(R.id.btnAddBoxId);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("myBox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("boxid", Integer.parseInt(mEditText.getText().toString()));
                editor.commit();

                /*String boxIdString = mEditText.getText().toString();
                int boxId = Integer.parseInt(boxIdString);
                Box box = new Box(boxId);
                MainActivity.myAppDatabase.boxDAO().addBox(box);*/
                Toast.makeText(BoxActivity.this, "Add successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BoxActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
