package com.promise.www.schoollibrary;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.promise.www.schoollibrary.NOTE.Database.NoteActivity;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

private LinearLayout searchlayout;
private ImageView btnsearch;
private EditText enterwordstosearch;
    private Query query;
    private FirestoreRecyclerAdapter<PdfModel, ShopViewHolder> mAdapter;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        btnsearch = findViewById(R.id.imgbackbutton);
        enterwordstosearch = findViewById(R.id.edtsearch);
        searchlayout = findViewById(R.id.layoutforsearch);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }
                builder.setTitle("Choose")
                        .setMessage("please choose what you want to upload, either a pdf or an image")
                        .setPositiveButton("Pdf", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), PostBookActivity.class));
                            }
                        })
                        .setNegativeButton("Pictures", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), PostImages.class));
                            }
                        })

                        .show();

            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchlayout.setVisibility(View.GONE);
                enterwordstosearch.setText(" ");

            }
        });

        findViewById(R.id.card_view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClassForAllPdfs.class);
                intent.putExtra("type", "pdf");
                startActivity(intent);

            }
        });

        findViewById(R.id.card_view2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClassForAllPdfs.class);
                intent.putExtra("type", "past");
                startActivity(intent);
            }
        });

            findViewById(R.id.card_view3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), NoteActivity.class));
                }
            });

        findViewById(R.id.imgcancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterwordstosearch.setText(" ");
            }
        });

        enterwordstosearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(enterwordstosearch.getText().toString())) {

//                    findViewById(R.id.booklistlayout).setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                  //  findViewById(R.id.booklistlayout).setVisibility(View.VISIBLE);

                    recyclerView.setVisibility(View.GONE);
                }
              query = FirebaseFirestore.getInstance().collection("books").
                      whereLessThanOrEqualTo("pdf_name", enterwordstosearch.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setUpAdapter();
        recyclerView.setAdapter(mAdapter);

    }




    @Override
    public void onStart() {
        super.onStart();
       /* if (mAdapter != null) {
            mAdapter.startListening();
        }*/
    }



    @Override
    public void onStop() {
        super.onStop();
       /* if (mAdapter != null) {
            mAdapter.stopListening();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.search){

            findViewById(R.id.layoutforsearch).setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpAdapter() {

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