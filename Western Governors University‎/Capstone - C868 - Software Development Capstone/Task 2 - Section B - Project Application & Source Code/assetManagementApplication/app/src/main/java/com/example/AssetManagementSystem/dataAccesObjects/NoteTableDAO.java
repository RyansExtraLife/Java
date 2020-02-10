package com.example.AssetManagementSystem.dataAccesObjects;

import com.example.AssetManagementSystem.database.DatabaseContentProvider;
import com.example.AssetManagementSystem.modules.Note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteTableDAO extends DatabaseContentProvider implements NoteDAOImplementation, NoteTableSchema {

    private Cursor cursor;
    private ContentValues initialContentValues;

    public NoteTableDAO(SQLiteDatabase passedSqlLiteDatabase) {
        super(passedSqlLiteDatabase);
    }

    private ContentValues getContentValue() {
        return initialContentValues;
    }

    private void setContentValue(Note passedNote) {
        initialContentValues = new ContentValues();
        initialContentValues.put(NOTE_ID, passedNote.getId());
        initialContentValues.put(NOTE_PRODUCT_ID, passedNote.getProductId());
        initialContentValues.put(NOTE_TITLE, passedNote.getNoteTitle());
        initialContentValues.put(NOTE_TEXT, passedNote.getNoteText());
    }

    public boolean addNote(Note passedNote) {
        setContentValue(passedNote);

        try {
            return super.insert(TABLE_NOTES, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updateNote(Note passedNote) {
        final String selection = NOTE_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedNote.getId())};

        setContentValue(passedNote);
        try {
            return super.update(TABLE_NOTES, getContentValue(), selection, selectionArguments) > 0;
        } catch (SQLiteConstraintException ex) {
            return false;
        }
    }

    public boolean removeNote(Note passedNote) {
        final String selection = NOTE_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedNote.getId())};

        return super.delete(TABLE_NOTES, selection, selectionArguments) > 0;
    }

    //
    public ArrayList<Note> getNotesByProduct(int passedCourseId) {
        final String selection = NOTE_PRODUCT_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedCourseId)};
        ArrayList<Note> noteList = new ArrayList<Note>();

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, selection, selectionArguments, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Note note = cursorToEntity(cursor);
                noteList.add(note);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return noteList;
    }

    public Note getNoteById(int passedNoteId) {
        final String selection = NOTE_ID + " = ?";
        final String[] selectionArguments = {String.valueOf(passedNoteId)};
        Note note = null;

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, selection, selectionArguments, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                note = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return note;
    }

    public int getNoteCount() {
        List<Note> noteList = getNotes();

        return noteList.size();
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> noteList = new ArrayList<Note>();

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, null, null, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Note note = cursorToEntity(cursor);
                noteList.add(note);

                cursor.moveToNext();
            }
            cursor.close();
        }

        System.out.println(noteList);

        return noteList;
    }

    protected Note cursorToEntity(Cursor passedCursor) {

        int noteIdIndex;
        int noteCourseIdIndex;
        int noteTitleIndex;
        int noteTextIndex;

        int noteId = -1;
        int noteCourseId = -1;
        String noteTitle = "";
        String noteText = "";

        if (passedCursor != null) {
            if (passedCursor.getColumnIndex(NOTE_ID) != -1) {
                noteIdIndex = passedCursor.getColumnIndexOrThrow(NOTE_ID);
                noteId = passedCursor.getInt(noteIdIndex);
            }
            if (passedCursor.getColumnIndex(NOTE_PRODUCT_ID) != -1) {
                noteCourseIdIndex = passedCursor.getColumnIndexOrThrow(NOTE_PRODUCT_ID);
                noteCourseId = passedCursor.getInt(noteCourseIdIndex);
            }
            if (passedCursor.getColumnIndex(NOTE_TITLE) != -1) {
                noteTitleIndex = passedCursor.getColumnIndexOrThrow(NOTE_TITLE);
                noteTitle = passedCursor.getString(noteTitleIndex);
            }
            if (passedCursor.getColumnIndex(NOTE_TEXT) != -1) {
                noteTextIndex = passedCursor.getColumnIndexOrThrow(NOTE_TEXT);
                noteText = passedCursor.getString(noteTextIndex);
            }
        }

        return new Note(noteId, noteCourseId, noteTitle, noteText);

    }
}