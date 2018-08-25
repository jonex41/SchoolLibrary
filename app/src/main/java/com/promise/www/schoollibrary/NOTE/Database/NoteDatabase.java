package com.promise.www.schoollibrary.NOTE.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.promise.www.schoollibrary.ModelClass.NoteModel;

import java.util.ArrayList;
import java.util.List;


public class NoteDatabase extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "note_table";


    public static final String NOTE_ID ="_id" ;
    public static final String NOTE_NAME = "note_name";
    public static final String NOTE_TIME = "note_time";
    public static final String NOTE_CONTENT = "note_content";

    public SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION =2;

    // Database Name
    private static final String DATABASE_NAME = "Note.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOTE_NAME + " TEXT , " +
                NOTE_CONTENT + " TEXT , " +
                NOTE_TIME + " TEXT " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_BENEFICIARY_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    //Method to create beneficiary records


    public void addNote(NoteModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_CONTENT, contact.getContent());
        contentValues.put(NOTE_NAME, contact.getName());
        contentValues.put(NOTE_TIME, contact.getTime());

        db.insert(TABLE_NAME, null, contentValues);


        db.close();
    }

    public void updateNote(NoteModel contact, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_CONTENT, contact.getContent());
        contentValues.put(NOTE_NAME, contact.getName());
        contentValues.put(NOTE_TIME, contact.getTime());
        db.update(TABLE_NAME, contentValues, "note_name = ?", new String[]{name});


        db.close();
    }




    public List<NoteModel> getAllNotes(){
        List<NoteModel> todos = new ArrayList<NoteModel>();
        // array of columns to fetch
        String[] columns = {
                NOTE_ID,
                NOTE_TIME,
                NOTE_CONTENT,
                NOTE_NAME,
        };
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                null ,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        if (c.moveToFirst()) {
            do {


                NoteModel td = new NoteModel();
                td.setContent(c.getString(c.getColumnIndex(NOTE_CONTENT)));
                td.setName(c.getString(c.getColumnIndex(NOTE_NAME)));
                td.setTime(c.getString(c.getColumnIndex(NOTE_TIME)));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        c.close();

        db.close();

        return todos;
    }

    public void deleteRecord(String note) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, NOTE_NAME + " = ?", new String[]{note});
        db.close();
    }


    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null, null);
        db.close();
    }


}

