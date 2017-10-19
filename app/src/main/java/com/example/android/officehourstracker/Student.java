package com.example.android.officehourstracker;

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

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public String getGoogleId(){ return googleId;}

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }
}
