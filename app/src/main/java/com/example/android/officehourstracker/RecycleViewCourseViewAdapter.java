package com.example.android.officehourstracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dfreer on 10/19/2017.
 */

public class RecycleViewCourseViewAdapter extends RecyclerView.Adapter<RecycleViewCourseViewAdapter.ViewHolder> {

        Context context;

        List<Course> courses;
        private String googleName;

        public RecycleViewCourseViewAdapter(List<Course> getDataAdapter, Context context, String googleName){

            super();
            this.courses = getDataAdapter;
            this.context = context;
            this.googleName = googleName;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Course getDataAdapter1 =  courses.get(position);

            holder.CourseName.setText(getDataAdapter1.getCourseName());
            holder.CourseTime.setText(getDataAdapter1.getCourseTime());
        }

        @Override
        public int getItemCount() {

            return courses.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView CourseName, CourseTime;


            public ViewHolder(View itemView) {

                super(itemView);

                CourseName = (TextView) itemView.findViewById(R.id.textViewCourse) ;
                CourseTime = (TextView) itemView.findViewById(R.id.textViewTime);
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
            }

            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), ClassList.class);
                intent.putExtra("className", CourseName.getText().toString());
                intent.putExtra("googleId", googleName);
                context.startActivity(intent);
            }
        }
    }

