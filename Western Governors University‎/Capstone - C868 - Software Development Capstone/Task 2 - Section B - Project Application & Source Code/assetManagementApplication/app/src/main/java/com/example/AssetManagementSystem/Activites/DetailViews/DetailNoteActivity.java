package com.example.AssetManagementSystem.Activites.DetailViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyNoteActivity;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.fragments.NoteFragment;
import com.example.AssetManagementSystem.modules.Note;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.degreetracker.R;


public class DetailNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID = "com.example.degreetracker.NOTE_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainActivityFragment = "nav_note";

    public Database database;

    private Note passedNote;
    private boolean passedFromModifyNote;
    private int passedNoteId;

    private Button modifyButton;
    private TextView textViewNoteTitle;
    private TextView textViewNoteDescription;
    private TextView textViewAssociatedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);
        database.openDatabase();

        modifyButton = findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModifyNoteActivity();
            }
        });

        Intent intent = getIntent();
        passedNoteId = intent.getIntExtra(NoteFragment.NOTE_ID, -1);
        passedFromModifyNote = intent.getBooleanExtra(ModifyNoteActivity.SAVED_BOOLEAN, false);
        if (passedNoteId != -1) {
            setTitle("Detailed Note View");

            passedNote = Database.NoteDataAccessObject.getNoteById(passedNoteId);

            String tempNoteTitle = passedNote.getNoteTitle();
            String tempNoteDescription = passedNote.getNoteText();
            int tempNoteProductId = passedNote.getProductId();
            String tempNoteProduct = (Database.ProductDataAccessObject.getProductById(tempNoteProductId).getProductName());

            textViewNoteTitle = findViewById(R.id.textViewNoteDetailName);
            textViewNoteDescription = findViewById(R.id.textAreaNoteDetailDescription);
            textViewAssociatedProduct =findViewById(R.id.textViewAssosicatedCourseNote);

            textViewNoteTitle.setText(tempNoteTitle);
            textViewNoteDescription.setText(tempNoteDescription);
            textViewAssociatedProduct.setText(tempNoteProduct);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(passedFromModifyNote){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
            startActivity(intent);
            return true;
        }else{
            onBackPressed();
            return true;
        }
    }

    @Override
    public void onDestroy() {
        database.closeDatabase();
        super.onDestroy();
    }

    public void openModifyNoteActivity(){
        Intent intent = new Intent(this, ModifyNoteActivity.class);
        intent.putExtra(NOTE_ID, passedNoteId);
        startActivity(intent);
    }
}