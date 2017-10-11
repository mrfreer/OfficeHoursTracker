package com.example.android.officehourstracker;

import android.provider.BaseColumns;

/**
 * Created by dfreer on 10/11/2017.
 */

public final class CourseDB {
    private CourseDB(){}

    public static class CourseEntry implements BaseColumns{
        public static final String COLUMN_NAME_CLASS_NAME = "className";
        public static final String COLUMN_NAME_CLASS_ID = "classID";
        public static final String TABLE_NAME = "courses";
    }
}
