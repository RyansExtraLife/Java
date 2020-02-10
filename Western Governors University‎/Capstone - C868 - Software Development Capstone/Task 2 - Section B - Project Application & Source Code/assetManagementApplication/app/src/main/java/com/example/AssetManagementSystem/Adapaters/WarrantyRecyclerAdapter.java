package com.example.AssetManagementSystem.Adapaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degreetracker.R;
import com.example.AssetManagementSystem.modules.Warranty;


import java.util.ArrayList;

public class WarrantyRecyclerAdapter extends RecyclerView.Adapter<WarrantyRecyclerAdapter.WarrantyRecyclerViewHolder> {

    private ArrayList<Warranty> mWarrantyList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class WarrantyRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public WarrantyRecyclerViewHolder(View itemView, final OnItemClickListener listener) {
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

    public WarrantyRecyclerAdapter(ArrayList<Warranty> passedWarrantyList) {
        mWarrantyList = passedWarrantyList;
    }

    @Override
    public WarrantyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_card, parent, false);
        WarrantyRecyclerViewHolder warrantyRecyclerViewHolder = new WarrantyRecyclerViewHolder(view, mListener);
        return warrantyRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(WarrantyRecyclerViewHolder holder, int position) {

        Warranty currentItem = mWarrantyList.get(position);

        holder.mImageView.setImageResource(R.drawable.ic_assessment);
        holder.mTextView1.setText(currentItem.getWarrantyName());
        holder.mTextView2.setText(currentItem.getWarrantyTypeAndDate());
    }

    @Override
    public int getItemCount() {
        return mWarrantyList.size();
    }

}
