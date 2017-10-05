package com.example.android.officehourstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private List<Course> courses;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courses = new ArrayList<>();


        courses.add(new Course("Java", "M-W 6:00pm-7:40pm"));
        courses.add(new Course("C++", "M-W 2:20pm-4:00pm"));
        courses.add(new Course("Android Programming", "T-TH 6:15-9:30pm"));
        courses.add(new Course("CGS 1060", "F 7:00-10:20am"));
        courses.add(new Course("CGS 1060", "T-TH 10:40-12:20pm"));
        for(int i = 0; i < courses.size(); i++){
            courses.get(i).setID(i);
        }

         //set some mock IDs...I'll think of a better system later.  Maybe use a database!
        //for now I will hard code the classes
        //TODO: allow user to edit the classes and save it in a database!

        adapter = new MyAdapter(courses, this);
        recyclerView.setAdapter(adapter);


    }
}
