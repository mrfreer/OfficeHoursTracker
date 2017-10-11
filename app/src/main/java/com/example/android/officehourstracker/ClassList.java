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

    public void insertDataStudents(View view){
        ClassListDbHelper classListDbHelper = new ClassListDbHelper(this);  //sending this instead of getContext()
        //https://stackoverflow.com/questions/10641144/difference-between-getcontext-getapplicationcontext-getbasecontext-and
        SQLiteDatabase db = classListDbHelper.getWritableDatabase();
        Log.i("WRITING_MDC", " WRITING DATABASE");
        //TODO fix this!
        ContentValues values = new ContentValues();
        values.put(StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME, "Billy Johnston");
        values.put(StudentDB.StudentEntry.COLUMN_NAME_CLASS_ID, 5);
        values.put(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID, 201);
        values.put(StudentDB.StudentEntry.COLUMN_TIME, "12/21/2017");  //does the order matter?  probably not...
        //some dummy values for now!
        //TODO: Get values from which element was clicked...
        long newRowId = db.insert(StudentDB.StudentEntry.TABLE_NAME, null, values);
        Toast.makeText(getApplicationContext(), newRowId + " ", Toast.LENGTH_LONG).show();
    }

    public void readFromStudents(View view){
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
