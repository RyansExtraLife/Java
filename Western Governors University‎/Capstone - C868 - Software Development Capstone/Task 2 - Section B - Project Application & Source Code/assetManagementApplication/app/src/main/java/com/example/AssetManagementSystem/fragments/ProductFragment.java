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

import com.example.AssetManagementSystem.Activites.DetailViews.DetailProductActivity;
import com.example.AssetManagementSystem.Activites.ModifyViews.ModifyProductActivity;
import com.example.AssetManagementSystem.database.Database;
import com.example.AssetManagementSystem.modules.Product;
import com.example.AssetManagementSystem.Adapaters.ProductRecyclerAdapter;
import com.example.degreetracker.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    public static final String PRODUCT_ID = "com.example.degreetracker.PRODUCT_ID";
    public Database database;

    private ArrayList<Product> product_list = new ArrayList<>();
    private Product localProduct;


    private RecyclerView recyclerView;
    private ProductRecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        getActivity().setTitle("Product List View");

        database = new Database(getActivity());
        database.openDatabase();

        if(Database.ProductDataAccessObject.getProductCount() != 0) {
            product_list = Database.ProductDataAccessObject.getProducts();
        }

        recyclerView = rootView.findViewById(R.id.courseRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new ProductRecyclerAdapter(product_list);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new ProductRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int passedPosition) {
                getProductItem(passedPosition);
            }
        });

        FloatingActionButton addFloatingActionButton = rootView.findViewById(R.id.fab_add);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModifyProductActivity();
            }
        });
        return rootView;
    }

    public void getProductItem(int passedPosition){
        localProduct = product_list.get(passedPosition);
        openProductDetailActivity();
    }

    public void openModifyProductActivity(){
        Intent intent = new Intent(getActivity(), ModifyProductActivity.class);
        startActivity(intent);
    }

    public void openProductDetailActivity(){
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra(PRODUCT_ID, localProduct.getId());
        startActivity(intent);
    }
}
