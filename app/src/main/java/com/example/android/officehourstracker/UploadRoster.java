package com.example.android.officehourstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class UploadRoster extends AppCompatActivity {
    private TextView textViewSecret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_roster);
        textViewSecret = (TextView)findViewById(R.id.textViewRealSecretId);
    }

    public void generateUniqueId(View view){
        BackgroundWorkerSecretId backgroundWorkerSecretId = new BackgroundWorkerSecretId(this);
        Intent intent = getIntent();
        String googleIdentification = intent.getStringExtra("googleIdentification");
        backgroundWorkerSecretId.execute(googleIdentification);
        textViewSecret.setText(backgroundWorkerSecretId.getSecretId());
        Log.v("testing_testing", backgroundWorkerSecretId.getSecretId() + " is secret");
    }

}
