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

import com.example.AssetManagementSystem.Activites.DetailViews.DetailCampusType;
import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyCampusActivity;
import com.example.degreetracker.R;
import com.example.AssetManagementSystem.Adapaters.CampusRecyclerAdapter;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Campus;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CampusFragment extends Fragment {

    public static final String CAMPUS_ID = "com.example.degreetracker.CAMPUS_ID";

    public Database database;

    private ArrayList<Campus> campus_List =  new ArrayList<>();
    private Campus localCampus;

    private RecyclerView recyclerView;
    private CampusRecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_campus, container, false);
        getActivity().setTitle("Campus List View");

        database = new Database(getActivity());
        database.openDatabase();

        if (Database.CampusDataAccessObject.getCampusCount() != 0) {
            campus_List = Database.CampusDataAccessObject.getCampuses();
        }

        recyclerView = rootView.findViewById(R.id.termRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new CampusRecyclerAdapter(campus_List);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new CampusRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {
                getCampusItem(passedPosition);
            }
        });

        FloatingActionButton addFloatingActionButton = rootView.findViewById(R.id.fab_add);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModifyCampusActivity();
            }
        });

        return rootView;
    }

    public void getCampusItem(int passedPosition){
        localCampus = campus_List.get(passedPosition);
        openCampusDetailActivity();
    }

    public void openModifyCampusActivity() {
        Intent intent = new Intent(getActivity(), ModifyCampusActivity.class);
        startActivity(intent);
    }

    public void openCampusDetailActivity() {
        Intent intent = new Intent(getActivity(), DetailCampusType.class);
        intent.putExtra(CAMPUS_ID, localCampus.getId());
        startActivity(intent);
    }
}
