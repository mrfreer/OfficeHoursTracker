package com.freerschool.android.freerschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by dfree on 10/19/2017.
 */

public class BackgroundWorkerStudentTime extends AsyncTask<String, Void, String>
{
        Context context;
        AlertDialog alertDialog;
        public BackgroundWorkerStudentTime(Context c) {
        context = c;
        }
        private Activity activity;

        public BackgroundWorkerStudentTime(Activity activity, Context c){
        this.activity = activity;
        context = c;
        }
        private String writeData = "";

        protected String doInBackground(String ... params) {
        String result = "";
        String googleId = params[0];
        String studentId = params[1];
        String studentName = params[2];
                String timeEntered = params[3];
        String login_url = "http://freerschool.com/OfficeHoursTracker/insertStudentTime.php";
        try {
        String post_data = URLEncoder.encode("googleId", "UTF-8") + "=" + URLEncoder.encode(googleId, "UTF-8") +
                "&" + URLEncoder.encode("studentId", "UTF-8") + "=" + URLEncoder.encode(studentId, "UTF-8")
                + "&" + URLEncoder.encode("studentName", "UTF-8") + "=" + URLEncoder.encode(studentName, "UTF-8")
                + "&" +URLEncoder.encode("timeEntered", "UTF-8") + "=" + URLEncoder.encode(timeEntered, "UTF-8")
                ;

        URL url = new URL(login_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

        bufferedWriter.write(post_data);
            Log.v("insideStudentWorker1", post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
        "iso-8859-1"));
        String line = "";
        while((line = bufferedReader.readLine()) != null){
        Log.v("insideStudentWorker2", line);
        result += line;

        }
            writeData = result;
        bufferedReader.close();
        inputStream.close();


        httpURLConnection.disconnect();

        }
        catch (MalformedURLException e){
        e.printStackTrace();
                Log.v("errorhere", "do we get here?");
        }
        catch (IOException e){
                Log.v("errorhere2", "do we get here?");
        e.printStackTrace();
        }
        return result;
        }

@Override
protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Student visit recorded.");

        }

@Override
protected void onPostExecute(String s) {

        alertDialog.setMessage(s);
        alertDialog.show();

        //show response to server
        Log.v("getHere?", s);
        getWriteData();
        }


public String getWriteData(){
        return writeData;
        }
@Override
protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        }


}
