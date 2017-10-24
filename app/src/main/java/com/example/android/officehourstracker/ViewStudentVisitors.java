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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ViewStudentVisitors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<StudentTime> studentTimes;
    private RecyclerView.Adapter adapter;
    Intent intent;
    String curTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_visitors);
        studentTimes = new ArrayList<>();
        intent = getIntent();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        curTime = sdf.format(new Date()).toString();
        BackgroundWorkerStudentTime backgroundWorkerStudentTime = new BackgroundWorkerStudentTime(this, this);
        backgroundWorkerStudentTime.execute(intent.getStringExtra("googleId"), intent.getStringExtra("studentID"),
                "JOHN DOE", curTime
                );
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewTimes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        JSON_DATA_WEB_CALL();

    }

    public void JSON_DATA_WEB_CALL(){

        HTTP_JSON_URL += "?googleId=" + intent.getStringExtra("googleId");
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                HTTP_JSON_URL,
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
                }
        );

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {
//            Toast.makeText(getApplicationContext(), array.length(), Toast.LENGTH_LONG).show();

            StudentTime GetDataAdapter2 = new StudentTime();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setTimeStamp(json.getString(GET_JSON_TIME));
                GetDataAdapter2.setStudentID(json.getString(GET_JSON_ID));
                Log.v("writing_here", json.getString(GET_JSON_ID));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            studentTimes.add(GetDataAdapter2);
            Log.v("addingStudentTime", GetDataAdapter2.getStudentID());
        }
        Log.v("dowehavegoogleid", intent.getStringExtra("googleId"));
        adapter = new RecycleViewStudentTimes(studentTimes, this, intent.getStringExtra("googleId"));

        recyclerView.setAdapter(adapter);

    }

    public void returnHome(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }

    JsonArrayRequest jsonArrayRequest;
    String GET_JSON_TIME = "timeEntered";
    String GET_JSON_ID = "studentId";
    String HTTP_JSON_URL = "http://freerschool.com/OfficeHoursTracker/getStudentTime.php";
    RequestQueue requestQueue;



}
