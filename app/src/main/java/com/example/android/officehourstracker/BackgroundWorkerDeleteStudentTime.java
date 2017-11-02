package com.example.android.officehourstracker;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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
 * Created by dfreer on 11/2/2017.
 */


public class BackgroundWorkerDeleteStudentTime extends AsyncTask<String, Void, String>
{
    Context context;
    AlertDialog alertDialog;

    public BackgroundWorkerDeleteStudentTime(Context c) {
        context = c;
    }



    protected String doInBackground(String ... params) {
        String result = "";
        String googleId = params[0];
        Log.v("AgainWriting", googleId);
        String timeStamp = params[1];
        String toDelete;
        int t_id = Integer.parseInt(params[2]);


        String login_url = "http://freerschool.com/OfficeHoursTracker/deleteStudentTime.php";
            Log.v("whatisit", login_url);
            try {
                String post_data = URLEncoder.encode("t_id", "UTF-8") + "=" + URLEncoder.encode(t_id + "", "UTF-8");
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                        "iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    Log.v("deleting", line);
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String s) {
        alertDialog.setMessage(s);
        //show response to server
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
