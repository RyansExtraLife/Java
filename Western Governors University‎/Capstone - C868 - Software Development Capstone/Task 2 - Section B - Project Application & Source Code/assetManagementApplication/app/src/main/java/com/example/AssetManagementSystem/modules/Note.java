package com.example.AssetManagementSystem.modules;


public class Note {

    private int noteId;
    private int productId;
    private String noteTitle;
    private String noteText;

    public Note(int passedNoteId, int passedProductId, String passedNoteTitle, String passedNoteText) {
        this.noteId = passedNoteId;
        this.productId = passedProductId;
        this.noteTitle = passedNoteTitle;
        this.noteText = passedNoteText;
    }

    public int getId() {
        return this.noteId;
    }

    public int getProductId() {
        return this.productId;
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

    public String getNoteText() {
        return this.noteText;
    }

    @Override
    public String toString() {
        return this.noteTitle;
    }

    public boolean isNoteValid() {
        return !noteTitle.isEmpty() && !noteText.isEmpty();
    }
}
