package com.freerschool.android.freerschool;

/**
 * Created by dfreer on 10/5/2017.
 */

public class Student {
    private String studentID;
    private String name;
    private String googleId;

    public Student(String studentID, String name, String googleId) {
        this.studentID = studentID;
        this.name = name;
        this.googleId = googleId;
    }

    public Student(){

    }

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleId(){ return googleId;}

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }
}
