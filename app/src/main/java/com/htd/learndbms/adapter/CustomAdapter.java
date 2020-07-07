package com.htd.learndbms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htd.learndbms.R;
import com.htd.learndbms.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 0;

    private ArrayList<Chapter> chapters;
    private LayoutInflater inflater;
    private ChapterListener listener;

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    public void setListener(ChapterListener listener) {
        this.listener = listener;
    }

    public CustomAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listviewitem, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_headeritem, parent, false);
            return new HeaderViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Chapter chapter = chapters.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).txtHeader.setText(chapter.getName());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).txtItemNumber.setText(chapter.getId());
            ((ItemViewHolder) holder).txtItemName.setText(chapter.getName());
        }

        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemChapterListener(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chapters.get(position).isHeader())
            return TYPE_HEADER;
        return TYPE_ITEM;

    }

    @Override
    public int getItemCount() {
        return chapters == null ? 0 : chapters.size();
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView txtHeader;

        public HeaderViewHolder(@NonNull View view) {
            super(view);
            txtHeader = view.findViewById(R.id.header);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemNumber, txtItemName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.name_item);
            txtItemNumber = itemView.findViewById(R.id.txt_numberitem);
        }
    }

    public interface ChapterListener {
        void onItemChapterListener(int position);
    }
}
