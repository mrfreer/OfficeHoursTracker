package com.example.android.officehourstracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentVisitors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Student> students;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_visitors);
        ArrayList<StudentTime> arrayList = readDataLocal();
        adapter = new AdapterStudentTime(arrayList, this);
        Intent intent = getIntent();

        BackgroundWorkerStudentTime backgroundWorkerStudentTime = new BackgroundWorkerStudentTime(this, this);
        backgroundWorkerStudentTime.execute(intent.getStringExtra("googleId"), intent.getStringExtra("studentID"),
                intent.getStringExtra("studentName"));
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewTimes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public ArrayList<StudentTime> readDataLocal(){
        Log.i("WRITING_MDC", " READING DATABASE");
        SQLiteDatabase db = new ClassListDbHelper(this).getReadableDatabase();
        String [] projection = {
                StudentTimesDB.COLUMN_NAME_STUDENT_ID
                ,StudentTimesDB.COLUMN_TIMESTAMP
        };

        Cursor cursor = db.query(StudentTimesDB.TABLE_NAME, projection, StudentTimesDB.COLUMN_NAME_STUDENT_ID + ">0",
                null, null, null, null);
        List itemIds = new ArrayList<>();
        ArrayList<StudentTime> studentNamesTimes = new ArrayList<>();
        Intent prevIntent = getIntent();
        while(cursor.moveToNext()){

            String id = cursor.getString(cursor.getColumnIndexOrThrow(StudentTimesDB.COLUMN_NAME_STUDENT_ID));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(StudentTimesDB.COLUMN_TIMESTAMP));
            studentNamesTimes.add(new StudentTime(id, time, prevIntent.getStringExtra("googleId")));
        }
        cursor.close();
        Log.i("testing_testing", studentNamesTimes.size() + "");
        int counter = 0;
        return studentNamesTimes;
    }

    public ArrayList<StudentTime> readMySQLData(){
        ArrayList<StudentTime> arrayList = new ArrayList<>();


        return arrayList;
    }


}
