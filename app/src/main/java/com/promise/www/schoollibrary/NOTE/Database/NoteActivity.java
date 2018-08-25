package com.promise.www.schoollibrary.NOTE.Database;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.promise.www.schoollibrary.ModelClass.NoteModel;
import com.promise.www.schoollibrary.NoteAdapter;
import com.promise.www.schoollibrary.NoteEditorActivity;
import com.promise.www.schoollibrary.R;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<NoteModel> noteModelList = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private NoteDatabase noteDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_environment);
        noteDatabase = new NoteDatabase(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                new FecthNotes().execute();
          recyclerView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter(this, noteModelList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noteAdapter);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NoteEditorActivity.class));
            }
        });
    }

    class FecthNotes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if(noteModelList.size() >0){
                noteModelList.clear();
            }
            noteModelList.addAll(noteDatabase.getAllNotes());


            return null;
        }

        @Override
        public void onPostExecute(Void count) {
            noteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new FecthNotes().execute();
    }
}
