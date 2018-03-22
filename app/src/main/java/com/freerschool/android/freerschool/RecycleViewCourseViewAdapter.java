package com.freerschool.android.freerschool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freerschool.android.freerschool.helper.ItemTouchHelperAdapter;
import com.freerschool.android.freerschool.helper.OnStartDragListener;

import java.util.List;

/**
 * Created by dfreer on 10/19/2017.
 */

public class RecycleViewCourseViewAdapter extends RecyclerView.Adapter<RecycleViewCourseViewAdapter.ViewHolder> implements ItemTouchHelperAdapter {

        Context context;

        List<Course> courses;
        private String googleName;
        private final OnStartDragListener mDragStartListener;

        public RecycleViewCourseViewAdapter(List<Course> getDataAdapter, Context context, String googleName, OnStartDragListener dragStartListener){

            super();
            this.courses = getDataAdapter;
            this.context = context;
            this.googleName = googleName;
            mDragStartListener = dragStartListener;
        }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    public void onItemDismiss(int position){
            BackgroundWorkerDeleteClass backgroundWorker = new BackgroundWorkerDeleteClass(context);
            backgroundWorker.execute("x", "x", courses.get(position).getID() + "");
            courses.remove(position);
            notifyItemRemoved(position);
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
            holder.CourseId.setText(getDataAdapter1.getID() + "");
        }

        @Override
        public int getItemCount() {

            return courses.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView CourseName, CourseTime, CourseId;


            public ViewHolder(View itemView) {

                super(itemView);

                CourseName = (TextView) itemView.findViewById(R.id.textViewCourse) ;
                CourseTime = (TextView) itemView.findViewById(R.id.textViewTime);
                CourseId = (TextView) itemView.findViewById(R.id.textViewCourseId);
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
            }

            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), ClassList.class);
                intent.putExtra("classId", CourseId.getText().toString());
                intent.putExtra("googleId", googleName);
                context.startActivity(intent);
            }
        }
    }

