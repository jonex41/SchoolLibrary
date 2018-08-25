package com.promise.www.schoollibrary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;

import java.util.ArrayList;

import static com.fxn.utility.PermUtil.*;

public class PostImages extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postimage);
        findViewById(R.id.pickimages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pix.start(PostImages.this, 40, 10);

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 40) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

            for(String url : returnValue) {
                ImageView imageview = new ImageView(PostImages.this);
                LinearLayout linearLayout = findViewById(R.id.linearlayout);
                LinearLayout.LayoutParams params = new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                // Add image path from drawable folder.
               // imageview.setImageURI(Uri.parse(url));
                Glide.with(PostImages.this).load(url).into(imageview);
                imageview.setAdjustViewBounds(true);
                params.setMargins(0, 0, 10, 0);

                imageview.setLayoutParams(params);
                linearLayout.addView(imageview);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(PostImages.this, 40, 10);
                } else {
                    Toast.makeText(PostImages.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
