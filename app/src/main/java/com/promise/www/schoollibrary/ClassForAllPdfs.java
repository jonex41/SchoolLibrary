package com.promise.www.schoollibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.promise.www.schoollibrary.Biology.BiologyActivity;
import com.promise.www.schoollibrary.ComputerScience.ComputerActivity;
import com.promise.www.schoollibrary.Math.MathActivity;
import com.promise.www.schoollibrary.Physics.PhysicsActivity;

public class ClassForAllPdfs extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_of_pdfs);
        final String string = getIntent().getStringExtra("type");

        findViewById(R.id.computerscience).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ComputerActivity.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });

        findViewById(R.id.physics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhysicsActivity.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });
        findViewById(R.id.biology).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BiologyActivity.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });
        findViewById(R.id.math).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MathActivity.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });


    }
}
