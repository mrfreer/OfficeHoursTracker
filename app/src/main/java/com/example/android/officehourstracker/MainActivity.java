package com.example.android.officehourstracker;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private List<Course> courses;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("com.google");
        final CharSequence items[] = new CharSequence[accounts.length];
        int num = 0;
        for(Account account : accounts){
            items[num] = account.name;
        }
        if(accounts.length > 0){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("value is ", items[i].toString());
                }
            });
            adb.setNegativeButton("Cancel", null);
            adb.setTitle("Pick an account:");
            adb.show();
        }

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

    public void showStudentTime(View view){
        Intent intent = new Intent(view.getContext(), ViewStudentVisitors.class);
        startActivity(intent);

    }
}
