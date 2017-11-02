
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

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.VolleyLog;
        import com.android.volley.toolbox.JsonArrayRequest;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.JsonRequest;
        import com.android.volley.toolbox.StringRequest;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewSignIn;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter mysqlAdapter;
    private List<Course> courses;
    String googleName = "";
    ArrayList<String> gUsernameList = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        AccountManager accountManager = (AccountManager)getSystemService(getApplicationContext().ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Log.v("writing", accounts.length + "");

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
                googleName = gUsernameList.get(position);

            }
        });

        builder.setView(lv);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        final Dialog dialog = builder.create();
        //if(gUsernameList.size() > 1)
        //    dialog.show();
        googleName = gUsernameList.get(0);

        if(gUsernameList.size() == 0){
            Toast.makeText(getApplicationContext(), "You aren't logged into an account", Toast.LENGTH_LONG).show();
        }

        textViewSignIn.setText(googleName);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courses = new ArrayList<>();
        JSON_DATA_WEB_CALL();

    }

    public void JSON_DATA_WEB_CALL(){
        HTTP_JSON_URL += "?googleId=" + googleName;
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
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }



    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            Course GetDataAdapter2 = new Course();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setID(json.getInt(GET_CLASS_ID));
                GetDataAdapter2.setCourseName(json.getString(GET_JSON_FROM_SERVER_NAME));
                GetDataAdapter2.setCourseTime(json.getString(GET_JSON_CLASS_MEETING_TIMES));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            courses.add(GetDataAdapter2);
            Log.v("addingCourse", GetDataAdapter2.getCourseName());
        }

        mysqlAdapter = new RecycleViewCourseViewAdapter(courses, this, googleName);
        recyclerView.setAdapter(mysqlAdapter);

    }

    JsonArrayRequest jsonArrayRequest;
    String GET_JSON_FROM_SERVER_NAME = "className";
    String GET_JSON_CLASS_MEETING_TIMES = "meetingDaysAndTime";
    String GET_CLASS_ID = "classId";
    String GET_JSON_ID = "studentId";
    String HTTP_JSON_URL = "http://freerschool.com/OfficeHoursTracker/getClasses.php";
    RequestQueue requestQueue;
    View ChildView ;
    int GetItemPosition ;
    ArrayList<String> classNames;
    ArrayList<String> meetingDaysAndTime;




    public void showStudentTime(View view){
        Intent intent = new Intent(view.getContext(), ViewStudentVisitors.class);
        intent.putExtra("googleId", googleName);
        intent.putExtra("dontinsert", "yes");
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
