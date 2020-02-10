package com.example.AssetManagementSystem.Adapaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.AssetManagementSystem.modules.Product;
import com.example.degreetracker.R;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductRecyclerViewHolder> {

    private ArrayList<Product> mProductList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ProductRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ProductRecyclerViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ProductRecyclerAdapter(ArrayList<Product> passedProductList) {
        mProductList = passedProductList;
    }

    @Override
    public ProductRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_card, parent, false);
        ProductRecyclerViewHolder productRecyclerViewHolder = new ProductRecyclerViewHolder(view, mListener);
        return productRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductRecyclerViewHolder holder, int position) {

        Product currentItem = mProductList.get(position);

        holder.mImageView.setImageResource(R.drawable.ic_course);
        holder.mTextView1.setText(currentItem.getProductName());
        holder.mTextView2.setText(currentItem.getProductDates());
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

}
