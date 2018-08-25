package com.promise.www.schoollibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.promise.www.schoollibrary.ModelClass.NoteModel;
import com.promise.www.schoollibrary.NOTE.Database.NoteDatabase;

public class NoteEditorActivity extends AppCompatActivity {

    private NoteDatabase noteDatabase;

    private EditText header, mainbody;
    private String name;
    private String content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_for_notes);
        noteDatabase = new NoteDatabase(getApplicationContext());

         name = getIntent().getStringExtra("name");
         content = getIntent().getStringExtra("content");

        header = findViewById(R.id.header);
        mainbody  = findViewById(R.id.mainbody);
        if(!TextUtils.isEmpty(name)) {
            header.setText(name);
        }
        if(!TextUtils.isEmpty(content)) {
            mainbody.setText(content);
        }
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(header.getText())){

                    Toast.makeText(NoteEditorActivity.this, "Please make sure you give the heading of the note...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mainbody.getText())){

                    Toast.makeText(NoteEditorActivity.this, "Please make sure you give the main body of the note...", Toast.LENGTH_SHORT).show();
                    return;
                }

                NoteModel noteModel = new NoteModel();
                noteModel.setName(header.getText().toString());
                noteModel.setContent(mainbody.getText().toString());
                noteModel.setTime(System.currentTimeMillis() +"");
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(content)) {
                        noteDatabase.updateNote(noteModel, name);
                    Toast.makeText(NoteEditorActivity.this, "Note has been updated successfully", Toast.LENGTH_SHORT).show();
                    header.setText("");
                    mainbody.setText("");
                    return;
                }


                noteDatabase.addNote(noteModel);

                Toast.makeText(NoteEditorActivity.this, "Note has been inserted", Toast.LENGTH_SHORT).show();
                header.setText("");
                mainbody.setText("");

            }
        });
    }


}
