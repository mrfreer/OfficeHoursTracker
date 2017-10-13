package com.example.android.officehourstracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentVisitors extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_visitors);
        ArrayList<String> arrayList = readData();
        Toast.makeText(getApplicationContext(), arrayList.get(0).toString(), Toast.LENGTH_LONG).show();

    }

    public ArrayList<String> readData(){
        Log.i("WRITING_MDC", " READING DATABASE");
        SQLiteDatabase db = new ClassListDbHelper(this).getReadableDatabase();
        String [] projection = {
                StudentTimesDB.COLUMN_NAME_STUDENT_ID
                ,StudentTimesDB.COLUMN_TIMESTAMP
        };

        Cursor cursor = db.query(StudentTimesDB.TABLE_NAME, projection, StudentTimesDB.COLUMN_NAME_STUDENT_ID + ">0",
                null, null, null, null);
        List itemIds = new ArrayList<>();
        ArrayList<String> studentNamesTimes = new ArrayList<>();
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow(StudentTimesDB.COLUMN_NAME_STUDENT_ID));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(StudentTimesDB.COLUMN_TIMESTAMP));
            studentNamesTimes.add(id + "\n" + time + "\n");
        }
        cursor.close();
        Log.i("testing_testing", studentNamesTimes.size() + "");
        int counter = 0;
        return studentNamesTimes;
    }


}
