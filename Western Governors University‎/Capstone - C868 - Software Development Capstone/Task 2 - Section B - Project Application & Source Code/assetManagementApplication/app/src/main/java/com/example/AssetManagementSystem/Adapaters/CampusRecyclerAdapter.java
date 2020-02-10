package com.example.AssetManagementSystem.Adapaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.AssetManagementSystem.modules.Campus;
import com.example.degreetracker.R;

import java.util.ArrayList;

public class CampusRecyclerAdapter extends RecyclerView.Adapter<CampusRecyclerAdapter.CampusRecyclerViewHolder> {

    private ArrayList<Campus> mCampusList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class CampusRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public CampusRecyclerViewHolder(View itemView, final OnItemClickListener listener) {
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

    public CampusRecyclerAdapter(ArrayList<Campus> passedCampusList) {
        mCampusList = passedCampusList;
    }

    @Override
    public CampusRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_card, parent, false);
        CampusRecyclerViewHolder campusRecyclerViewHolder = new CampusRecyclerViewHolder(view, mListener);
        return campusRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(CampusRecyclerViewHolder holder, int position) {

        Campus currentItem = mCampusList.get(position);

        holder.mImageView.setImageResource(R.drawable.ic_home);
        holder.mTextView1.setText(currentItem.getCampusName());
        holder.mTextView2.setText("Next Audit: " + currentItem.getCampusAuditDate());
    }

    @Override
    public int getItemCount() {
        return mCampusList.size();
    }

}
