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
import com.example.AssetManagementSystem.modules.Product;
import com.example.AssetManagementSystem.modules.Vendor;
import com.example.AssetManagementSystem.Activites.DetailViews.DetailVendorActivity;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.degreetracker.R;

import java.util.ArrayList;

public class ModifyVendorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String VENDOR_ID = "com.example.degreetracker.Vendor_ID";
    public static final String SAVED_BOOLEAN = "com.example.degreetracker.SAVED_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainActivityFragment = "nav_mentor";

    public Database database;

    private Vendor passedVendor;
    private int passedVendorId;
    private int passedProductId;


    private Button saveButton;
    private Button deleteButton;
    private EditText editVendorName;
    private EditText editVendorNumber;
    private EditText editVendorEmail;
    private Spinner productSelectorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_vendor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);
        database.openDatabase();

        productSelectorSpinner = findViewById(R.id.course_selector_spinner);
        ArrayList<Product> product_list = Database.ProductDataAccessObject.getProducts();
        ArrayAdapter<Product> courseSelectorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, product_list);
        courseSelectorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSelectorSpinner.setAdapter(courseSelectorArrayAdapter);
        productSelectorSpinner.setOnItemSelectedListener(this);

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
        passedVendorId = intent.getIntExtra(DetailVendorActivity.Vendor_ID, -1);

        if (passedVendorId != -1) {
            setTitle("Edit Vendor View");
            deleteButton.setVisibility(View.VISIBLE);

            passedVendor = Database.VendorDataAccessObject.getVendorById(passedVendorId);

            String tempVendorName = passedVendor.getVendorName();
            String tempVendorEmail = passedVendor.getVendorEmail();
            String tempVendorPhoneNumber = passedVendor.getVendorPhoneNumber();
            Product tempProduct;
            if (passedVendor.getProductId() != -1) {
                tempProduct = Database.ProductDataAccessObject.getProductById(passedVendor.getProductId());
            } else {
                tempProduct = null;
            }

            editVendorName = findViewById(R.id.editTextCourseMentor);
            editVendorNumber = findViewById(R.id.editTextCourseMentorPhone);
            editVendorEmail = findViewById(R.id.editTextCourseMentorEmail);
            productSelectorSpinner = findViewById(R.id.course_selector_spinner);

            editVendorName.setText(tempVendorName);
            editVendorNumber.setText(tempVendorPhoneNumber);
            editVendorEmail.setText(tempVendorEmail);
            if (passedVendor.getProductId() != -1) {
                ArrayAdapter productSpinnerAdapter = (ArrayAdapter) productSelectorSpinner.getAdapter();
                int productSpinnerPosition = productSpinnerAdapter.getPosition(tempProduct);
                productSelectorSpinner.setSelection(productSpinnerPosition);
            }

        } else {
            setTitle("Add Vendor View");
            deleteButton.setVisibility(View.INVISIBLE);

            editVendorName = findViewById(R.id.editTextCourseMentor);
            editVendorEmail = findViewById(R.id.editTextCourseMentorEmail);
            editVendorNumber = findViewById(R.id.editTextCourseMentorPhone);
            productSelectorSpinner = findViewById(R.id.course_selector_spinner);
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

        boolean vendorRemoved = Database.VendorDataAccessObject.removeVendor(passedVendor);

        if (vendorRemoved) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, "Failed to delete mentor.", Toast.LENGTH_LONG);
            toast.show();
        }


    }

    public void onSave(View view) {

        int tempVendorId;
        int tempCourseId;

        String tempVendorName = editVendorName.getText().toString();
        String tempVendorEmail = editVendorEmail.getText().toString();
        String tempVendorPhone = editVendorNumber.getText().toString();
        Product tempSelectedProduct = (Product) productSelectorSpinner.getSelectedItem();
        tempCourseId = tempSelectedProduct.getId();

        if (passedVendor != null) {
            tempVendorId = passedVendor.getId();
        } else {
            tempVendorId = Database.VendorDataAccessObject.getVendorCount();
        }

        Vendor tempVendor = new Vendor(tempVendorId, tempCourseId, tempVendorName, tempVendorPhone, tempVendorEmail);

        boolean isVendorValid = tempVendor.isVendorValid();
        boolean didVendorSave = false;

        if (isVendorValid && passedVendorId == -1) {
            didVendorSave = Database.VendorDataAccessObject.addVendor(tempVendor);
        } else if (isVendorValid) {
            didVendorSave = Database.VendorDataAccessObject.updateVendor(tempVendor);
        }

        if (didVendorSave) {
            if (passedVendorId == -1) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, DetailVendorActivity.class);
                intent.putExtra(SAVED_BOOLEAN, didVendorSave);
                intent.putExtra(VENDOR_ID, tempVendor.getId());
                startActivity(intent);
            }
        }else{
            Toast toast = Toast.makeText(this, "Failed to save Vendor", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
