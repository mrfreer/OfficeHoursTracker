package com.example.android.officehourstracker;

/**
 * Created by dfreer on 10/13/2017.
 */

public class StudentTime {
    private String studentName;
    private String studentID;
    private String timeStamp;
    private String googleId;

    public StudentTime(){

    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public StudentTime(String studentID, String timeStamp, String googleId) {
        this.studentID = studentID;
        this.timeStamp = timeStamp;
        this.googleId = googleId;
    }

    public String getStudentName() {
        return studentName;
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
