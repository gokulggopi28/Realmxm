package com.example.realmxm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    EditText courseName,courseDuration,courseDescription,courseTracks;

    Button submit,display;
    private Realm realm;

    private String courseName1,courseDuration1,courseDescription1,courseTracks1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        courseName = findViewById(R.id.coursename);
        courseDuration = findViewById(R.id.coursedur);
        courseDescription = findViewById(R.id.coursedes);
        courseTracks = findViewById(R.id.coursetracks);
        submit =findViewById(R.id.btnSubmit);

        realm =Realm.getDefaultInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseName1 = courseName.getText().toString();
                courseDescription1 = courseDescription.getText().toString();
                courseDuration1 = courseDuration.getText().toString();
                courseTracks1 = courseTracks.getText().toString();

                if (TextUtils.isEmpty(courseName1)){
                    courseName.setError("enter course name");
                } else if (TextUtils.isEmpty(courseDuration1)) {
                    courseDuration.setError("enter course duration");

                } else if (TextUtils.isEmpty(courseTracks1)) {
                    courseTracks.setError("enter course tracks");

                } else if (TextUtils.isEmpty(courseDescription1)) {
                    courseDescription.setError("enter course description");

                }else{
                    addDataToDatabase(courseName1,courseTracks1,courseDuration1,courseDescription1);
                    Toast.makeText(MainActivity.this, "Data added to db", Toast.LENGTH_SHORT).show();
                    courseName.setText("");
                    courseDescription.setText("");
                    courseTracks.setText("");
                    courseDuration.setText("");

                    startActivity(new Intent(MainActivity.this,DisplayActivity.class));


                }
            }
        });

    }

    private void addDataToDatabase(String courseName1, String courseTracks1, String courseDuration1, String courseDescription1) {

        DataModal dataModal = new DataModal();

        Number id = realm.where(DataModal.class)
                .max("id");
        long nextId;

        if (id==null){
            nextId = 1;
        }else {
            nextId = id.intValue()+1;

        }

        dataModal.setId(nextId);
        dataModal.setCourseDuration(courseDuration1);
        dataModal.setCourseDescription(courseDescription1);
        dataModal.setCourseTracks(courseTracks1);
        dataModal.setCourseName(courseName1);


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(dataModal);
            }
        });


        }
    }


