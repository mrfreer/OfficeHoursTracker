package com.example.android.officehourstracker;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewSignIn;
    private RecyclerView.Adapter adapter;
    private List<Course> courses;
    String googleName;
    ArrayList<String> gUsernameList = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        //TODO: request user to add permissions when they start the app!

        gUsernameList.clear();
//loop
        for (Account account : accounts) {
            gUsernameList.add(account.name);
            Log.i("testingtesting", account.name);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose your gmail-account");

        ListView lv = new ListView(this);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1, android.R.id.text1,
                        gUsernameList);

        lv.setAdapter(adapter1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent,View view,int position,long
                    id)
            {
                Log.d("You-select-gmail", gUsernameList.get(position));
            }
        });

        builder.setView(lv);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        final Dialog dialog = builder.create();
        if(gUsernameList.size() > 1)
            dialog.show();



        googleName = gUsernameList.get(0);
        textViewSignIn.setText("You are signed in as: " + googleName);
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

    public void OnLogin(View view){
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        //this refers to the context in Java/Android
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        String curTime = sdf.format(new Date()).toString();
        Log.v("writeGoogleName", googleName);
        backgroundWorker.execute(googleName, curTime);
    }

    public void uploadInfo(View view){
        Intent intent = new Intent(view.getContext(), UploadRoster.class);
        intent.putExtra("googleIdentification", googleName);
        startActivity(intent);
    }
}
