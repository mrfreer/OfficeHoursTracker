package com.freerschool.android.freerschool;

/**
 * Created by dfreer on 10/13/2017.
 */

public class StudentTime {
    private String studentName;
    private String studentID;
    private String timeStamp;
    private String googleId;
    private int t_id;

    public StudentTime(){

    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
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

    public StudentTime(String studentID, String timeStamp, String googleId, int t_id) {
        this.studentID = studentID;
        this.timeStamp = timeStamp;
        this.googleId = googleId;
        this.t_id = t_id;
    }

    public int getT_id(){
        return t_id;
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
