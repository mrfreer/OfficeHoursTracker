package com.freerschool.android.freerschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class UploadRoster extends AppCompatActivity {
    private TextView textViewSecret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_roster);
        textViewSecret = (TextView)findViewById(R.id.textViewRealSecretId);
    }

    public void generateUniqueId(View view){
        BackgroundWorkerSecretId backgroundWorkerSecretId = new BackgroundWorkerSecretId(this, this);
        Intent intent = getIntent();
        String googleIdentification = intent.getStringExtra("googleIdentification");
        String secret = "";
        try {
            secret = backgroundWorkerSecretId.execute(googleIdentification).get();
        }
        catch (ExecutionException | InterruptedException ei){
            ei.printStackTrace();
        }
        textViewSecret.setVisibility(View.VISIBLE);
        textViewSecret.setText(secret + " is the secret code.  Enter it exactly into the website above.");

       // Log.v("testing_testing", backgroundWorkerSecretId.getSecretId() + " is secret");
    }

}
