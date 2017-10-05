package com.example.android.officehourstracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfreer on 10/5/2017.
 */

public class Course {
    private String courseName;
    private String courseTime;
    private int ID;
    //should we read this in?
    private List<Student> students;

    public Course(String courseName, String courseTime) {

        this.courseName = courseName;
        this.courseTime = courseTime;
        students = new ArrayList<>();
        students.add(new Student("123", "Billy Johnson"));
        students.add(new Student("234", "Jon Smith"));
    }

    //TODO: Add student names from file!

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseTime='" + courseTime + '\'' +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
