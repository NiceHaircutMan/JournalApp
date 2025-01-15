package com.example.journalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper{
    public final String TABLE_NAME = "Journals";
    public final String TITLE_COL = "titles";

    public DatabaseHandler(Context context) {
        super(context, "JournalDB", null, 1);
    }

    //Method that creates the table used to store titles
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT)";
        db.execSQL(query);
    }

    //Method used to add journal to database
    public void addNewJournal(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COL, title);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Method used to return list of all journals in database
    public ArrayList<JournalModel> getTitles(ArrayList<JournalModel> journalModels) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorTitles = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME, null);
        if (cursorTitles.moveToFirst()) {
            do {
                journalModels.add(new JournalModel(cursorTitles.getString(1)));
            } while (cursorTitles.moveToNext());
        }
        cursorTitles.close();
        return journalModels;
    }

    //Method used to delete a journal from database
    public void deleteJournal(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "titles=?", new String[]{title});
        db.close();
    }

    //Method used to change the name of a journal in the database
    public void editJournal(String newTitle, String oldTitle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COL, newTitle);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME,contentValues , "titles=?", new String[]{oldTitle});
    }
}
