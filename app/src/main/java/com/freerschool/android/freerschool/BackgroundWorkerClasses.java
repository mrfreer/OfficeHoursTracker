package com.freerschool.android.freerschool;

import android.app.Activity;
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
 * Created by dfreer on 10/19/2017.
 */

public class BackgroundWorkerClasses extends AsyncTask<String, Void, String>
{
    Context context;
    AlertDialog alertDialog;
    public BackgroundWorkerClasses(Context c) {
        context = c;
    }
    private Activity activity;

    public BackgroundWorkerClasses(Activity activity, Context c){
        this.activity = activity;
        context = c;
    }
    private String secId = "";

    protected String doInBackground(String ... params) {
        String result = "";
        String googleId = params[0];
        Log.v("AgainWriting", googleId);
        String login_url = "http://freerschool.com/OfficeHoursTracker/getClasses.php";
        try {

            String post_data = URLEncoder.encode("googleId", "UTF-8") + "=" + URLEncoder.encode(googleId, "UTF-8");

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
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
            while((line = bufferedReader.readLine()) != null){
                //Log.v("testingWriting", line);
                result += line;

            }
            secId = result;
            bufferedReader.close();
            inputStream.close();

            httpURLConnection.disconnect();

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String s) {

//        alertDialog.setMessage(s);
//        alertDialog.show();

        //show response to server
        //Log.v("testingWriting", getSecretId());
        getSecretId();
    }


    public String getSecretId(){
        return secId;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
