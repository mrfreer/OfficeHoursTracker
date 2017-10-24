package com.example.android.officehourstracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by davidfreer on 10/23/17.
 */

public class RecycleViewStudentTimes extends RecyclerView.Adapter<RecycleViewStudentTimes.ViewHolder> {

        Context context;

        List<StudentTime> studentTimes;
        private String googleId;
        public RecycleViewStudentTimes(List<StudentTime> getDataAdapter, Context context, String googleId){

            super();

            this.studentTimes = getDataAdapter;
            this.googleId = googleId;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_times, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecycleViewStudentTimes.ViewHolder holder, int position) {

            StudentTime getDataAdapter1 =  studentTimes.get(position);
            //TODO make a call to the database to find out the name using the student ID
            holder.StudentId.setText(getDataAdapter1.getStudentID()+"");
            holder.StudentTime.setText(getDataAdapter1.getTimeStamp());
            holder.StudentName.setText(getDataAdapter1.getStudentName());
        }

        @Override
        public int getItemCount() {

            return studentTimes.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView StudentName, StudentId, StudentTime;


            public ViewHolder(View itemView) {

                super(itemView);

                StudentName = (TextView) itemView.findViewById(R.id.textViewStudentNameTime) ;
                StudentId = (TextView) itemView.findViewById(R.id.textViewStudentId);
                StudentTime = (TextView) itemView.findViewById(R.id.textViewTime);
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
            }

            public void onClick(View v) {
                Log.v("weclicked", "goodclick");
            }
        }
    }

