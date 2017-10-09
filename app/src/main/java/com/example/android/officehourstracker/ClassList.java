package com.example.android.officehourstracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClassList extends AppCompatActivity {


    TextView textViewClassName;
    private RecyclerView recyclerView;
    private List<Student> students;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewStudents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        students = new ArrayList<>();
        students.add(new Student("123", "Jon Smith"));
        students.add(new Student("234", "Juan Gonzalez"));

        adapter = new MyAdapterStudents(students, this);
        recyclerView.setAdapter(adapter);

        textViewClassName = (TextView) findViewById(R.id.textViewClass);
        textViewClassName.setText(getIntent().getExtras().getString("className"));
        String courseIdentification = Integer.toString(getIntent().getExtras().getInt("classID")) + " is the class ID.";
        Toast.makeText(getApplicationContext(), courseIdentification, Toast.LENGTH_LONG).show();
        //viewID
        //Toast.makeText(getApplicationContext(), Integer.toString(getIntent().getExtras().getInt("viewID")), Toast.LENGTH_LONG).show();
    }

}
