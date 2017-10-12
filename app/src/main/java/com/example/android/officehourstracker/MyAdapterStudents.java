package com.example.android.officehourstracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dfreer on 10/9/2017.
 */

public class MyAdapterStudents extends RecyclerView.Adapter<MyAdapterStudents.ViewHolder>  {
    private List<Student> studentsInClass;
    private Context context;

    public MyAdapterStudents(List<Student> studentsInClass, Context context) {
        this.studentsInClass = studentsInClass;
        this.context = context;
    } //constructor

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);

        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(MyAdapterStudents.ViewHolder holder, int position) {
        Student student = studentsInClass.get(position);
        holder.textViewStudent.setText(student.getName());
        holder.textViewID.setText("" + student.getStudentID());
    }

    @Override
    public int getItemCount() {
        return studentsInClass.size();
    }


    RecyclerView.Adapter adapter;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewStudent;
        public TextView textViewID;
        CardView cv;

        Context getContext;

        public ViewHolder(View itemView, Context context){
            super(itemView);
            getContext = context;
            textViewStudent = (TextView) itemView.findViewById(R.id.textViewStudent);
            textViewID = (TextView) itemView.findViewById(R.id.textViewID);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        public void setContext(Context con){
            getContext = con;
        }
        @Override
        public void onClick(View v) {
            Log.i("position-of-clicked", String.valueOf(getAdapterPosition()));
            Log.i("position-of-clicked", String.valueOf(textViewStudent.getText()));
            Log.i("position-of-clicked", String.valueOf(textViewID.getText()));

            SQLiteDatabase db = new ClassListDbHelper(getContext).getWritableDatabase();
            ClassListDbHelper classListDbHelper = new ClassListDbHelper(getContext);  //sending this instead of getContext()

            //https://stackoverflow.com/questions/10641144/difference-between-getcontext-getapplicationcontext-getbasecontext-and

            ContentValues values = new ContentValues();

            values.put(StudentTimesDB.COLUMN_NAME_STUDENT_ID, textViewID.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("EST"));
            values.put(StudentTimesDB.COLUMN_TIMESTAMP, sdf.format(new Date()).toString());


            //TODO: Get values from which element was clicked...
            long newRowId = db.insert(StudentTimesDB.TABLE_NAME, null, values);
            Log.i("mdc_android", newRowId + "");

        }

    }
}
