package com.example.AssetManagementSystem.Activites.DetailViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyProductActivity;
import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyNoteActivity;
import com.example.AssetManagementSystem.Adapaters.WarrantyRecyclerAdapter;
import com.example.AssetManagementSystem.Adapaters.VendorRecyclerAdapter;
import com.example.AssetManagementSystem.Adapaters.NoteRecyclerAdapter;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Warranty;
import com.example.AssetManagementSystem.modules.Campus;
import com.example.AssetManagementSystem.modules.Vendor;
import com.example.AssetManagementSystem.modules.Note;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.degreetracker.R;
import com.example.AssetManagementSystem.fragments.ProductFragment;
import com.example.AssetManagementSystem.modules.Product;

import java.util.ArrayList;


public class DetailProductActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "com.example.degreetracker.PRODUCT_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public static final String FROM_PRODUCT = "com.example.degreetracker.FROM_PRODUCT";
    public String mainActivityFragment = "course_mentor";

    public Database database;

    private Product passedProduct;
    private Note localNote;
    private boolean passedFromModifyProduct;
    private int passedProductId;
    private int searchWarrantyId;
    private int searchVendorId;
    private int searchNoteId;
    ArrayList<Warranty> warranty_list = new ArrayList<>();
    ArrayList<Vendor> vendor_list = new ArrayList<>();
    ArrayList<Note> note_list = new ArrayList<>();

    private Button addNoteButton;
    private Button modifyButton;
    private TextView textViewTitle;
    private TextView textViewStatus;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private TextView textViewTermTitle;

    private RecyclerView warrantyRecyclerView;
    private WarrantyRecyclerAdapter warrantyRecyclerViewAdapter;
    private RecyclerView.LayoutManager warrantyRecyclerViewManager;

    private RecyclerView vendorRecyclerView;
    private VendorRecyclerAdapter vendorRecyclerViewAdapter;
    private RecyclerView.LayoutManager vendorRecyclerViewManager;

    private RecyclerView noteRecyclerView;
    private NoteRecyclerAdapter noteRecyclerViewAdapter;
    private RecyclerView.LayoutManager noteRecyclerViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

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
                openProductActivity();
            }
        });

        addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNoteActivity();
            }
        });

        Intent intent = getIntent();
        passedProductId = intent.getIntExtra(ProductFragment.PRODUCT_ID, -1);
        passedFromModifyProduct = intent.getBooleanExtra(ModifyProductActivity.SAVED_BOOLEAN, false);
        if (passedProductId != -1) {
            setTitle("Detailed Product View");

            passedProduct = Database.ProductDataAccessObject.getProductById(passedProductId);

            String tempProductTitle = passedProduct.getProductName();
            String tempProductStatus = passedProduct.getProductStatus();
            String tempProductPurchaseDate = passedProduct.getProductPurchaseDate();
            String tempProductWarrantyEndDate = passedProduct.getProductWarrantyEndDate();
            String tempCampusTitle;

            if(passedProduct.getCampusId() != -1){
                Campus tempCampus =  Database.CampusDataAccessObject.getCampusById(passedProduct.getCampusId());
                tempCampusTitle = tempCampus.getCampusName();
            }else{
                tempCampusTitle = "No Assigned Campus";
            }

            textViewTitle = findViewById(R.id.textViewCourseTitle);
            textViewStatus = findViewById(R.id.textViewCourseStatus);
            textViewStartDate = findViewById(R.id.textViewCourseStart);
            textViewEndDate = findViewById(R.id.textViewCourseEnd);
            textViewTermTitle = findViewById(R.id.textViewCourseTerm);

            textViewTitle.setText(tempProductTitle);
            textViewStatus.setText(tempProductStatus);
            textViewStartDate.setText(tempProductPurchaseDate);
            textViewEndDate.setText(tempProductWarrantyEndDate);
            textViewTermTitle.setText(tempCampusTitle);

            if (Database.WarrantyDataAccessObject.getWarrantyCount() != 0) {
                if (passedProductId != -1) {
                    searchWarrantyId = passedProductId;
                } else {
                    searchWarrantyId = -2;
                }
                warranty_list = Database.WarrantyDataAccessObject.getWarrantyByProduct(searchWarrantyId);
            }

            if(Database.NoteDataAccessObject.getNoteCount() != 0){
                if (passedProductId != -1){
                    searchNoteId = passedProductId;
                }else{
                    searchNoteId = -2;
                }
                note_list = Database.NoteDataAccessObject.getNotesByProduct(searchNoteId);
            }

            if (Database.VendorDataAccessObject.getVendorCount() != 0) {
                if (passedProductId != -1) {
                    searchVendorId = passedProductId;
                } else {
                    searchVendorId = -2;
                }
                vendor_list = Database.VendorDataAccessObject.getVendorsByProduct(searchVendorId);
            }
        }

        warrantyRecyclerView = findViewById(R.id.courseAssessmentRecyclerView);
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

        noteRecyclerView = findViewById(R.id.courseNoteRecyclerView);
        noteRecyclerView.setHasFixedSize(true);
        noteRecyclerViewManager = new LinearLayoutManager(this);
        noteRecyclerViewAdapter = new NoteRecyclerAdapter(note_list);
        noteRecyclerView.setLayoutManager(noteRecyclerViewManager);
        noteRecyclerView.setAdapter(noteRecyclerViewAdapter);
        noteRecyclerViewAdapter.setOnItemClickListener(new NoteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {
                getNoteItem(passedPosition);
            }
        });

        vendorRecyclerView = findViewById(R.id.courseMentorRecyclerView);
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
        if (passedFromModifyProduct) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FRAGMENT_STRING, mainActivityFragment);
            startActivity(intent);
            return true;
        } else {
            onBackPressed();
            return true;
        }
    }

    @Override
    public void onDestroy() {
        database.closeDatabase();
        super.onDestroy();
    }

    public void getNoteItem(int passedPosition){
        localNote = note_list.get(passedPosition);

        emailNoteActivity();
    }

    private void emailNoteActivity (){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, localNote.getNoteTitle());
        intent.putExtra(Intent.EXTRA_TEXT, localNote.getNoteText());
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an Email Client"));
    }


    public void openProductActivity() {
        Intent intent = new Intent(this, ModifyProductActivity.class);
        intent.putExtra(PRODUCT_ID, passedProductId);
        startActivity(intent);
    }

    public void openNoteActivity() {
        Intent intent = new Intent(this, ModifyNoteActivity.class);
        intent.putExtra(PRODUCT_ID, passedProductId);
        intent.putExtra(FROM_PRODUCT, true);
        startActivity(intent);
    }
}
