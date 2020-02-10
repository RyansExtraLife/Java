package com.example.AssetManagementSystem.Activites.ModifyViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Note;
import com.example.AssetManagementSystem.Activites.DetailViews.DetailProductActivity;
import com.example.AssetManagementSystem.Activites.DetailViews.DetailNoteActivity;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.AssetManagementSystem.modules.Product;
import com.example.degreetracker.R;

import java.util.ArrayList;

public class ModifyNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String NOTE_ID = "com.example.degreetracker.NOTE_ID";
    public static final String COURSE_ID = "com.example.degreetracker.PRODUCT_ID";
    public static final String SAVED_BOOLEAN = "com.example.degreetracker.SAVED_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainActivityFragment = "nav_note";

    public Database database;

    private Note passedNote;
    private int passedNoteId;
    private int passedCourseId;
    private boolean fromCourseAdd;


    private Button saveButton;
    private Button deleteButton;
    private EditText editNoteTitle;
    private EditText editNoteDescription;
    private Spinner courseSelectorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);
        database.openDatabase();

        courseSelectorSpinner = findViewById(R.id.course_selector_spinner_note);
        ArrayList<Product> product_list = Database.ProductDataAccessObject.getProducts();
        ArrayAdapter<Product> courseSelectorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, product_list);
        courseSelectorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSelectorSpinner.setAdapter(courseSelectorArrayAdapter);
        courseSelectorSpinner.setOnItemSelectedListener(this);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View passedView) {
                onSave(passedView);
            }
        });

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View passedView) {
                onDelete(passedView);
            }
        });

        Intent intent = getIntent();
        passedNoteId = intent.getIntExtra(DetailNoteActivity.NOTE_ID, -1);
        fromCourseAdd = intent.getBooleanExtra(DetailProductActivity.FROM_PRODUCT, false);

        if (passedNoteId != -1) {
            setTitle("Edit Note View");
            deleteButton.setVisibility(View.VISIBLE);

            passedNote = Database.NoteDataAccessObject.getNoteById(passedNoteId);

            String tempNoteTitle = passedNote.getNoteTitle();
            String tempNoteDescription = passedNote.getNoteText();
            Product tempProduct;
            if (passedNote.getProductId() != -1) {
                tempProduct = Database.ProductDataAccessObject.getProductById(passedNote.getProductId());
            } else {
                tempProduct = null;
            }

            editNoteTitle = findViewById(R.id.textViewNoteEditName);
            editNoteDescription = findViewById(R.id.EditTextNoteDescription);
            courseSelectorSpinner = findViewById(R.id.course_selector_spinner_note);

            editNoteTitle.setText(tempNoteTitle);
            editNoteDescription.setText(tempNoteDescription);
            if (passedNote.getProductId() != -1) {
                ArrayAdapter courseSpinnerAdapter = (ArrayAdapter) courseSelectorSpinner.getAdapter();
                int courseSpinnerPosition = courseSpinnerAdapter.getPosition(tempProduct);
                courseSelectorSpinner.setSelection(courseSpinnerPosition);
            }

        } else {
            setTitle("Add Note View");
            deleteButton.setVisibility(View.INVISIBLE);

            editNoteTitle = findViewById(R.id.textViewNoteEditName);
            editNoteDescription = findViewById(R.id.EditTextNoteDescription);
            courseSelectorSpinner = findViewById(R.id.course_selector_spinner_note);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDestroy() {
        database.closeDatabase();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onDelete(View view) {

        boolean noteRemoved = Database.NoteDataAccessObject.removeNote(passedNote);

        if (noteRemoved) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, "Failed to delete note.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onSave(View view) {

        int tempNoteId;
        int tempCourseId;

        String tempNoteName = editNoteTitle.getText().toString();
        String tempNoteDescription = editNoteDescription.getText().toString();
        Product tempSelectedProduct = (Product) courseSelectorSpinner.getSelectedItem();
        tempCourseId = tempSelectedProduct.getId();

        if (passedNote != null) {
            tempNoteId = passedNote.getId();
        } else {
            tempNoteId = Database.NoteDataAccessObject.getNoteCount();
        }

        Note tempNote = new Note(tempNoteId, tempCourseId, tempNoteName, tempNoteDescription);

        boolean isNoteValid = tempNote.isNoteValid();
        boolean didNoteSave = false;

        if (isNoteValid && passedNoteId == -1) {
            didNoteSave = Database.NoteDataAccessObject.addNote(tempNote);
        } else if (isNoteValid) {
            didNoteSave = Database.NoteDataAccessObject.updateNote(tempNote);
        }

        if (didNoteSave) {
            if (passedNoteId == -1) {
                if (fromCourseAdd) {
                    Intent intent = new Intent(this, DetailProductActivity.class);
                    intent.putExtra(SAVED_BOOLEAN, true);
                    intent.putExtra(COURSE_ID, tempNote.getProductId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(this, DetailNoteActivity.class);
                intent.putExtra(SAVED_BOOLEAN, true);
                intent.putExtra(NOTE_ID, tempNote.getId());
                startActivity(intent);
            }
        }else{
            Toast toast = Toast.makeText(this, "Failed to save Note", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
