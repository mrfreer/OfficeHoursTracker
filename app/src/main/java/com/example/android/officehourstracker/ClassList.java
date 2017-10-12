package com.example.android.officehourstracker;

import android.content.ContentValues;
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
        students = readStudentsFromDB();

        adapter = new MyAdapterStudents(students, this);
        recyclerView.setAdapter(adapter);

        textViewClassName = (TextView) findViewById(R.id.textViewClass);
        textViewClassName.setText(getIntent().getExtras().getString("className"));
        String courseIdentification = Integer.toString(getIntent().getExtras().getInt("classID")) + " is the class ID.";
        Toast.makeText(getApplicationContext(), courseIdentification, Toast.LENGTH_LONG).show();

    }


    public ArrayList<Student> readStudentsFromDB(){

        SQLiteDatabase db = new ClassListDbHelper(this).getReadableDatabase();
        String [] projection = {
                StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME
                ,StudentDB.StudentEntry.COLUMN_NAME_CLASS_ID
                ,StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID
                //,StudentDB.StudentEntry.COLUMN_TIME
        };
        String [] selectionArgs = {"students"};

        Cursor cursor = db.query(StudentDB.StudentEntry.TABLE_NAME, projection, StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID + ">0",
                null, null, null, null);
        List itemIds = new ArrayList<>();
        ArrayList<Student> studentNames = new ArrayList<>();

        while(cursor.moveToNext()){
            String studentId, name;
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID));
            studentId = cursor.getString(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID));
            name = cursor.getString(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME));
            studentNames.add(new Student(studentId, name));
            itemIds.add(itemId);
        }
        return studentNames;
    }

    public void readFromStudents(View view){

        //TODO: READ FROM DATABASE AND WRITE TO CARDS!  VERY IMPORTANT!
        Log.i("WRITING_MDC", " READING DATABASE");
        SQLiteDatabase db = new ClassListDbHelper(this).getReadableDatabase();
        String [] projection = {
                StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME
                ,StudentDB.StudentEntry.COLUMN_NAME_CLASS_ID
                ,StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID
                //,StudentDB.StudentEntry.COLUMN_TIME
        };


        //String sortOrder = StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID + " ASC";
        String [] selectionArgs = {"students"};

        Cursor cursor = db.query(StudentDB.StudentEntry.TABLE_NAME, projection, StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID + ">0",
                null, null, null, null);
        List itemIds = new ArrayList<>();
        List studentNames = new ArrayList<>();
        while(cursor.moveToNext()){
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID));
            studentNames.add(cursor.getString(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME)));
            itemIds.add(itemId);
        }
        cursor.close();
        Toast.makeText(getApplicationContext(), studentNames.size() + " NUM STUDENTS", Toast.LENGTH_LONG).show();
        int counter = 0;
        for(Object a : studentNames){
            Toast.makeText(getApplicationContext(), a.toString() + " " + itemIds.get(counter), Toast.LENGTH_LONG).show();
            counter++;
        }


    }


}
