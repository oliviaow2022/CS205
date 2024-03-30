package com.example.cs205proj;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    private static final String TABLE_SCORES = "scores";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SCORE = "score";
    public DatabaseHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    public static synchronized DatabaseHelper getInstance(Context context, String name) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext(), name);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_SCORES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SCORE + " INTEGER)";
        db.execSQL(createTableQuery);
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, 0);
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // Method to insert score into the database
    public void insertScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    // Method to retrieve the latest score from the database
    @SuppressLint("Range")
    public int getLatestScore() {
        int latestScore = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_SCORE + " FROM " + TABLE_SCORES +
                " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            latestScore = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
            cursor.close();
        }
        db.close();
        return latestScore;
    }
}
