package com.example.android.officehourstracker;

/**
 * Created by dfreer on 10/5/2017.
 */

public class Student {
    private String studentID;
    private String name;


    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }
}
