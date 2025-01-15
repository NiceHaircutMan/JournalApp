package com.example.journalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JournalPageAdapter extends RecyclerView.Adapter<JournalPageAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<JournalPageModel> allJournalPages;

    public JournalPageAdapter(Context context, ArrayList<JournalPageModel> allJournalPages) {
        this.context = context;
        this.allJournalPages = allJournalPages;
    }

    @NonNull
    @Override
    public JournalPageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_layout2, parent, false);
        return new JournalPageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalPageAdapter.MyViewHolder holder, int position) {
        holder.pageNumber.setText(allJournalPages.get(position).getPageNumber());
        holder.journalText.setText(allJournalPages.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return allJournalPages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pageNumber;
        TextView journalText;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.pageNumber = itemView.findViewById(R.id.page_number);
            this.journalText = itemView.findViewById(R.id.journal_text);
        }
    }
}
