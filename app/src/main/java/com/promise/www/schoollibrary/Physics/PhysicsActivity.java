package com.promise.www.schoollibrary.Physics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.promise.www.schoollibrary.R;

public class PhysicsActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physics);
        final String string = getIntent().getStringExtra("type");
        findViewById(R.id.phys101).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Phys101.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });

        findViewById(R.id.phys102).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Phys102.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });
    }
}
