package com.example.AssetManagementSystem.dataAccesObjects;


public interface NoteTableSchema {
    String TABLE_NOTES = "notes";
    String NOTE_ID = "id";
    String NOTE_TITLE = "note_title";
    String NOTE_TEXT = "note_text";
    String NOTE_PRODUCT_ID = "note_product_id";
    String[] NOTES_COLUMNS = {NOTE_ID, NOTE_TITLE, NOTE_TEXT, NOTE_PRODUCT_ID};
    String NOTES_CREATE = "CREATE TABLE " + TABLE_NOTES + " (" + NOTE_ID + " INTEGER PRIMARY KEY, " + NOTE_PRODUCT_ID + " INTEGER, " + NOTE_TITLE + " TEXT, " + NOTE_TEXT + " TEXT " + ")";
}
