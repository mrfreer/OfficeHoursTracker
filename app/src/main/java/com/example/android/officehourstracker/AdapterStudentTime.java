package com.example.android.officehourstracker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dfreer on 10/13/2017.
 */

public class AdapterStudentTime extends RecyclerView.Adapter<AdapterStudentTime.ViewHolder> {
    List<StudentTime> studentTimes;
    private Context context;

    public AdapterStudentTime(List<StudentTime> studentTimes, Context context) {
        this.studentTimes = studentTimes;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_times, parent, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(AdapterStudentTime.ViewHolder holder, int position) {
        StudentTime studentTime = studentTimes.get(position);
        holder.textViewStudent.setText(studentTime.getStudentID());
        holder.textViewTime.setText(studentTime.getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewStudent;
        public TextView textViewTime;
        CardView cv;
        Context getContext;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            textViewStudent = (TextView) itemView.findViewById(R.id.textViewStudentNameTime);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);


        }
    }
}
