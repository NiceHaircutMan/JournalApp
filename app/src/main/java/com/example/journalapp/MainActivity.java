package com.example.journalapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener{
    ArrayList<JournalModel> journalModels;
    DatabaseHandler allJournals;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    GridLayoutManager gridLayoutManager;
    AlertDialog openJournalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //define the recycler view
        recyclerView = findViewById(R.id.journals_recyclerview);
        //Run method to display journals to recycler view
        getJournals();
    }

    //Method to get all journals and fill recycler view
    public void getJournals() {
        //Array list to hold all journal titles for recycler list
        journalModels = new ArrayList<>();
        //Object for database use
        allJournals = new DatabaseHandler(this);
        //Use database to get all journal tiltles
        journalModels = allJournals.getTitles(journalModels);
        //define and fill recycler view with all journal titles
        adapter = new RecyclerAdapter(this, journalModels, this);
        recyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    //Method to add a journal to the database
    public void addIconClicked(View v) {
        //Create a dialog box
        AlertDialog addJournalDialog;
        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View addJournalView = inflater.inflate(R.layout.add_journal_dialog, null);
        builder.setView(addJournalView);
        addJournalDialog = builder.create();
        addJournalDialog.show();
        EditText titleInput = addJournalView.findViewById(R.id.title_input);
        Button addButton = addJournalView.findViewById(R.id.add_button);
        Button cancelButton = addJournalView.findViewById(R.id.cancel_button);
        //set up the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the users input
                String title = titleInput.getText().toString();
                //Close the dialog box
                addJournalDialog.dismiss();
                //Use method to add journal to databse and recycler view
                createJournal(title);
            }
        });
        //Set up cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJournalDialog.dismiss();
            }
        });
    }

    public void createJournal(String title) {
        //Use database to add journal
        allJournals.addNewJournal(title);
        //Use method to reset recyclerview and display new journal
        getJournals();
    }

    //Method for when any journal is clicked
    @Override
    public void recyclerItemClicked(View v, int postition) {
        //Display dialog box
        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View openJournalView = inflater.inflate(R.layout.open_journal_dialog, null);
        builder.setView(openJournalView);
        openJournalDialog = builder.create();
        openJournalDialog.show();
        View itemView = gridLayoutManager.findViewByPosition(postition);
        TextView editJournalTitle = openJournalDialog.findViewById(R.id.journal_title);
        TextView journalTitle = itemView.findViewById(R.id.title_text);
        Button openButton = openJournalDialog.findViewById(R.id.open_button);
        Button editButton = openJournalDialog.findViewById(R.id.edit_button);
        Button deleteButton = openJournalDialog.findViewById(R.id.delete_button);
        Button closeButton = openJournalDialog.findViewById(R.id.close_button);
        //Show the title of whatever journal was pressed
        editJournalTitle.setText(journalTitle.getText().toString());
        //Button for opening a journal
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open a new activity showing all the pages in the journal
                Intent intent = new Intent(v.getContext(), JournalPages.class);
                intent.putExtra("titleText", editJournalTitle.getText().toString());
                startActivity(intent);
                openJournalDialog.dismiss();            }
        });
        //Button for changing journals title
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPage(editJournalTitle.getText().toString());
            }
        });
        //Button for deleting a journal
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDeletePage(editJournalTitle.getText().toString());
            }
        });
        //Button for closing the dialog box
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJournalDialog.dismiss();
            }
        });
    }

    //Method to display a doalog box to confirm user wants to delete journal
    public void checkDeletePage(String title) {
        AlertDialog confirmDeleteDialog;
        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View confirmDeleteView = inflater.inflate(R.layout.guarantee_delete_dialog, null);
        builder.setView(confirmDeleteView);
        confirmDeleteDialog = builder.create();
        confirmDeleteDialog.show();
        Button deleteButton = confirmDeleteDialog.findViewById(R.id.delete_button2);
        Button cancelButton = confirmDeleteDialog.findViewById(R.id.cancel_button2);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allJournals.deleteJournal(title);
                getJournals();
                confirmDeleteDialog.dismiss();
                openJournalDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteDialog.dismiss();
            }
        });
    }

    //Method to display a dialog box that lets the user change the
    //name of a journal
    public void editTextPage(String title) {
        AlertDialog editTitleDialog;
        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View editTitleView = inflater.inflate(R.layout.edit_title_dialog, null);
        builder.setView(editTitleView);
        editTitleDialog = builder.create();
        editTitleDialog.show();
        EditText titleEdit = editTitleDialog.findViewById(R.id.edit_title);
        Button editButton = editTitleDialog.findViewById(R.id.confirm_edit_button);
        Button cancelButton = editTitleDialog.findViewById(R.id.cancel_edit_button);
        titleEdit.setText(title);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allJournals.editJournal(titleEdit.getText().toString(), title);
                getJournals();
                editTitleDialog.dismiss();
                openJournalDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTitleDialog.dismiss();
            }
        });
    }
}