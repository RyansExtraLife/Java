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

import com.example.AssetManagementSystem.Activites.DetailViews.DetailWarrantyActivity;
import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyWarrantyActivity;
import com.example.AssetManagementSystem.Adapaters.WarrantyRecyclerAdapter;
import com.example.AssetManagementSystem.modules.Warranty;
import com.example.degreetracker.R;
import com.example.AssetManagementSystem.database.Database;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WarrantyFragment extends Fragment {

    static final String WARRANTY_ID = "com.example.degreetracker.WARRANTY_ID";

    public Database database;

    ArrayList<Warranty> warranty_list = new ArrayList<>();
    private Warranty localWarranty;

    private RecyclerView recyclerView;
    private WarrantyRecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_waranty, container, false);
        getActivity().setTitle("Warranty List View");

        database = new Database(getActivity());
        database.openDatabase();

        if(Database.WarrantyDataAccessObject.getWarrantyCount() != 0) {
            warranty_list = Database.WarrantyDataAccessObject.getWarranty();
        }

        recyclerView = rootView.findViewById(R.id.assessmentRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new WarrantyRecyclerAdapter(warranty_list);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new WarrantyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {
                getWarrantyItem(passedPosition);
            }
        });

        FloatingActionButton addFloatingActionButton = rootView.findViewById(R.id.fab_add);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModifyWarrantyActivity();
            }
        });

        return rootView;
    }

    public void getWarrantyItem(int passedPosition){
        localWarranty = warranty_list.get(passedPosition);
        openWarrantyDetailActivity();
    }

    public void openModifyWarrantyActivity(){
        Intent intent = new Intent(getActivity(), ModifyWarrantyActivity.class);
        startActivity(intent);
    }

    public void openWarrantyDetailActivity(){
        Intent intent = new Intent(getActivity(), DetailWarrantyActivity.class);
        intent.putExtra(WARRANTY_ID, localWarranty.getId());
        startActivity(intent);
    }
}
