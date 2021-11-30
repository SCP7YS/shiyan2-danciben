package com.example.vocabularynotebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseWrapper {
    public static final String TABLE_NAME = "VOCABULARY";
    public static final String COL_ENGLISH = "ENGLISH";
    public static final String COL_VIETNAMESE = "VIETNAMESE";

    private SQLiteDatabase db;

    DatabaseWrapper(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public Cursor selectAll() {
        Cursor cursor = db.query(TABLE_NAME,
                new String[] {"_id", COL_ENGLISH, COL_VIETNAMESE},
                null, null, null, null, null);
        return cursor;
    }

    public void deleteRow(String word){
        db.delete(TABLE_NAME, COL_ENGLISH + " = ?", new String[] {word});
    }

    public void updateRow(String word, String newEng, String newVn){
        ContentValues values = new ContentValues();
        values.put(COL_ENGLISH, newEng);
        values.put(COL_VIETNAMESE, newVn);
        db.update(TABLE_NAME, values, COL_ENGLISH + " = ?", new String[]{word});
    }

    public void insertWord(String en, String vn){
        ContentValues values = new ContentValues();
        values.put(COL_ENGLISH, en);
        values.put(COL_VIETNAMESE, vn);
        db.insert(TABLE_NAME, null, values);
    }

    public void closeDatabase(){
        db.close();
    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "vocabulary_reminder"; // the name of our database
        private static final int DB_VERSION = 1; // the version of the database

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            updateMyDatabase(db, 0, DB_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            updateMyDatabase(db, oldVersion, newVersion);
        }

        private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 1) {
                // Create the database and insert data here
                db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +  COL_ENGLISH + " TEXT, "
                        +  COL_VIETNAMESE + " TEXT);");
            }

            if (oldVersion < 2) {
                // Do something when database is upgraded to version 2
            }

            if (oldVersion < 3) {
                // Do something when database is upgraded to version 3
            }
        }
    }
}
