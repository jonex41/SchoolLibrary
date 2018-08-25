package com.promise.www.schoollibrary.ComputerScience;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.promise.www.schoollibrary.R;

public class ComputerActivity  extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computerscience);
        final String string = getIntent().getStringExtra("type");



        findViewById(R.id.cosc101).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Cosc101.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });

        findViewById(R.id.cosc102).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Cosc102.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });
    }

}
