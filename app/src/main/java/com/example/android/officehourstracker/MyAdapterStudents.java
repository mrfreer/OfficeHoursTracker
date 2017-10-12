package com.example.android.officehourstracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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
        return new ViewHolder(v);
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

        public ViewHolder(View itemView){
            super(itemView);
            textViewStudent = (TextView) itemView.findViewById(R.id.textViewStudent);
            textViewID = (TextView) itemView.findViewById(R.id.textViewID);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("position-of-clicked", String.valueOf(getAdapterPosition()));
            Log.i("position-of-clicked", String.valueOf(textViewStudent.getText()));
            Log.i("position-of-clicked", String.valueOf(textViewID.getText()));
        }

    }
}
