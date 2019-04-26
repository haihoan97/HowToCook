package com.example.howtocook.model;

public class Search {

    private int SearchId;
    private String searchContent;
    private int searchQuantity;
    private String searchTime;


    public Search() {
    }

    public Search(int searchId, String searchContent, int searchQuantity, String searchTime) {
        SearchId = searchId;
        this.searchContent = searchContent;
        this.searchQuantity = searchQuantity;
        this.searchTime = searchTime;
    }

    public int getSearchId() {
        return SearchId;
    }

    public void setSearchId(int searchId) {
        SearchId = searchId;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public int getSearchQuantity() {
        return searchQuantity;
    }

    public void setSearchQuantity(int searchQuantity) {
        this.searchQuantity = searchQuantity;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }
}
