package com.vigneshtraining.assignment73;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vigneshtraining.assignment73.database.DBhandler;
import com.vigneshtraining.assignment73.model.Employee;
import com.vigneshtraining.assignment73.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView nameTxt,ageTxt;
    private ImageView imgView;
    private DBhandler handleDB;
    private List<Employee> employees=new LinkedList<Employee>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTxt= (TextView) findViewById(R.id.nameTxt);
        ageTxt= (TextView) findViewById(R.id.ageTxt);
        imgView= (ImageView) findViewById(R.id.imgView);

        handleDB= DBhandler.getInstance(this);

        try {
            retriveEmployee();
        }catch (Exception ex){
            Toast.makeText(this,"There is no sufficient memory do the operation, please extend the memory.",Toast.LENGTH_LONG).show();
        }


    }

    private void addEmployee(){
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.img);
        byte[] photo=getBytes(bitmap);
        handleDB.addEmployee("Vigneshwaran",99,photo);
        retriveEmployee();
    }

    private void retriveEmployee(){

        employees=handleDB.getAllEmployees();

        if(employees.isEmpty()){
            addEmployee();
        }else{
           Employee employee= employees.get(0);
           nameTxt.setText(employee.getName());
           ageTxt.setText(employee.getAge().toString());
           imgView.setImageBitmap(getImage(employee.getPhoto()));

        }
    }

    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    private Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handleDB.close();
    }
}
