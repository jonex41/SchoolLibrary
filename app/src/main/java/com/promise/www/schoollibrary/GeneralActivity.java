package com.promise.www.schoollibrary;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public abstract class GeneralActivity extends AppCompatActivity {

    private String string;

    private FirestoreRecyclerAdapter<PdfModel, ShopViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_class);

        string = getIntent().getStringExtra("type");

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(GeneralActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(GeneralActivity.this);
                }
                builder.setTitle("Choose")
                        .setMessage("Choose between any of the following to upload...")
                        .setPositiveButton("Pdf", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                startActivity(new Intent(GeneralActivity.this, PostBookActivity.class));

                            }
                        })
                        .setNegativeButton("Images", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.dismiss();
                                Pix.start(GeneralActivity.this, 40);

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 40) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
        }
    }


    private void setUpAdapter() {
        Query query = FirebaseFirestore.getInstance().collection(getString()+string);

        FirestoreRecyclerOptions<PdfModel> options = new FirestoreRecyclerOptions.Builder<PdfModel>().setQuery(query, PdfModel.class).build();


        mAdapter = new FirestoreRecyclerAdapter<PdfModel, ShopViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ShopViewHolder holder, int position, final PdfModel model) {


            }

            @NonNull
            @Override
            public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false));

            }
        };

    }
    public abstract String getString();
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(GeneralActivity.this, 40);
                } else {
                    Toast.makeText(GeneralActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    class ShopViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ImageView imageView;
        private TextView nameofbook;
        private LinearLayout linearLayout;


        public ShopViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imageView =  (ImageView) mView.findViewById(R.id.pdfimage);
            nameofbook = (TextView) mView.findViewById(R.id.pdfname);
            linearLayout =(LinearLayout) mView.findViewById(R.id.click_library);







        }






    }
}
