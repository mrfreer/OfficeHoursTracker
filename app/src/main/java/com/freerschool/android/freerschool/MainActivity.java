
package com.freerschool.android.freerschool;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.freerschool.android.freerschool.helper.OnStartDragListener;
import com.freerschool.android.freerschool.helper.SimpleItemTouchHelperCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements OnStartDragListener, View.OnClickListener{

    private RecyclerView recyclerView;
    private TextView textViewSignIn;
    private ItemTouchHelper mItemTouchHelper;
    private RecycleViewCourseViewAdapter adapter;
    com.google.android.gms.common.SignInButton sign_in_button;
    private static final int RC_SIGN_IN = 9001;

    private List<Course> courses;
    String googleName = "";
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3837;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in_button = (com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);
        sign_in_button.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        courses = new ArrayList<>();
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.GET_ACCOUNTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);  //this doesn't work.
        if (acct != null) {
            String personName = acct.getDisplayName();
            Log.v("getgooglename", personName + " is the person name");
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Toast.makeText(getApplicationContext(), "Welcome " + personName, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_LONG).show();
        }
    }

    public void JSON_DATA_WEB_CALL(){
        HTTP_JSON_URL += "?googleId=" + googleName;
        Log.v("checking", "what is googleName " + googleName);
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

        adapter = new RecycleViewCourseViewAdapter(courses, this, googleName, this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

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
    GoogleSignInClient mGoogleSignInClient;



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

    public void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }


    private void updateUI(GoogleSignInAccount account) {
        if(account != null){
            Toast.makeText(this,"Welcome " + account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Welcome guest!", Toast.LENGTH_SHORT).show();
        }

    }


    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onClick(View v) {
        signIn();
        JSON_DATA_WEB_CALL();
    }

    private void signIn() {
        Log.v("checking", "In signin()");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("checking", "In onActivityResult()");
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            googleName = account.getEmail().toString();
            Log.v("checking", googleName + " googleName.");
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failedHERE", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "You are ready to keep track of your student visits.", Toast.LENGTH_LONG).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Doesn't work!", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
