package com.example.android.officehourstracker;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dfreer on 10/11/2017.
 */

public class ClassListDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES_COURSE = "CREATE TABLE " + CourseDB.CourseEntry.TABLE_NAME +
            " (" + CourseDB.CourseEntry.COLUMN_NAME_CLASS_ID + " INTEGER PRIMARY KEY, " +
            CourseDB.CourseEntry.COLUMN_NAME_CLASS_NAME + " TEXT )";

    private static final String SQL_CREATE_ENTRIES_STUDENTS = "CREATE TABLE " + StudentDB.StudentEntry.TABLE_NAME +
            " (" + StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID + " INTEGER PRIMARY KEY, " +
            StudentDB.StudentEntry.COLUMN_TIME + " TEXT, " +
            StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME + " TEXT, " + StudentDB.StudentEntry.COLUMN_NAME_CLASS_ID +
            " INTEGER)";

    private static final String SQL_DELETE_ENTRIES_STUDENT = "DROP TABLE IF EXISTS " + StudentDB.StudentEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_COURSE = "DROP TABLE IF EXISTS " + CourseDB.CourseEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Students.db";

    public ClassListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_COURSE);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_STUDENTS);
        Log.i("WRITING_MDC", " MAKING DATABASE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_COURSE);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_STUDENT);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }


}
