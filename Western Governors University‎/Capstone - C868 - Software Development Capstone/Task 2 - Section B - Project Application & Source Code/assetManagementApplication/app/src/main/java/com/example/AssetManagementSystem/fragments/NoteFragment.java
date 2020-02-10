package com.example.AssetManagementSystem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.AssetManagementSystem.Activites.DetailViews.DetailNoteActivity;
import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyNoteActivity;
import com.example.AssetManagementSystem.Adapaters.NoteRecyclerAdapter;
import com.example.degreetracker.R;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Note;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoteFragment extends Fragment {

    public static final String NOTE_ID = "com.example.degreetracker.NOTE_ID";
    public Database database;

    private ArrayList<Note> note_list = new ArrayList<>();
    private Note localNote;

    private RecyclerView recyclerView;
    private NoteRecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        getActivity().setTitle("Note List View");

        database = new Database(getActivity());
        database.openDatabase();

        if (Database.NoteDataAccessObject.getNoteCount() != 0) {
            note_list = Database.NoteDataAccessObject.getNotes();
        }

        recyclerView = rootView.findViewById(R.id.noteRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new NoteRecyclerAdapter(note_list);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new NoteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {
                getNoteItem(passedPosition);
            }
        });

        FloatingActionButton addFloatingActionButton = rootView.findViewById(R.id.fab_add);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModifyNoteActivity();
            }
        });
        return rootView;
    }

    public void getNoteItem(int passedPosition) {
        localNote = note_list.get(passedPosition);
        openNoteDetailActivity();
    }

    public void openModifyNoteActivity() {
        Intent intent = new Intent(getActivity(), ModifyNoteActivity.class);
        startActivity(intent);
    }

    public void openNoteDetailActivity() {
        Intent intent = new Intent(getActivity(), DetailNoteActivity.class);
        intent.putExtra(NOTE_ID, localNote.getId());
        startActivity(intent);
    }
}
