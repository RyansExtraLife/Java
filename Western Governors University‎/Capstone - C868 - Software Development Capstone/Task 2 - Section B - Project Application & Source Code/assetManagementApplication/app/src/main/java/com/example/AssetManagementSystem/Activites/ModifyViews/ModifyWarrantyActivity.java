package com.example.AssetManagementSystem.Activites.ModifyViews;

import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Warranty;
import com.example.AssetManagementSystem.Activites.DetailViews.DetailWarrantyActivity;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.AssetManagementSystem.modules.Product;
import com.example.degreetracker.R;
import com.example.AssetManagementSystem.Receivers.ExtendedWarrantyExpirationReceiver;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class ModifyWarrantyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String WARRANTY_ID = "com.example.degreetracker.WARRANTY_ID";
    public static final String SAVED_BOOLEAN = "com.example.degreetracker.SAVED_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainWarrantyFragment = "nav_assessment";

    public Database database;

    private Warranty passedWarranty;
    private int passedWarrantyId;
    Calendar startCalendar = Calendar.getInstance();

    private Button saveButton;
    private Button deleteButton;
    private EditText editTextWarrantyName;
    private Spinner warrantyTypeSelectSpinner;
    private EditText editTextWarrantyDueDate;
    private DatePickerDialog.OnDateSetListener onWarrantyDueDateListener;
    private Spinner productSelectTypeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_waranty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);
        database.openDatabase();

        warrantyTypeSelectSpinner = findViewById(R.id.assessment_type_spinner);
        ArrayList<String> warrantyTypeList = new ArrayList<>();
        warrantyTypeList.add("Non Accidental");
        warrantyTypeList.add("Accidental");
        ArrayAdapter<String> productStatusArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, warrantyTypeList);
        productStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        warrantyTypeSelectSpinner.setAdapter(productStatusArrayAdapter);
        warrantyTypeSelectSpinner.setOnItemSelectedListener(this);


        productSelectTypeSpinner = findViewById(R.id.course_selector_spinner);
        ArrayList<Product> product_List = Database.ProductDataAccessObject.getProducts();
        ArrayAdapter<Product> courseTermArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, product_List);
        courseTermArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSelectTypeSpinner.setAdapter(courseTermArrayAdapter);
        productSelectTypeSpinner.setOnItemSelectedListener(this);


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
        passedWarrantyId = intent.getIntExtra(DetailWarrantyActivity.WARRANTY_ID, -1);

        if (passedWarrantyId != -1) {
            setTitle("Edit  Warranty View");
            passedWarranty = Database.WarrantyDataAccessObject.getWarrantyById(passedWarrantyId);
            deleteButton.setVisibility(View.VISIBLE);

            String tempWarrantyName = passedWarranty.getWarrantyName();
            String tempWarrantyType = passedWarranty.getWarrantyType();
            String tempWarrantyDate = passedWarranty.getWarrantyExpirationDate();
            Product tempProduct;
            if(passedWarranty.getProductId() != -1){
                tempProduct = Database.ProductDataAccessObject.getProductById(passedWarranty.getProductId());
            }else{
                tempProduct = null;
            }

            editTextWarrantyName = findViewById(R.id.editTextDetailAssessmentName);
            warrantyTypeSelectSpinner = findViewById(R.id.assessment_type_spinner);
            editTextWarrantyDueDate = findViewById(R.id.editTextDetailAssessmentDueDate);
            productSelectTypeSpinner = findViewById(R.id.course_selector_spinner);

            editTextWarrantyName.setText(tempWarrantyName);
            ArrayAdapter warrantyTypeSelectSpinnerAdapter = (ArrayAdapter) warrantyTypeSelectSpinner.getAdapter();
            int warrantyTypeSelectSpinnerPosition = warrantyTypeSelectSpinnerAdapter.getPosition(tempWarrantyType);
            warrantyTypeSelectSpinner.setSelection(warrantyTypeSelectSpinnerPosition);
            editTextWarrantyDueDate.setText(tempWarrantyDate);
            if(passedWarranty.getProductId() != -1){
                ArrayAdapter productSelectTypeSpinnerAdapter = (ArrayAdapter) productSelectTypeSpinner.getAdapter();
                int courseSelectTypeSpinnerPosition = productSelectTypeSpinnerAdapter.getPosition(tempProduct);
                productSelectTypeSpinner.setSelection(courseSelectTypeSpinnerPosition);
            }

        }else{
            setTitle("Add Warranty View");
            deleteButton.setVisibility(View.INVISIBLE);

            editTextWarrantyName = findViewById(R.id.editTextDetailAssessmentName);
            warrantyTypeSelectSpinner = findViewById(R.id.assessment_type_spinner);
            editTextWarrantyDueDate = findViewById(R.id.editTextDetailAssessmentDueDate);
            productSelectTypeSpinner = findViewById(R.id.course_selector_spinner);
        }

        editTextWarrantyDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ModifyWarrantyActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onWarrantyDueDateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onWarrantyDueDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;

                startCalendar.set(year, month -1, dayOfMonth,0,0,0);

                editTextWarrantyDueDate.setText(date);
            }
        };


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

        boolean warrantyRemoved = Database.WarrantyDataAccessObject.removeWarranty(passedWarranty);

        if (warrantyRemoved) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FRAGMENT_STRING, mainWarrantyFragment);
            startActivity(intent);
        }else {
            Toast toast = Toast.makeText(this, "Failed to delete assessment", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onSave(View view) {

        int tempWarrantyId;

        String tempWarrantyName = editTextWarrantyName.getText().toString();
        String tempWarrantyType = warrantyTypeSelectSpinner.getSelectedItem().toString();
        String tempWarrantyDate = editTextWarrantyDueDate.getText().toString();
        Product tempSelectedProduct = (Product) productSelectTypeSpinner.getSelectedItem();
        int tempCourseId = tempSelectedProduct.getId();

        if(passedWarranty != null){
            tempWarrantyId = passedWarranty.getId();
        } else {
            tempWarrantyId = Database.WarrantyDataAccessObject.getWarrantyCount() +1;
        }

        Warranty tempWarranty = new Warranty(tempWarrantyId, tempCourseId, tempWarrantyName, tempWarrantyType, tempWarrantyDate);

        boolean isWarrantyValid = tempWarranty.isWarrantyValid();
        boolean didWarrantySave = false;


        if (isWarrantyValid && passedWarrantyId == -1) {
            didWarrantySave = Database.WarrantyDataAccessObject.addWarranty(tempWarranty);

        } else if (isWarrantyValid) {
            didWarrantySave = Database.WarrantyDataAccessObject.updateWarranty(tempWarranty);
        }

        if (didWarrantySave) {

            Intent startIntent = new Intent(this, ExtendedWarrantyExpirationReceiver.class);
            PendingIntent startSender = PendingIntent.getBroadcast(this, 1, startIntent, 0);
            AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            startAlarmManager.set(AlarmManager.RTC_WAKEUP, startCalendar.getTimeInMillis(), startSender);

            Toast toast = Toast.makeText(this, "Warranty Notification scheduled.", Toast.LENGTH_LONG);
            toast.show();

            if (passedWarrantyId == -1) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(FRAGMENT_STRING, mainWarrantyFragment);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, DetailWarrantyActivity.class);
                intent.putExtra(SAVED_BOOLEAN, true);
                intent.putExtra(WARRANTY_ID, tempWarranty.getId());
                startActivity(intent);
            }
        }else{
            Toast toast = Toast.makeText(this, "Failed to save assessment.", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
