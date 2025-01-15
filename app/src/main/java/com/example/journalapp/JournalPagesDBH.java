package com.example.journalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class JournalPagesDBH extends SQLiteOpenHelper {
    private final String TABLE_NAME = "JournalPages";
    private final String COL_1 = "id";
    private final String COL_2 = "PageNumber";
    private final String COL_3 = "PageText";

    public JournalPagesDBH(Context context) {
        super(context, "journalPageDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " INTEGER, "
                + COL_3 + " LONGTEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<JournalPageModel> getPages(ArrayList<JournalPageModel> allPages) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                allPages.add(new JournalPageModel(cursor.getInt(1),
                        cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allPages;
    }

    public void addPage(String journalText) {
        int pageNumber = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + COL_2 + ")"
            + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
               pageNumber = cursor.getInt(1) + 1;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, pageNumber);
        values.put(COL_3, journalText);
        db.insert(TABLE_NAME, null, values);
    }
}
