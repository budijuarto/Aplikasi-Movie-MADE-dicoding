package com.example.tabactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;

import static android.provider.UserDictionary.Words._ID;
import static com.example.tabactivity.DatabaseContract.NoteColumns.CATEGORY;
import static com.example.tabactivity.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.example.tabactivity.DatabaseContract.NoteColumns.POSTER;
import static com.example.tabactivity.DatabaseContract.NoteColumns.TITLE;
import static com.example.tabactivity.DatabaseContract.TABLE_NOTE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_NOTE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public boolean isFavorit(String title) {
        String query = String.format("SELECT * FROM " + TABLE_NOTE + " WHERE title = '%s'", title);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<AnimeTraining > getAll(String category) {
        ArrayList<AnimeTraining > arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                CATEGORY + " = '" + category + "'",
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        AnimeTraining note;
        if (cursor.getCount() > 0) {
            do {
                note = new AnimeTraining();
                note.setId_api(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                note.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                note.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                note.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));

                arrayList.add(note);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertNote(AnimeTraining note, String category) {
        ContentValues args = new ContentValues();
        args.put(TITLE, note.getName());
        args.put(DESCRIPTION, note.getOverview());
        args.put(POSTER, note.getPoster());
        args.put(CATEGORY, category);
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(AnimeTraining note) {
        ContentValues args = new ContentValues();
        args.put(TITLE, note.getName());
        args.put(DESCRIPTION, note.getOverview());
        args.put(POSTER, note.getPoster());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + note.getId_api() + "'", null);
    }

    public int deleteNote(String name) {
        return database.delete(TABLE_NOTE, TITLE + " = '" + name + "'", null);
    }


}
