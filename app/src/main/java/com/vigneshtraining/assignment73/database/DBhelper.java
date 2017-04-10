package com.vigneshtraining.assignment73.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vigneshtraining.assignment73.utils.Constants;

/**
 * Created by vimadhavan on 4/9/2017.
 */

public class DBhelper extends SQLiteOpenHelper {
    private Context context;
    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE IF NOT EXISTS " + Constants.EMPLOYEE_TABLE_NAME + " ("
                + Constants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.EMPLOYEE_NAME + " TEXT, "
                + Constants.EMPLOYEE_AGE+ " INTEGER, "
                + Constants.EMPLOYEE_PHOTO+" BLOB NOT NULL"
                +")";

        db.execSQL(table1);
        Log.d("Debug Vignesh:","OnCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.EMPLOYEE_TABLE_NAME);
        onCreate(db);
    }
}
