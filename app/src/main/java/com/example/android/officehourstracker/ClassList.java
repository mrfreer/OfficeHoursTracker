package com.example.android.officehourstracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ClassList extends AppCompatActivity {


    TextView textViewClassName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textViewClassName = (TextView) findViewById(R.id.textViewClass);
        textViewClassName.setText(getIntent().getExtras().getString("className"));
        String courseIdentification = Integer.toString(getIntent().getExtras().getInt("classID")) + " is the class ID.";
        Toast.makeText(getApplicationContext(), courseIdentification, Toast.LENGTH_LONG).show();
        //viewID
        //Toast.makeText(getApplicationContext(), Integer.toString(getIntent().getExtras().getInt("viewID")), Toast.LENGTH_LONG).show();
    }

}
