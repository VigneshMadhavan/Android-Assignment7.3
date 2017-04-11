package com.vigneshtraining.assignment73.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.vigneshtraining.assignment73.model.Employee;
import com.vigneshtraining.assignment73.utils.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vimadhavan on 4/9/2017.
 */

public class DBhandler {
    private SQLiteDatabase db;
    private final Context context;
    private final DBhelper dbHelper;
    private static DBhandler db_handler = null;

    public static DBhandler getInstance(Context context){
        try{
            if(db_handler == null){
                db_handler = new DBhandler(context);
                db_handler.open();
            }
        }catch(IllegalStateException e){
            //db_helper already open
        }
        return db_handler;
    }

    public DBhandler(Context context) {

        this.context = context;
        this.dbHelper = new DBhelper(context, Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }
    public void close() {
        try {
            if (db.isOpen()) {
                db.close();
            }
        }catch (Exception e){

        }

    }



    /*
     * open database
     */
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            // Log.v("open database Exception", "error==" + e.getMessage());
            db = dbHelper.getReadableDatabase();
        }
    }




    public List<Employee> getAllEmployees(){
        List<Employee> employees=new LinkedList<Employee>();

        String query = "SELECT  * FROM " + Constants.EMPLOYEE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Integer id;


        Employee employee=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    employee = new Employee();

                    employee.setId(cursor.getInt(0));
                    employee.setName(cursor.getString(1));
                    employee.setAge(cursor.getInt(2));
                    employee.setPhoto(cursor.getBlob(3));
                    employees.add(employee);
                } while (cursor.moveToNext());


            }
        }finally {
            cursor.close();
        }




        return employees;

    }


    public long addEmployee(String name,Integer age,byte[] photo) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Constants.EMPLOYEE_NAME, name);
        initialValues.put(Constants.EMPLOYEE_AGE, age);
        initialValues.put(Constants.EMPLOYEE_PHOTO, photo);

        return db.insert(Constants.EMPLOYEE_TABLE_NAME , null, initialValues);
    }



}
