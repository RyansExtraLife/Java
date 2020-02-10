package com.example.AssetManagementSystem.Activites.ModifyViews;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.AssetManagementSystem.Adapaters.WarrantyRecyclerAdapter;
import com.example.AssetManagementSystem.Adapaters.VendorRecyclerAdapter;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Warranty;
import com.example.AssetManagementSystem.modules.Campus;
import com.example.AssetManagementSystem.modules.Product;
import com.example.AssetManagementSystem.modules.Vendor;
import com.example.AssetManagementSystem.Activites.DetailViews.DetailProductActivity;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.AssetManagementSystem.Receivers.ProductMFGWarrantyEndReceiver;
import com.example.AssetManagementSystem.Receivers.CourseStartReceiver;
import com.example.degreetracker.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ModifyProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String _ID = "com.example.degreetracker.PRODUCT_ID";
    public static final String SAVED_BOOLEAN = "com.example.degreetracker.SAVED_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainActivityFragment = "nav_course";

    public Database database;

    private Product passedProduct;
    private int passedProductId;
    private int searchWarrantyId;
    private int searchVendorId;
    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();
    ArrayList<Warranty> warranty_list = new ArrayList<>();
    ArrayList<Vendor> vendor_list = new ArrayList<>();


    private Button saveButton;
    private Button deleteButton;
    private EditText editProductTitle;
    private EditText editProductStart;
    private EditText editProductEnd;
    private DatePickerDialog.OnDateSetListener onStartDateSetListener;
    private DatePickerDialog.OnDateSetListener onEndDateSetListener;
    private Spinner productStatusSpinner;
    private Spinner campusSelectSpinner;

    private RecyclerView warrantyRecyclerView;
    private WarrantyRecyclerAdapter warrantyRecyclerViewAdapter;
    private RecyclerView.LayoutManager warrantyRecyclerViewManager;

    private RecyclerView vendorRecyclerView;
    private VendorRecyclerAdapter vendorRecyclerViewAdapter;
    private RecyclerView.LayoutManager vendorRecyclerViewManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new Database(this);
        database.openDatabase();

        productStatusSpinner = findViewById(R.id.course_status_spinner);
        ArrayList<String> productStatusList = new ArrayList<>();
        productStatusList.add("In Use");
        productStatusList.add("In Storage");
        productStatusList.add("Broken");
        productStatusList.add("Away for Repair");

        ArrayAdapter<String> productStatusArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productStatusList);
        productStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productStatusSpinner.setAdapter(productStatusArrayAdapter);
        productStatusSpinner.setOnItemSelectedListener(this);

        campusSelectSpinner = findViewById(R.id.term_selector_spinner);
        ArrayList<Campus> campusList = Database.CampusDataAccessObject.getCampuses();
        ArrayAdapter<Campus> productTermArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, campusList);
        productTermArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campusSelectSpinner.setAdapter(productTermArrayAdapter);
        campusSelectSpinner.setOnItemSelectedListener(this);

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
        passedProductId = intent.getIntExtra(DetailProductActivity.PRODUCT_ID, -1);
        if (passedProductId != -1) {
            setTitle("Modify Product View");
            deleteButton.setVisibility(View.VISIBLE);

            passedProduct = Database.ProductDataAccessObject.getProductById(passedProductId);

            String tempProductTitle = passedProduct.getProductName();
            String tempProductStatus = passedProduct.getProductStatus();
            String tempProductStartDate = passedProduct.getProductPurchaseDate();
            String tempProductEndDate = passedProduct.getProductWarrantyEndDate();
            Campus tempCampus;
            if (passedProduct.getCampusId() != -1) {
                tempCampus = Database.CampusDataAccessObject.getCampusById(passedProduct.getCampusId());
            } else {
                tempCampus = null;
            }

            editProductTitle = findViewById(R.id.editTextCourseName);
            productStatusSpinner = findViewById(R.id.course_status_spinner);
            editProductStart = findViewById(R.id.editTextCourseStart);
            editProductEnd = findViewById(R.id.editTextCourseEndDate);
            campusSelectSpinner = findViewById(R.id.term_selector_spinner);

            editProductTitle.setText(tempProductTitle);
            ArrayAdapter productStatusSpinnerAdapter = (ArrayAdapter) productStatusSpinner.getAdapter();
            int productStatusSpinnerPosition = productStatusSpinnerAdapter.getPosition(tempProductStatus);
            productStatusSpinner.setSelection(productStatusSpinnerPosition);
            editProductStart.setText(tempProductStartDate);
            editProductEnd.setText(tempProductEndDate);
            if (passedProduct.getCampusId() != -1) {
                ArrayAdapter termSpinnerAdapter = (ArrayAdapter) campusSelectSpinner.getAdapter();
                int termSpinnerPosition = termSpinnerAdapter.getPosition(tempCampus);
                campusSelectSpinner.setSelection(termSpinnerPosition);
            }

            if (Database.WarrantyDataAccessObject.getWarrantyCount() != 0) {
                if (passedProductId != -1) {
                    searchWarrantyId = passedProductId;
                } else {
                    searchWarrantyId = -2;
                }
                warranty_list = Database.WarrantyDataAccessObject.getWarrantyByProduct(passedProductId);
            }

            if (Database.VendorDataAccessObject.getVendorCount() != 0) {
                if (passedProductId != -1) {
                    searchVendorId = passedProductId;
                } else {
                    searchVendorId = -2;
                }
                vendor_list = Database.VendorDataAccessObject.getVendorsByProduct(passedProductId);
            }

        } else {
            setTitle("Add Product View");
            deleteButton.setVisibility(View.INVISIBLE);

            editProductTitle = findViewById(R.id.editTextCourseName);
            productStatusSpinner = findViewById(R.id.course_status_spinner);
            editProductStart = findViewById(R.id.editTextCourseStart);
            editProductEnd = findViewById(R.id.editTextCourseEndDate);
            campusSelectSpinner = findViewById(R.id.term_selector_spinner);

            TextView hideMentorLabel = findViewById(R.id.MentorsInModifyCourse);
            hideMentorLabel.setVisibility(View.INVISIBLE);

            TextView hideAssessmentLabel = findViewById(R.id.assessmentsInModifyCourse);
            hideAssessmentLabel.setVisibility(View.INVISIBLE);
        }

        editProductStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ModifyProductActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        editProductEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(ModifyProductActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String passedDate = month + "/" + dayOfMonth + "/" + year;

                startCalendar.set(year, month -1, dayOfMonth,0,0,0);

                editProductStart.setText(passedDate);
            }
        };

        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                endCalendar.set(year, month -1, dayOfMonth,0,0,0);

                editProductEnd.setText(date);
            }
        };

        warrantyRecyclerView = findViewById(R.id.modifyCourseAssessmentRecyclerView);
        warrantyRecyclerView.setHasFixedSize(true);
        warrantyRecyclerViewManager = new LinearLayoutManager(this);
        warrantyRecyclerViewAdapter = new WarrantyRecyclerAdapter(warranty_list);
        warrantyRecyclerView.setLayoutManager(warrantyRecyclerViewManager);
        warrantyRecyclerView.setAdapter(warrantyRecyclerViewAdapter);
        warrantyRecyclerViewAdapter.setOnItemClickListener(new WarrantyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {

            }
        });


        vendorRecyclerView = findViewById(R.id.modifyCourseMentorRecyclerView);
        vendorRecyclerView.setHasFixedSize(true);
        vendorRecyclerViewManager = new LinearLayoutManager(this);
        vendorRecyclerViewAdapter = new VendorRecyclerAdapter(vendor_list);
        vendorRecyclerView.setLayoutManager(vendorRecyclerViewManager);
        vendorRecyclerView.setAdapter(vendorRecyclerViewAdapter);
        vendorRecyclerViewAdapter.setOnItemClickListener(new VendorRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onDelete(View view) {

        boolean productRemoved = false;
        ArrayList<Vendor> removeVendorRelationshipList = new ArrayList<>();
        ArrayList<Warranty> removeWarrantyRelationshipList = new ArrayList<>();

        removeVendorRelationshipList = Database.VendorDataAccessObject.getVendorsByProduct(passedProductId);
        if (removeVendorRelationshipList.size() != 0) {
            for (Vendor currentVendor : removeVendorRelationshipList) {
                Vendor tempVendor = new Vendor(currentVendor.getId(), -1, currentVendor.getVendorName(), currentVendor.getVendorPhoneNumber(), currentVendor.getVendorEmail());
                boolean didMentorSave = Database.VendorDataAccessObject.updateVendor(tempVendor);
                if (didMentorSave != true) {
                    break;
                }
            }
        }

        removeWarrantyRelationshipList = Database.WarrantyDataAccessObject.getWarrantyByProduct(passedProductId);
        if (removeWarrantyRelationshipList.size() != 0) {
            for (Warranty currentWarranty : removeWarrantyRelationshipList) {
                Warranty tempWarranty = new Warranty(currentWarranty.getId(), -1, currentWarranty.getWarrantyName(), currentWarranty.getWarrantyType(), currentWarranty.getWarrantyExpirationDate());
                boolean didAssessmentSave = Database.WarrantyDataAccessObject.updateWarranty(tempWarranty);
                if (didAssessmentSave != true) {
                    break;
                }
            }
        }

        productRemoved = Database.ProductDataAccessObject.removeProduct(passedProduct);

        if (productRemoved) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, "Failed to delete coruse.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onSave(View view) {

        int tempProductId;
        int tempCampusId;


        String tempProductTitle = editProductTitle.getText().toString();
        String tempProductStatus = productStatusSpinner.getSelectedItem().toString();
        String tempProductStart = editProductStart.getText().toString();
        String tempProductEnd = editProductEnd.getText().toString();
        Campus tempSelectedCampus = (Campus) campusSelectSpinner.getSelectedItem();
        tempCampusId = tempSelectedCampus.getId();

        if (passedProduct != null) {
            tempProductId = passedProduct.getId();
        } else {
            tempProductId = Database.ProductDataAccessObject.getProductCount();
        }

        Product tempProduct = new Product(tempProductId, tempCampusId, tempProductTitle, tempProductStatus, tempProductStart, tempProductEnd);

        boolean isProductValid = tempProduct.isProductValid();
        boolean didProductSave = false;

        if (isProductValid && passedProductId == -1) {
            didProductSave = Database.ProductDataAccessObject.addProduct(tempProduct);
        } else if (isProductValid) {
            didProductSave = Database.ProductDataAccessObject.updateProduct(tempProduct);
        }

        if (didProductSave) {

            Intent startIntent = new Intent(this, CourseStartReceiver.class);
            PendingIntent startSender = PendingIntent.getBroadcast(this, 1, startIntent, 0);
            AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            startAlarmManager.set(AlarmManager.RTC_WAKEUP, startCalendar.getTimeInMillis(), startSender);


            Intent endIntent = new Intent(this, ProductMFGWarrantyEndReceiver.class);
            PendingIntent endSender = PendingIntent.getBroadcast(this, 0, endIntent, 0);
            AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            endAlarmManager.set(AlarmManager.RTC_WAKEUP, endCalendar.getTimeInMillis(), endSender);
            android.util.Log.i("Time Class ", " Time value in millisecinds "+ endCalendar.getTimeInMillis());


            Toast toast = Toast.makeText(this, "Product Date Notifications scheduled.", Toast.LENGTH_SHORT);
            toast.show();

            if (passedProductId == -1) {

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, DetailProductActivity.class);
                intent.putExtra(SAVED_BOOLEAN, true);
                intent.putExtra(_ID, tempProduct.getId());
                startActivity(intent);
            }
        }else{
            Toast toast = Toast.makeText(this, "Failed to save product.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
