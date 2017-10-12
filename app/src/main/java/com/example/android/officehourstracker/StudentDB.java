package com.example.android.officehourstracker;

import android.provider.BaseColumns;

/**
 * Created by dfreer on 10/11/2017.
 */

public final class StudentDB {
    private StudentDB(){}

    public static class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "students";
        public static final String COLUMN_NAME_STUDENT_ID = "studentID";
        public static final String COLUMN_NAME_TITLE_STUDENT_NAME = "studentName";
        public static final String COLUMN_NAME_CLASS_ID = "classID";

    }

}
