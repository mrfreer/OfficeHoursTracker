package com.freerschool.android.freerschool;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.freerschool.android.freerschool.helper.ItemTouchHelperAdapter;
import com.freerschool.android.freerschool.helper.OnStartDragListener;
import com.freerschool.android.freerschool.helper.ItemTouchHelperViewHolder;

import java.util.List;

/**
 * Created by davidfreer on 10/23/17.
 */

public class RecycleViewStudentTimes extends RecyclerView.Adapter<RecycleViewStudentTimes.ViewHolder>  implements ItemTouchHelperAdapter{
        Context context;

    List<StudentTime> studentTimes;
    private final OnStartDragListener mDragStartListener;

        private String googleId;
        public RecycleViewStudentTimes(List<StudentTime> getDataAdapter, Context context, String googleId, OnStartDragListener dragStartListener){

            super();

            this.studentTimes = getDataAdapter;
            this.googleId = googleId;
            this.context = context;
            mDragStartListener = dragStartListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_times, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }


    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    public void onItemDismiss(int position) {

        BackgroundWorkerDeleteStudentTime backgroundWorker = new BackgroundWorkerDeleteStudentTime(context);
        backgroundWorker.execute("x", "x", studentTimes.get(position).getT_id() + "");
        studentTimes.remove(position);
        notifyItemRemoved(position);
    }

        @Override
        public void onBindViewHolder(final RecycleViewStudentTimes.ViewHolder holder, int position) {

            StudentTime getDataAdapter1 =  studentTimes.get(position);
            holder.StudentId.setText(getDataAdapter1.getStudentID()+"");
            holder.StudentTime.setText(getDataAdapter1.getTimeStamp());
            holder.StudentName.setText(getDataAdapter1.getStudentName());

            holder.handleView.setOnTouchListener(new View.OnTouchListener(){



                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });


        }


        @Override
        public int getItemCount() {

            return studentTimes.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder{

            public TextView StudentName, StudentId, StudentTime;
            final public ImageView handleView;

            public ViewHolder(View itemView) {
                super(itemView);
                StudentName = (TextView) itemView.findViewById(R.id.textViewStudentNameTime) ;
                StudentId = (TextView) itemView.findViewById(R.id.textViewStudentId);
                StudentTime = (TextView) itemView.findViewById(R.id.textViewTime);
                handleView = (ImageView) itemView.findViewById(R.id.handle);
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
            }

            public void onClick(View v) {


            }

            @Override
            public void onItemSelected() {
                itemView.setBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void onItemClear() {
                itemView.setBackgroundColor(0);
            }
        }
    }

