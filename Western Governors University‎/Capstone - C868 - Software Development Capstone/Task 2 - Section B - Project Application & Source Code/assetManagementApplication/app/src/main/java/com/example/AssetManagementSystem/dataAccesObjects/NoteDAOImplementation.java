package com.example.AssetManagementSystem.dataAccesObjects;


import com.example.AssetManagementSystem.modules.Note;

import java.util.ArrayList;

interface NoteDAOImplementation {

    boolean addNote(Note passedNote);

    boolean removeNote(Note passedNote);

    boolean updateNote(Note passedNote);

    int getNoteCount();

    ArrayList<Note> getNotes();

    ArrayList<Note> getNotesByProduct(int courseId);

    Note getNoteById(int passedNoteId);
}
