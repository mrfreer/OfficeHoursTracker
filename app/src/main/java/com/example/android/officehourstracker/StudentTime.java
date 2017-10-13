package com.example.android.officehourstracker;

/**
 * Created by dfreer on 10/13/2017.
 */

public class StudentTime {
    private String studentID;
    private String timeStamp;

    public StudentTime(String studentID, String timeStamp) {
        this.studentID = studentID;
        this.timeStamp = timeStamp;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
