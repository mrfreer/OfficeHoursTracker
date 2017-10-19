package com.example.android.officehourstracker;

/**
 * Created by dfreer on 10/13/2017.
 */

public class StudentTime {
    private String studentID;
    private String timeStamp;
    private String googleId;

    public StudentTime(String studentID, String timeStamp, String googleId) {
        this.studentID = studentID;
        this.timeStamp = timeStamp;
        this.googleId = googleId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
