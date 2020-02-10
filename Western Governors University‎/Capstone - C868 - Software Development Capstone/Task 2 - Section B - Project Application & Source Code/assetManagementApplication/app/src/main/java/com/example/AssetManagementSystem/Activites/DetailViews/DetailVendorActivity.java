package com.example.AssetManagementSystem.Activites.DetailViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyVendorActivity;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.fragments.VendorFragment;
import com.example.AssetManagementSystem.modules.Vendor;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.degreetracker.R;

public class DetailVendorActivity extends AppCompatActivity {

    public static final String Vendor_ID = "com.example.degreetracker.Vendor_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainActivityFragment = "nav_mentor";

    public Database database;

    private Vendor passedVendor;
    private boolean passedFromModifyVendor;
    private int passedVendorId;

    private Button modifyButton;
    private TextView textViewVendorName;
    private TextView textViewVendorEmail;
    private TextView textViewVendorPhone;
    private TextView textViewAssociatedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vendor);

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
                openModifyVendorActivity();
            }
        });

        Intent intent = getIntent();
        passedVendorId = intent.getIntExtra(VendorFragment.Vendor_ID, -1);
        passedFromModifyVendor = intent.getBooleanExtra(ModifyVendorActivity.SAVED_BOOLEAN, false);
        if (passedVendorId != -1) {
            setTitle("Detailed Vendor View");

            passedVendor = Database.VendorDataAccessObject.getVendorById(passedVendorId);

            String tempVendorName = passedVendor.getVendorName();
            String tempVendorEmail = passedVendor.getVendorEmail();
            String tempVendorPhoneNumber = passedVendor.getVendorPhoneNumber();
            int tempVendorProductId = passedVendor.getProductId();
            String tempVendorProduct = (Database.ProductDataAccessObject.getProductById(tempVendorProductId).getProductName());

            textViewVendorName = findViewById(R.id.textViewDetailMentorName);
            textViewVendorEmail = findViewById(R.id.textViewDetailMentorEmail);
            textViewVendorPhone = findViewById(R.id.textViewDetailMentorPhoneNumber);
            textViewAssociatedProduct =findViewById(R.id.textViewAssosicatedCourse);

            textViewVendorName.setText(tempVendorName);
            textViewVendorEmail.setText(tempVendorEmail);
            textViewVendorPhone.setText(tempVendorPhoneNumber);
            textViewAssociatedProduct.setText(tempVendorProduct);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(passedFromModifyVendor){
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

    public void openModifyVendorActivity(){
        Intent intent = new Intent(this, ModifyVendorActivity.class);
        intent.putExtra(Vendor_ID, passedVendorId);
        startActivity(intent);
    }
}
