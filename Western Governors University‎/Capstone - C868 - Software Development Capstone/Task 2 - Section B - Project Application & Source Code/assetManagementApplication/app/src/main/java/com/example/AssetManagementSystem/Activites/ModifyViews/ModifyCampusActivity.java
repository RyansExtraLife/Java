package com.example.AssetManagementSystem.Activites.ModifyViews;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Campus;
import com.example.AssetManagementSystem.modules.Product;
import com.example.AssetManagementSystem.Activites.DetailViews.DetailCampusType;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.AssetManagementSystem.Adapaters.ProductRecyclerAdapter;
import com.example.degreetracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ModifyCampusActivity extends AppCompatActivity {

    public static final String CAMPUS_ID = "com.example.degreetracker.CAMPUS_ID";
    public static final String SAVED_BOOLEAN = "com.example.degreetracker.SAVED_ID";
    public static final String CAMPUS_BOOLEAN = "com.example.degreetracker.SAVED_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainActivityFragment = "nav_term";

    public Database database;
    private Campus passedCampus;
    private int searchCampusId;
    private int passedCampusId;
    private int passedCourseId;

    private Button addProductButton;
    private Button saveButton;
    private Button deleteButton;
    private EditText editCampusTitle;
    private EditText editStartDate;
    private EditText editEndDate;
    private DatePickerDialog.OnDateSetListener onStartDateSetListener;
    private DatePickerDialog.OnDateSetListener onEndDateSetListener;
    private RecyclerView recyclerView;
    private ProductRecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_campus);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);
        database.openDatabase();

        addProductButton = findViewById(R.id.deleteButton);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View passedView) {
                onDelete(passedView);
            }
        });

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
        passedCampusId = intent.getIntExtra(DetailCampusType.CAMPUS_ID, -1);

        if (passedCampusId != -1) {
            setTitle("Edit Campus View");
            deleteButton.setVisibility(View.VISIBLE);

            passedCampus = Database.CampusDataAccessObject.getCampusById(passedCampusId);

            String campusTitle = passedCampus.getCampusName();
            String campusStart = passedCampus.getCampusOpenDate();
            String campusEnd = passedCampus.getCampusAuditDate();

            editCampusTitle = findViewById(R.id.termNameEditText);
            editStartDate = findViewById(R.id.termStartDateEditText);
            editEndDate = findViewById(R.id.termEndDateEditText);

            editCampusTitle.setText(campusTitle);
            editStartDate.setText(campusStart);
            editEndDate.setText(campusEnd);
        } else {
            setTitle("Add Campus View");
            deleteButton.setVisibility(View.INVISIBLE);

            editCampusTitle = findViewById(R.id.termNameEditText);
            editStartDate = findViewById(R.id.termStartDateEditText);
            editEndDate = findViewById(R.id.termEndDateEditText);

            TextView hideCoursesInCampus = findViewById(R.id.coursesInTerm);
            hideCoursesInCampus.setVisibility(View.INVISIBLE);
        }

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ModifyCampusActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ModifyCampusActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                editStartDate.setText(date);
            }
        };

        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                editEndDate.setText(date);
            }
        };

        ArrayList<Product> product_list = new ArrayList<>();
        if(Database.ProductDataAccessObject.getProductCount() != 0) {
            if(passedCampusId != -1){
                searchCampusId = passedCampusId;
            }else{
                searchCampusId = -2;
            }
            product_list = Database.ProductDataAccessObject.getProductsByCampus(searchCampusId);
        }

        recyclerView = findViewById(R.id.courseRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new ProductRecyclerAdapter(product_list);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new ProductRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

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

    public void onDelete(View view) {
        List<Product> product_list = Database.ProductDataAccessObject.getProductsByCampus(passedCampus.getId());
        boolean campusRemoved = false;

        if (product_list.size() == 0) {
            campusRemoved = Database.CampusDataAccessObject.removeCampus(passedCampus);

            if (campusRemoved) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
                startActivity(intent);
            }
        } else {
            Toast toast = Toast.makeText(this, "Cannot Delete Campus with Courses.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onSave(View view) {

        int tempCampusId;

        String campusTitle = editCampusTitle.getText().toString();
        String termStart = editStartDate.getText().toString();
        String termEnd = editEndDate.getText().toString();

        if (passedCampus != null) {
            tempCampusId = passedCampus.getId();
        } else {
            tempCampusId = Database.CampusDataAccessObject.getCampusCount();
        }

        Campus tempCampus = new Campus(tempCampusId, campusTitle, termStart, termEnd);

        boolean isTermValid = tempCampus.isValidCampus();
        boolean didTermSave = false;

        if (isTermValid && passedCampusId == -1) {
            didTermSave = Database.CampusDataAccessObject.addCampus(tempCampus);
        } else if (isTermValid) {
            didTermSave = Database.CampusDataAccessObject.updateCampus(tempCampus);
        }

        if (didTermSave) {
            if (passedCampusId == -1) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, DetailCampusType.class);
                intent.putExtra(SAVED_BOOLEAN, true);
                intent.putExtra(CAMPUS_ID, tempCampus.getId());
                startActivity(intent);
            }
        }else{
            Toast toast = Toast.makeText(this, "Failed to Save Campus", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
