package com.freerschool.android.freerschool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dfreer on 10/5/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Course> professorCourses;
    private Context context;
    int globalPosition = 0;
    public MyAdapter(List<Course> professorCourses, Context context) {
        this.professorCourses = professorCourses;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = professorCourses.get(position);
        holder.textViewCourse.setText(course.getCourseName());
        holder.textViewTime.setText(course.getCourseTime());
        globalPosition = position;
    }

    @Override
    public int getItemCount() {
        return professorCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewCourse;
        public TextView textViewTime;
        public ViewHolder(View itemView){
            super(itemView);
            textViewCourse = (TextView) itemView.findViewById(R.id.textViewCourse);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int viewID = v.getId();
            final Intent intent = new Intent(v.getContext(), ClassList.class);
            intent.putExtra("className", textViewCourse.getText().toString());
            intent.putExtra("classID", professorCourses.get(globalPosition).getID());
            intent.putExtra("viewID", viewID);
            context.startActivity(intent);
        }

    }
}
