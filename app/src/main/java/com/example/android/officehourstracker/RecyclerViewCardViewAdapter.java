package com.example.android.officehourstracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import java.util.List;

public class RecyclerViewCardViewAdapter extends RecyclerView.Adapter<RecyclerViewCardViewAdapter.ViewHolder> {

    Context context;

    List<Student> students;

    public RecyclerViewCardViewAdapter(List<Student> getDataAdapter, Context context){

        super();

        this.students = getDataAdapter;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Student getDataAdapter1 =  students.get(position);

        holder.StudentName.setText(getDataAdapter1.getName());
        holder.StudentId.setText(getDataAdapter1.getStudentID());
    }

    @Override
    public int getItemCount() {

        return students.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView StudentName, StudentId;


        public ViewHolder(View itemView) {

            super(itemView);

            StudentName = (TextView) itemView.findViewById(R.id.textViewStudent) ;
            StudentId = (TextView) itemView.findViewById(R.id.textViewID);

        }
    }
}
