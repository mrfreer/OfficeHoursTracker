package com.freerschool.android.freerschool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by dfreer on 10/12/2017.
 */

public class StudentsDBAdapter {
    private ClassListDbHelper mDbHelper;

    private SQLiteDatabase mDb;
    private Context mCtx;

    public StudentsDBAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public StudentsDBAdapter open() throws SQLException {
        mDbHelper = new ClassListDbHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        if(mDbHelper != null){
            mDbHelper.close();
        }
    }

    public long addStudentJonSmith(){
        ContentValues initialValues = new ContentValues();
        initialValues.put(StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID, "123");
        initialValues.put(StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME, "John Smith");
        return mDb.insert(StudentDB.StudentEntry.TABLE_NAME, null, initialValues);

    }

    public Cursor fetchStudentsByName(String inputText) throws SQLException {
        Log.w("getting_students", inputText);
        Cursor mCursor = null;
        if(inputText == null || inputText.length() == 0){
            mCursor = mDb.query(StudentDB.StudentEntry.TABLE_NAME, new String[] {StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID, StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME},
                    null, null, null, null, null);
        }
        else{
            mCursor = mDb.query(true, StudentDB.StudentEntry.TABLE_NAME, new String[] {StudentDB.StudentEntry.COLUMN_NAME_STUDENT_ID, StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME},
                    StudentDB.StudentEntry.COLUMN_NAME_TITLE_STUDENT_NAME + " like '%" + inputText + "%'", null, null, null, null, null);
        }

        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
