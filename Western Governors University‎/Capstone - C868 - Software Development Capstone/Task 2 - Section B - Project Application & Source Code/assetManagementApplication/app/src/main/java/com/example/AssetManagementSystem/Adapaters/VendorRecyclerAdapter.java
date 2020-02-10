package com.example.AssetManagementSystem.Adapaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degreetracker.R;
import com.example.AssetManagementSystem.modules.Vendor;

import java.util.ArrayList;

public class VendorRecyclerAdapter extends RecyclerView.Adapter<VendorRecyclerAdapter.VendorRecyclerViewHolder> {

    private ArrayList<Vendor> mVendorList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class VendorRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public VendorRecyclerViewHolder(View itemView, final OnItemClickListener listener) {
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

    public VendorRecyclerAdapter(ArrayList<Vendor> passedVendorList) {
        mVendorList = passedVendorList;
    }

    @Override
    public VendorRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_card, parent, false);
        VendorRecyclerViewHolder vendorRecyclerViewHolder = new VendorRecyclerViewHolder(view, mListener);
        return vendorRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(VendorRecyclerViewHolder holder, int position) {

        Vendor currentItem = mVendorList.get(position);

        holder.mImageView.setImageResource(R.drawable.ic_mentor);
        holder.mTextView1.setText(currentItem.getVendorName());
        holder.mTextView2.setText(currentItem.getVendorEmailAndPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return mVendorList.size();
    }

}
