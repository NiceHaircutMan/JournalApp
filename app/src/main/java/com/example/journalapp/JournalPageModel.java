package com.example.journalapp;

public class JournalPageModel {
    private int pageNumber;
    private String text;

    public JournalPageModel(int pageNumber, String text) {
        this.pageNumber = pageNumber;
        this.text = text;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getText() {
        return text;
    }
}
