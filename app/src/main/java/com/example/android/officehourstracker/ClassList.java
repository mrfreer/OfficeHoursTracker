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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassList extends AppCompatActivity {


    TextView textViewClassName;
    private RecyclerView recyclerView;
    private List<Student> students;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter mysqlAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        studentNames = new ArrayList<>();
        studentId = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewStudents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // students = readStudentsFromDBLocal();
        students = new ArrayList<>();
        JSON_DATA_WEB_CALL();
        adapter = new MyAdapterStudents(students, this);
       // recyclerView.setAdapter(adapter);

        textViewClassName = (TextView) findViewById(R.id.textViewClass);
        textViewClassName.setText(getIntent().getExtras().getString("className"));
        String courseIdentification = Integer.toString(getIntent().getExtras().getInt("classID")) + " is the class ID.";
        Toast.makeText(getApplicationContext(), courseIdentification, Toast.LENGTH_LONG).show();

    }


    public ArrayList<Student> readStudentsFromDBLocal(){

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
        ArrayList<Student> studentNames1 = new ArrayList<>();

        while(cursor.moveToNext()){
            String studentId, name;
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID));
            studentId = cursor.getString(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID));
            name = cursor.getString(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME));
            studentNames1.add(new Student(studentId, name));
            itemIds.add(itemId);
        }
        return studentNames1;
    }

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("network_error", error.getNetworkTimeMs() + "");
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {
//            Toast.makeText(getApplicationContext(), array.length(), Toast.LENGTH_LONG).show();

            Student GetDataAdapter2 = new Student();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                GetDataAdapter2.setStudentID(json.getString(GET_JSON_ID));
                studentNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));
                studentId.add(json.getString(GET_JSON_ID));
                //students.add(GetDataAdapter2);
                //cool!

            } catch (JSONException e) {

                e.printStackTrace();
            }
            students.add(GetDataAdapter2);
            Log.v("addingStudent", GetDataAdapter2.getStudentID());
        }

        mysqlAdapter = new RecyclerViewCardViewAdapter(students, this);

        recyclerView.setAdapter(mysqlAdapter);

    }

    JsonArrayRequest jsonArrayRequest;
    String GET_JSON_FROM_SERVER_NAME = "studentLast";
    String GET_JSON_ID = "studentId";
    String HTTP_JSON_URL = "http://freerschool.com/OfficeHoursTracker/getStudents.php";
    RequestQueue requestQueue;
    View ChildView ;
    int GetItemPosition ;
    ArrayList<String> studentNames;
    ArrayList<String> studentId;


    public ArrayList<Student> readStudentsMySQL(){
        //TODO MOST important, I need to use JSON to create the cards:
        //TODO https://androidjson.com/recyclerview-json-listview-example/

        //http://freerschool.com/OfficeHoursTracker/getStudents.php
        ArrayList<Student> arrayList = new ArrayList<>();



        return arrayList;
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
        List studentNames1 = new ArrayList<>();
        while(cursor.moveToNext()){
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID));
            studentNames1.add(cursor.getString(cursor.getColumnIndexOrThrow(StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME)));
            itemIds.add(itemId);
        }
        cursor.close();
        Toast.makeText(getApplicationContext(), studentNames1.size() + " NUM STUDENTS", Toast.LENGTH_LONG).show();
        int counter = 0;
        for(Object a : studentNames1){
            Toast.makeText(getApplicationContext(), a.toString() + " " + itemIds.get(counter), Toast.LENGTH_LONG).show();
            counter++;
        }


    }


}
