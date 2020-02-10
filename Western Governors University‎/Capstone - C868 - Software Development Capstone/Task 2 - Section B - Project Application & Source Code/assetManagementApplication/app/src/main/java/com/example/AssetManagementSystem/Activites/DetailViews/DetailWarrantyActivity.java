package com.example.AssetManagementSystem.Activites.DetailViews;

import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyWarrantyActivity;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Warranty;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.degreetracker.R;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailWarrantyActivity extends AppCompatActivity {

    public static final String WARRANTY_ID = "com.example.degreetracker.WARRANTY_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainWarrantyFragment = "nav_assessment";

    public Database database;

    private int passedWarrantyId;
    private Warranty passedWarranty;
    private boolean passedFromModifyTerm;

    private Button modifyButton;
    private TextView textViewWarrantyName;
    private TextView textViewWarrantyType;
    private TextView textViewWarrantyDate;
    private TextView textViewWarrantyCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial_waranty);

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
                openWarrantyActivity();
            }
        });

        Intent intent = getIntent();
        passedWarrantyId = intent.getIntExtra(WARRANTY_ID, -1);
        if (passedWarrantyId != -1) {
            setTitle("Detailed Warranty View");

            passedWarranty = Database.WarrantyDataAccessObject.getWarrantyById(passedWarrantyId);

            String tempWarrantyName = passedWarranty.getWarrantyName();
            String tempWarrantyType = passedWarranty.getWarrantyType();
            String tempWarrantyDueDate = passedWarranty.getWarrantyExpirationDate();
            int tempCourseId = passedWarranty.getProductId();
            String tempCourseName;

            if (tempCourseId != -1) {
                tempCourseName = (Database.ProductDataAccessObject.getProductById(tempCourseId)).getProductName();
            }else{
                tempCourseName = "No Product Selected";
            }

            textViewWarrantyName = findViewById(R.id.textViewAssessmentDetailName);
            textViewWarrantyType = findViewById(R.id.textViewAssessmentDetailType);
            textViewWarrantyDate = findViewById(R.id.editTextAssessmentDetailDueDate);
            textViewWarrantyCourse = findViewById(R.id.textViewDetailTerm);

            textViewWarrantyName.setText(tempWarrantyName);
            textViewWarrantyType.setText(tempWarrantyType);
            textViewWarrantyDate.setText(tempWarrantyDueDate);
            textViewWarrantyCourse.setText(tempCourseName);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(passedFromModifyTerm){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FRAGMENT_STRING, mainWarrantyFragment);
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

    public void openWarrantyActivity(){
        Intent intent = new Intent(this, ModifyWarrantyActivity.class);
        intent.putExtra(WARRANTY_ID, passedWarrantyId);
        startActivity(intent);
    }
}
