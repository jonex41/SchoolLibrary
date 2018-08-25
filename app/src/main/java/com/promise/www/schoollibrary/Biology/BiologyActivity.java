package com.promise.www.schoollibrary.Biology;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.promise.www.schoollibrary.Physics.PhysicsActivity;
import com.promise.www.schoollibrary.R;

public class BiologyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biology);
        final String string = getIntent().getStringExtra("type");

        findViewById(R.id.biol101).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Biol101.class);
                intent.putExtra("type", string);
                startActivity(intent);

            }
        });

        findViewById(R.id.biol102).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Biol102.class);
                intent.putExtra("type", string);
                startActivity(intent);
            }
        });
    }
}
