package com.promise.www.schoollibrary.Math;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.promise.www.schoollibrary.ComputerScience.Cosc101;
import com.promise.www.schoollibrary.ComputerScience.Cosc102;
import com.promise.www.schoollibrary.R;

public class MathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String string = getIntent().getStringExtra("type");

        findViewById(R.id.math101).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Math101.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });

        findViewById(R.id.math102).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Math102.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });
    }
}
