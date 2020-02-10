package com.example.AssetManagementSystem.Adapaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degreetracker.R;
import com.example.AssetManagementSystem.modules.Note;

import java.util.ArrayList;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteRecyclerViewHolder> {

    private ArrayList<Note> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class NoteRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public NoteRecyclerViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public NoteRecyclerAdapter(ArrayList<Note> passedMentorList) {
        mNoteList = passedMentorList;
    }

    @Override
    public NoteRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_card, parent, false);
        NoteRecyclerViewHolder noteRecyclerViewHolder = new NoteRecyclerViewHolder(view, mListener);
        return noteRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(NoteRecyclerViewHolder holder, int position) {

        Note currentItem = mNoteList.get(position);
        holder.mImageView.setImageResource(R.drawable.ic_note);
        holder.mTextView1.setText(currentItem.getNoteTitle());
        holder.mTextView2.setText(currentItem.getNoteText());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

}
