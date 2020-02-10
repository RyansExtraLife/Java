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

import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyCampusActivity;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.fragments.CampusFragment;
import com.example.AssetManagementSystem.modules.Campus;
import com.example.AssetManagementSystem.Activites.MainActivity;
import com.example.AssetManagementSystem.Adapaters.ProductRecyclerAdapter;
import com.example.degreetracker.R;
import com.example.AssetManagementSystem.modules.Product;

import java.util.ArrayList;

public class DetailCampusType extends AppCompatActivity {

    public static final String CAMPUS_ID = "com.example.degreetracker.CAMPUS_ID";
    public static final String FRAGMENT_STRING = "com.example.degreetracker.FRAGMENT_STRING";
    public String mainActivityFragment = "nav_term";

    public Database database;

    private int passedCampusId;
    private int searchCampusId;
    private Campus passedCampus;
    private boolean passedFromModifyCampus;

    private Button modifyButton;
    private TextView textViewTitle;
    private TextView textViewStartDate;
    private TextView textViewEndDate;

    private RecyclerView recyclerView;
    private ProductRecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_campus);

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
                openModifyCampusActivity();
            }
        });

        Intent intent = getIntent();
        passedCampusId = intent.getIntExtra(CampusFragment.CAMPUS_ID, -1);
        passedFromModifyCampus = intent.getBooleanExtra(ModifyCampusActivity.SAVED_BOOLEAN, false);
        if (passedCampusId != -1) {
            setTitle("Detailed Campus View");

            passedCampus = Database.CampusDataAccessObject.getCampusById(passedCampusId);

            String tempCampusTitle = passedCampus.getCampusName();
            String tempCampusStartDate = passedCampus.getCampusOpenDate();
            String tempCampusEndDate = passedCampus.getCampusAuditDate();

            textViewTitle = findViewById(R.id.textViewTermTile);
            textViewStartDate = findViewById(R.id.textViewTermStartDate);
            textViewEndDate = findViewById(R.id.textViewTermEndDate);

            textViewTitle.setText(tempCampusTitle);
            textViewStartDate.setText(tempCampusStartDate);
            textViewEndDate.setText(tempCampusEndDate);
        }


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
        if(passedFromModifyCampus){
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

    public void openModifyCampusActivity(){
        Intent intent = new Intent(this, ModifyCampusActivity.class);
        intent.putExtra(CAMPUS_ID, passedCampusId);
        startActivity(intent);
    }
}


