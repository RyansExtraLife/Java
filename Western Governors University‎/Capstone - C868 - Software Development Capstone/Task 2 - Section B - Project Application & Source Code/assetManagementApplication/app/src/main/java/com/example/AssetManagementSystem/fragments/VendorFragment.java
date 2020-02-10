package com.example.AssetManagementSystem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.AssetManagementSystem.Activites.DetailViews.DetailVendorActivity;
import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyVendorActivity;
import com.example.AssetManagementSystem.Adapaters.VendorRecyclerAdapter;
import com.example.AssetManagementSystem.modules.Vendor;
import com.example.degreetracker.R;
import com.example.AssetManagementSystem.database.Database;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VendorFragment extends Fragment {

    public static final String Vendor_ID = "com.example.degreetracker.Vendor_ID";
    public Database database;

    private ArrayList<Vendor> Vendor_List = new ArrayList<>();
    private Vendor localVendor;

    private RecyclerView recyclerView;
    private VendorRecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_vendor, container, false);
        getActivity().setTitle("Vendor List View");

        database = new Database(getActivity());
        database.openDatabase();

        if(Database.VendorDataAccessObject.getVendorCount() != 0) {
            Vendor_List = Database.VendorDataAccessObject.getVendors();
        }

        recyclerView = rootView.findViewById(R.id.mentorRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new VendorRecyclerAdapter(Vendor_List);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new VendorRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {
                getVendorItem(passedPosition);
            }
        });

        FloatingActionButton addFloatingActionButton = rootView.findViewById(R.id.fab_add);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModifyVendorActivity();
            }
        });
        return rootView;
    }

    public void getVendorItem(int passedPosition){
        localVendor = Vendor_List.get(passedPosition);
        openVendorDetailActivity();
    }

    public void openModifyVendorActivity(){
        Intent intent = new Intent(getActivity(), ModifyVendorActivity.class);
        startActivity(intent);
    }

    public void openVendorDetailActivity(){
        Intent intent = new Intent(getActivity(), DetailVendorActivity.class);
        intent.putExtra(Vendor_ID, localVendor.getId());
        startActivity(intent);
    }
}
