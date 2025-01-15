package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JournalPages extends AppCompatActivity {
    ArrayList<JournalPageModel> journalPages;
    JournalPagesDBH allPages;
    RecyclerView recyclerView;
    JournalPageAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_pages);
        //Get name of journal from main activity
        Intent intent = getIntent();
        String journalTitleText = intent.getStringExtra("titleText");
        TextView journalTitleview = findViewById(R.id.title_of_journal);
        journalTitleview.setText(journalTitleText);
        showPages();
    }

    //Method to display all pages of journal in recycler view
    public void showPages() {
        journalPages = new ArrayList<>();
        allPages = new JournalPagesDBH(this);
        journalPages = allPages.getPages(journalPages);
        adapter = new JournalPageAdapter(this, journalPages);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView = findViewById(R.id.pages_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    //Method to return to main activity
    public void backButtonClicked(View v) {
        finish();
    }

    public void addPageButtonClicked(View v) {
        Intent intent = new Intent(v.getContext(), AddPage.class);
        startActivity(intent);
    }
}
